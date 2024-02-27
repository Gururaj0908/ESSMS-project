/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.essms.admin.entities.Address;
import com.essms.admin.entities.Branch;
import com.essms.admin.entities.Employee;
import com.essms.admin.models.EmployeeModel;
import com.essms.admin.models.list.ListEmployee;
import com.essms.admin.models.request.CreateEmployeeRequest;
import com.essms.admin.models.request.UpdateEmployeeRequest;
import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.admin.publishers.UserExchangePublisher;
import com.essms.admin.utils.EntityUtil;
import com.essms.admin.utils.ModelUtil;
import com.essms.core.enums.UserType;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.DateUtil;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.business.SystemUserManagement;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.FormEditor;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.queue.SystemUserModel;
import com.essms.hibernate.core.utils.FormGeneratorUtil;
import com.essms.hibernate.core.utils.TableColumnUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController
		extends CRUDController<Employee, CreateEmployeeRequest, UpdateEmployeeRequest, ListEmployee, SearchEntity> {

	@Value("${bms.tenant.id}")
	private String bmsTenantId;

	@Value("${" + RolePropertyConstant.ADMIN_ROLE + "}")
	private String adminRoleGUID;

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Autowired
	private SystemUserManagement systemUserManagement;

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	@Autowired
	private EntityOperationExchangePublisher entityOperationExchangePublisher;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		final Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("registerUserModel.name",
				new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.username",
				new FieldHeaderLabelAndEntityProperty("Username", "username"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.mobileNo",
				new FieldHeaderLabelAndEntityProperty("Mobile No", "mobileNo"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.emailId",
				new FieldHeaderLabelAndEntityProperty("Email", "emailId"));
		headerLabelAndFieldPropertyMap.put("employeeModel.branch",
				new FieldHeaderLabelAndEntityProperty("Branch", "branch.name"));
		headerLabelAndFieldPropertyMap.put("permission",
				new FieldHeaderLabelAndEntityProperty("Permission", "permission"));
		headerLabelAndFieldPropertyMap.put("branchPermission",
				new FieldHeaderLabelAndEntityProperty("Branch Permission", "branchPermission"));
		headerLabelAndFieldPropertyMap.put("resetPassword",
				new FieldHeaderLabelAndEntityProperty("Reset Password", "resetPassword"));
		return headerLabelAndFieldPropertyMap;
	}

	@Override
	protected Map<String, String> getDisplayableColumnsWithHeaderMap() {
		final Map<String, String> displayableColumnsWithHeaderMap = new LinkedHashMap<>();
		for (final Entry<String, FieldHeaderLabelAndEntityProperty> entry : getHeaderLabelAndFieldPropertyMap()
				.entrySet()) {
			displayableColumnsWithHeaderMap.put(entry.getKey(), entry.getValue().getTableHeaderLabel());
		}
		if (UserInfoUtil.getUserRoleGUIDs().indexOf(adminRoleGUID) == -1) {
			displayableColumnsWithHeaderMap.remove("permission");
			displayableColumnsWithHeaderMap.remove("resetPassword");
		}
		return displayableColumnsWithHeaderMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#generateEditorFormList(
	 * java.lang.Class)
	 */
	@Override
	protected FormList generateEditorFormList(Class<?> clazz) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		final List<String> fieldsToSkip = new ArrayList<>();
		fieldsToSkip.add("birthDate");
		FormList formList = null;
		formList = formGeneratorUtil.generateFormList(clazz, null, FormDisplayMode.StrictlyVertical, fieldsToSkip);
		final FormEditor formEditor = new FormEditor();
		formEditor.setKey("birthDate");
		formEditor.setFormEditorType(FormEditorType.Date);
		formEditor.setLabel("Birth Date");
		formEditor.setPlaceHolder("Birth Date");
		formEditor.setMax(DateUtil.getStringCurrentDateInUTC());
		formList.getSubForms().get(1).getFormLists().get(0).getForms().add(formEditor);
		return formList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListEmployee> setupList(List<Employee> entities) {
		final List<ListEmployee> listEmployees = new ArrayList<>();
		ListEmployee listEmployee = null;
		RegisterUserModel registerUserModel = null;
		EmployeeModel employeeModel = null;
		for (final Employee employee : entities) {
			listEmployee = new ListEmployee();
			listEmployee.setId(employee.getId());
			registerUserModel = new RegisterUserModel();
			registerUserModel.setName(employee.getName());
			registerUserModel.setUsername(employee.getUsername());
			registerUserModel.setEmailId(employee.getEmailId());
			registerUserModel.setMobileNo(employee.getMobileNo());
			listEmployee.setRegisterUserModel(registerUserModel);
			employeeModel = new EmployeeModel();
			employeeModel.setBranch(employee.getBranch().getName());
			employeeModel.setBranchId(employee.getBranch().getId());
			employeeModel.setBirthDate(employee.getBirthDate());
			listEmployee.setEmployeeModel(employeeModel);
			if (employee.getAddress() != null) {
				listEmployee.setAddressModel(ModelUtil.generateAddressModel(employee.getAddress()));
			}
			listEmployee.setGuid(employee.getGuid());
			listEmployee.setIsActive(employee.getIsActive());
			listEmployee.setCreatedBy(employee.getCreatedBy());
			listEmployee.setLastModifiedBy(employee.getLastModifiedBy());
			if (listEmployee.getCreatedBy().equalsIgnoreCase("SYSTEMDEFINED")) {
				listEmployee.setPermission(TableColumnUtil.generateColumnProperty("View Permission",
						FormEditorType.Anchor, "permission", "/#/admin/user/permission/" + listEmployee.getGuid() + "|"
								+ registerUserModel.getName() + "|" + employee.getBranch().getGuid()));
			} else {
				listEmployee.setPermission(TableColumnUtil.generateColumnProperty("Manage Permission",
						FormEditorType.Anchor, "permission", "/#/admin/user/permission/" + listEmployee.getGuid() + "|"
								+ registerUserModel.getName() + "|" + employee.getBranch().getGuid()));
			}
			listEmployee
					.setBranchPermission(TableColumnUtil.generateColumnProperty(
							"Branch Permission", "/essms-auth/userbranch/permission/form/" + listEmployee.getGuid()
									+ "/" + employee.getBranch().getGuid(),
							FormEditorType.Hyperlink, "branchPermission"));
			listEmployee.setResetPassword(TableColumnUtil.generateColumnProperty("Reset Password",
					"/essms-auth/password/reset/form/" + listEmployee.getGuid(), FormEditorType.Hyperlink,
					"resetPassword"));
			listEmployees.add(listEmployee);
		}
		return listEmployees;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#setupCreate(com.ssms.
	 * hibernate.core.models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Employee setupCreate(CreateEmployeeRequest createRequest, Employee entity) {
		try {
			if (systemUserManagement.getUser(createRequest.getRegisterUserModel().getUsername(),
					createRequest.getRegisterUserModel().getEmailId(),
					createRequest.getRegisterUserModel().getMobileNo(), UserInfoUtil.getTenantId()) != null) {
				final List<FieldError> fieldErrors = new ArrayList<>();
				final FieldError fieldError = new FieldError("CreateEmployeeRequest", "registerUserModel.name",
						"User Already Registered");
				fieldErrors.add(fieldError);
				throw new ValidationException(fieldErrors);
			}
			if (!bmsTenantId.equals(UserInfoUtil.getTenantId())) {
				if (systemUserManagement.getUser(createRequest.getRegisterUserModel().getUsername(),
						createRequest.getRegisterUserModel().getEmailId(),
						createRequest.getRegisterUserModel().getMobileNo(), bmsTenantId) != null) {
					final List<FieldError> fieldErrors = new ArrayList<>();
					final FieldError fieldError = new FieldError("CreateEmployeeRequest", "registerUserModel.name",
							"User Already Registered in BMS");
					fieldErrors.add(fieldError);
					throw new ValidationException(fieldErrors);
				}
			}
		} catch (JsonProcessingException | RestClientException | JSONException e) {
			ApplicationLogger.logError("An error occurred while checking user exist in Investor", e);
			throw new ApplicationException(e);
		}
		patchEmployeeRegistration(entity, createRequest.getRegisterUserModel());
		entity.setBirthDate(createRequest.getEmployeeModel().getBirthDate());
		if (UserInfoUtil.getUserRoleGUIDs().indexOf(adminRoleGUID) != -1) {
			if (createRequest.getEmployeeModel().getBranchId() != null
					&& createRequest.getEmployeeModel().getBranchId() > 0) {
				entity.setBranch(
						genericRepository.getById(Branch.class, createRequest.getEmployeeModel().getBranchId()));
			}
		}
		if (entity.getBranch() == null) {
			if (StringUtils.isBlank(UserInfoUtil.getBranchGUID())) {
				throw new ValidationException(FieldErrorUtil.generateFieldErrors("CreateEmployeeRequest",
						"employeeModel.branchId", "You do not belong to any branch"));
			}
			entity.setBranch(genericRepository.getByGUID(Branch.class, UserInfoUtil.getBranchGUID()));
		}
		if (createRequest.getAddressModel() != null) {
			final Address address = new Address();
			entity.setAddress(EntityUtil.patchAddress(address, createRequest.getAddressModel(), genericRepository));
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.ssms.
	 * hibernate.core.models.BaseModel, com.ssms.hibernate.core.models.CreateEntity)
	 */
	@Override
	protected void afterCreate(Employee entity, CreateEmployeeRequest createRequest) {
		Boolean syncToBMS = false;
		if (!bmsTenantId.equals(UserInfoUtil.getTenantId())) {
			syncToBMS = true;
		}
		userExchangePublisher.produceUser(createRequest.getRegisterUserModel(), entity.getBranch().getGuid(),
				entity.getCreatedBy(), entity.getLastModifiedBy(), entity.getCreatedDate(),
				entity.getLastModifiedDate(), true, entity.getGuid(), UserType.BACKOFFICE, UserInfoUtil.getTenantId(),
				syncToBMS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#setupUpdate(com.ssms.
	 * hibernate.core.models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Employee setupUpdate(UpdateEmployeeRequest updateRequest, Employee entity) {
		if (updateRequest.getRegisterUserModel() != null) {
			patchEmployeeRegistration(entity, updateRequest.getRegisterUserModel());
		}
		if (updateRequest.getEmployeeModel() != null) {
			if (updateRequest.getEmployeeModel().getBirthDate() != null) {
				entity.setBirthDate(updateRequest.getEmployeeModel().getBirthDate());
			}
			if (updateRequest.getEmployeeModel().getBranchId() != null
					&& updateRequest.getEmployeeModel().getBranchId() > 0) {
				entity.setBranch(
						genericRepository.getById(Branch.class, updateRequest.getEmployeeModel().getBranchId()));
			}
		}
		if (updateRequest.getAddressModel() != null) {
			EntityUtil.patchAddress(entity.getAddress(), updateRequest.getAddressModel(), genericRepository);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterUpdate(com.ssms.
	 * hibernate.core.models.BaseModel, com.ssms.hibernate.core.models.UpdateEntity)
	 */
	@Override
	protected void afterUpdate(Employee entity, UpdateEmployeeRequest updateRequest) {
		Boolean syncToBMS = false;
		if (!bmsTenantId.equals(UserInfoUtil.getTenantId())) {
			syncToBMS = true;
		}
		if (updateRequest.getRegisterUserModel() != null) {
			userExchangePublisher.produceUser(updateRequest.getRegisterUserModel(), entity.getBranch().getGuid(),
					entity.getCreatedBy(), entity.getLastModifiedBy(), entity.getCreatedDate(),
					entity.getLastModifiedDate(), true, entity.getGuid(), UserType.BACKOFFICE,
					UserInfoUtil.getTenantId(), syncToBMS);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterActivateDeactivate(com
	 * .ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterActivateDeactivate(Employee entity) {
		Boolean syncToBMS = false;
		if (!bmsTenantId.equals(UserInfoUtil.getTenantId())) {
			syncToBMS = true;
		}
		userExchangePublisher.produceUser(null, null, entity.getCreatedBy(), entity.getLastModifiedBy(),
				entity.getCreatedDate(), entity.getLastModifiedDate(), entity.getIsActive(), entity.getGuid(),
				UserType.BACKOFFICE, UserInfoUtil.getTenantId(), syncToBMS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterDelete(java.util.Set)
	 */
	@Override
	protected void afterHardDelete(Set<Long> ids) {
		final Set<?> userGUIDS = getUserGUIDs(ids);
		if (userGUIDS != null) {
			try {
				entityOperationExchangePublisher.produceEntityOperation(userGUIDS, EntityOperationType.Deleted,
						SystemUserModel.class);
			} catch (final AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing employee delete, Cause : ", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterRestore(java.util.Set)
	 */
	@Override
	protected void afterRestore(Set<Long> ids) {
		final Set<?> userGUIDS = getUserGUIDs(ids);
		if (userGUIDS != null) {
			try {
				entityOperationExchangePublisher.produceEntityOperation(userGUIDS, EntityOperationType.Restored,
						SystemUserModel.class);
			} catch (final AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing employee restore, Cause : ", e);
			}
		}
	}

	private Set<?> getUserGUIDs(Set<Long> ids) {
		final Set<Object> userGUIDs = new HashSet<>();
		final Map<String, Object> criterias = new HashMap<>();
		if (ids != null && ids.size() > 0) {
			if (ids.size() == 1) {
				criterias.put("id", ids.toArray()[0]);
			} else {
				criterias.put("id", StringUtils.join(ids, ","));
			}
			for (final Employee entity : genericRepository.findByCriteria(Employee.class, criterias)) {
				userGUIDs.add(entity.getGuid());
			}
			return userGUIDs;
		}
		return null;
	}

	/**
	 * Patches data to Employee User Registration from Register User Model
	 *
	 * @param entity
	 * @param registerUserModel
	 * @return
	 */
	private Employee patchEmployeeRegistration(Employee entity, RegisterUserModel registerUserModel) {
		if (StringUtils.isNotBlank(registerUserModel.getName())) {
			entity.setName(registerUserModel.getName());
		}
		if (StringUtils.isNotBlank(registerUserModel.getUsername())) {
			entity.setUsername(registerUserModel.getUsername());
		}
		if (StringUtils.isNotBlank(registerUserModel.getEmailId())) {
			entity.setEmailId(registerUserModel.getEmailId());
		}
		if (StringUtils.isNotBlank(registerUserModel.getMobileNo())) {
			entity.setMobileNo(registerUserModel.getMobileNo());
		}
		return entity;
	}

}
