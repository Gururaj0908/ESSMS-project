/**
 *
 */
package com.essms.auth.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.UserManagement;
import com.essms.auth.entities.SystemUser;
import com.essms.auth.models.list.ListUser;
import com.essms.auth.models.request.UpdateUserRequest;
import com.essms.auth.models.request.UserSignUpRequest;
import com.essms.auth.models.response.UserSignUpResponse;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.auditor.Auditable;
import com.essms.hibernate.core.business.AuditManagement;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.ActivateDeactivateModel;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.PaginatedModel;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.SearchCriteria;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;
import com.essms.hibernate.core.models.response.ListWithCountResponse;
import com.essms.hibernate.core.utils.SearchCriteriaUtil;
import com.essms.hibernate.core.utils.TableColumnUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/user")
public class UserController
		extends CRUDController<SystemUser, RegisterUserModel, UpdateUserRequest, ListUser, SearchEntity> {

	@Value("${" + RolePropertyConstant.ADMIN_ROLE + "}")
	private String adminRoleGUID;

	@Autowired
	private AuditManagement auditManagement;

	@Autowired
	private UserManagement userManagement;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("name", new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("mobileNo", new FieldHeaderLabelAndEntityProperty("Mobile No", "mobileNo"));
		headerLabelAndFieldPropertyMap.put("emailId", new FieldHeaderLabelAndEntityProperty("Email", "emailId"));
		headerLabelAndFieldPropertyMap.put("username", new FieldHeaderLabelAndEntityProperty("Username", "username"));
		headerLabelAndFieldPropertyMap.put("permission",
				new FieldHeaderLabelAndEntityProperty("Permission", "permission"));
		headerLabelAndFieldPropertyMap.put("resetPassword",
				new FieldHeaderLabelAndEntityProperty("Reset Password", "resetPassword"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.hibernate.core.controller.CRUDController#
	 * getDisplayableColumnsWithHeaderMap()
	 */
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
	 * @see com.ssms.hibernate.core.controller.CRUDController#list(java.lang.String,
	 * java.lang.Boolean, java.lang.Integer, java.lang.Integer, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public ListWithCountResponse list(String jsonQueryString, Boolean includeDeActive, Integer pageNumber,
			Integer pageSize, String orderBy, Boolean desc) {
		ListWithCountResponse listWithCountResponse = new ListWithCountResponse();
		PaginatedModel paginatedModel = null;
		if (pageNumber != null && pageNumber > 0) {
			paginatedModel = new PaginatedModel();
			paginatedModel.setPageNumber(pageNumber);
			if (orderBy != null) {
				paginatedModel.setOrderBy(orderBy);
				paginatedModel.setDesc(desc);
			}
			paginatedModel.setPageSize(pageSize);
		}
		SearchCriteria searchCriteria = SearchCriteriaUtil.generate(jsonQueryString, paginatedModel,
				getHeaderLabelAndFieldPropertyMap(), SystemUser.class);
		listWithCountResponse.setEntityList(setupList(genericRepository.search(getEntityClass(), searchCriteria)));
		listWithCountResponse.setRecordCount(searchCriteria.getRecordCount());
		return listWithCountResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#listWithForms(java.lang.
	 * String, java.lang.Boolean, java.lang.Integer, java.lang.Integer,
	 * java.lang.String, java.lang.Boolean)
	 */
	@Override
	public ListWithCountAndFormsResponse listWithForms(String jsonQueryString, Boolean includeDeActive,
			Integer pageNumber, Integer pageSize, String orderBy, Boolean desc)
			throws InstantiationException, ClassNotFoundException {
		PaginatedModel paginatedModel = null;
		if (pageNumber != null) {
			paginatedModel = new PaginatedModel();
			paginatedModel.setPageNumber(pageNumber);
			if (orderBy != null) {
				paginatedModel.setOrderBy(orderBy);
				paginatedModel.setDesc(desc);
			}
			paginatedModel.setPageSize(pageSize);
		}
		SearchCriteria searchCriteria = SearchCriteriaUtil.generate(jsonQueryString, paginatedModel,
				getHeaderLabelAndFieldPropertyMap(), SystemUser.class);
		List<ListUser> entityList = setupList(genericRepository.search(getEntityClass(), searchCriteria));
		return generateListResponseWithForms(entityList, searchCriteria.getRecordCount());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListUser> setupList(List<SystemUser> entities) {
		List<ListUser> listUsers = new ArrayList<>();
		ListUser listUser = null;
		for (SystemUser systemUser : entities) {
			listUser = new ListUser();
			listUser.setId(systemUser.getId());
			listUser.setUserGUID(systemUser.getGuid());
			listUser.setName(systemUser.getName());
			listUser.setMobileNo(systemUser.getMobileNo());
			listUser.setEmailId(systemUser.getEmailId());
			listUser.setUsername(systemUser.getUsername());
			listUser.setIsActive(systemUser.isEnabled());
			listUser.setCreatedBy(systemUser.getCreatedBy());
			listUser.setLastModifiedBy(systemUser.getLastModifiedBy());
			listUser.setBranchGUID(systemUser.getBranchGUID());
			if (listUser.getCreatedBy().equalsIgnoreCase("SYSTEMDEFINED")) {
				listUser.setPermission(TableColumnUtil.generateColumnProperty("View Permission", FormEditorType.Anchor,
						"permission", "/#/admin/user/permission/" + listUser.getUserGUID() + "|" + listUser.getName()
								+ "|" + listUser.getBranchGUID()));
			} else {
				listUser.setPermission(TableColumnUtil.generateColumnProperty("Manage Permission",
						FormEditorType.Anchor, "permission", "/#/admin/user/permission/" + listUser.getUserGUID() + "|"
								+ listUser.getName() + "|" + listUser.getBranchGUID()));
			}
			listUser.setResetPassword(TableColumnUtil.generateColumnProperty("Reset Password",
					"/essms-auth/password/reset/form/" + listUser.getUserGUID(), FormEditorType.Hyperlink,
					"resetPassword"));
			listUsers.add(listUser);
		}
		return listUsers;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#setupCreate(com.ssms.
	 * hibernate.core.models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public SystemUser setupCreate(RegisterUserModel createRequest, SystemUser entity) {
		entity.setName(createRequest.getName());
		entity.setMobileNo(createRequest.getMobileNo());
		entity.setEmailId(createRequest.getEmailId());
		entity.setUsername(createRequest.getUsername());
		entity.setPassword(bCryptPasswordEncoder.encode(createRequest.getPassword()));
		entity.setCreatedBy(UserInfoUtil.getUserName());
		entity.setCreatedDate(new Date());
		entity.setGuid(UUID.randomUUID().toString());
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#setupUpdate(com.ssms.
	 * hibernate.core.models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public SystemUser setupUpdate(UpdateUserRequest updateRequest, SystemUser entity) {
		if (StringUtils.isNotBlank(updateRequest.getName())) {
			entity.setName(updateRequest.getName());
		}
		if (StringUtils.isNotBlank(updateRequest.getMobileNo())) {
			entity.setMobileNo(updateRequest.getMobileNo());
		}
		if (StringUtils.isNotBlank(updateRequest.getEmailId())) {
			entity.setEmailId(updateRequest.getEmailId());
		}
		if (StringUtils.isNotBlank(updateRequest.getPassword())) {
			entity.setPassword(bCryptPasswordEncoder.encode(updateRequest.getPassword()));
		}
		entity.setLastModifiedBy(UserInfoUtil.getUserName());
		entity.setLastModifiedDate(new Date());
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#activate(com.ssms.hibernate
	 * .core.models.ActivateDeactivateModel)
	 */
	@Override
	@Transactional
	public ListUser activate(@RequestBody ActivateDeactivateModel activateDeactivateModel)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		if (Auditable.class.isAssignableFrom(getEntityClass())) {
			EntityOperationType entityOperationType = EntityOperationType.Deactivated;
			if (activateDeactivateModel.getStatus()) {
				entityOperationType = EntityOperationType.Activated;
			}
			try {
				auditManagement.recordEntity(activateDeactivateModel.getId(), null, getEntityClass().getSimpleName(),
						UserInfoUtil.getUserName(), null, null, entityOperationType, null);
			} catch (JsonProcessingException e) {
				ApplicationLogger.logError(
						"An error occurred while trying to audit entity change, Cause : " + e.getMessage(), e);
			}
		}
		genericRepository.executeUpdate("update SystemUser set enabled = " + activateDeactivateModel.getStatus()
				+ " where id = " + activateDeactivateModel.getId());
		SystemUser updatedEntity = genericRepository.getById(getEntityClass(), activateDeactivateModel.getId());
		afterActivateDeactivate(updatedEntity);
		List<SystemUser> entities = new ArrayList<>();
		entities.add(updatedEntity);
		List<ListUser> listEntities = setupList(entities);
		return listEntities.get(0);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public UserSignUpResponse signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
//		return userManagement.userSignUp(userSignUpRequest);
		return null;
	}

	@RequestMapping(value = "/updatebyguid/{userGUID}", method = RequestMethod.PUT)
	public void updateByGUID(@RequestBody RegisterUserModel registerUserModel,
			@PathVariable("userGUID") String userGUID) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		userManagement.updateByUserGUID(registerUserModel, userGUID);
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ListWithCountResponse find(@RequestParam("jsonQueryString") String jsonQueryString) {
		ListWithCountResponse listWithCountResponse = new ListWithCountResponse();
		SearchCriteria searchCriteria = SearchCriteriaUtil.generateWithoutLike(jsonQueryString, null,
				getHeaderLabelAndFieldPropertyMap(), SystemUser.class);
		listWithCountResponse.setEntityList(setupList(genericRepository.search(getEntityClass(), searchCriteria)));
		listWithCountResponse.setRecordCount(searchCriteria.getRecordCount());
		return listWithCountResponse;
	}

}
