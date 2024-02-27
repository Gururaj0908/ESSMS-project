/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.essms.admin.business.EndCustomerManagement;
import com.essms.admin.entities.EndCustomer;
import com.essms.admin.entities.EndCustomerAddress;
import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.EndCustomerModel;
import com.essms.admin.models.EndCustomerUserModel;
import com.essms.admin.models.list.ListEndCustomer;
import com.essms.admin.models.request.CreateEndCustomerRequest;
import com.essms.admin.models.request.UpdateEndCustomerRequest;
import com.essms.admin.models.request.UpsertEndCustomerAddresses;
import com.essms.admin.models.response.UploadFileResponse;
import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.admin.publishers.UserExchangePublisher;
import com.essms.admin.utils.ModelUtil;
import com.essms.core.enums.UserType;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.queue.EndCustomerRegistration;
import com.essms.hibernate.core.models.queue.SystemUserModel;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/endcustomer")
public class EndCustomerController extends
		CRUDController<EndCustomer, CreateEndCustomerRequest, UpdateEndCustomerRequest, ListEndCustomer, SearchEntity> {

	@Autowired
	private EntityOperationExchangePublisher entityOperationExchangePublisher;

	@Autowired
	private EndCustomerManagement endCustomerManagement;

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controllers.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("endCustomerUserModel.name",
				new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("endCustomerUserModel.username",
				new FieldHeaderLabelAndEntityProperty("Username", "username"));
		headerLabelAndFieldPropertyMap.put("endCustomerUserModel.mobileNo",
				new FieldHeaderLabelAndEntityProperty("Mobile No", "mobileNo"));
		headerLabelAndFieldPropertyMap.put("endCustomerUserModel.emailId",
				new FieldHeaderLabelAndEntityProperty("Email", "emailId"));
		headerLabelAndFieldPropertyMap.put("endCustomerModel.altMobileNo",
				new FieldHeaderLabelAndEntityProperty("Alt Mobile No", "altMobileNo"));
		headerLabelAndFieldPropertyMap.put("endCustomerModel.gstinNumber",
				new FieldHeaderLabelAndEntityProperty("GSTIN No", "gstinNumber"));
		return headerLabelAndFieldPropertyMap;
	}

	@RequestMapping(value = "/public/register", method = RequestMethod.POST)
	public void register(@RequestBody CreateEndCustomerRequest createRequest, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		create(createRequest, request, response);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListEndCustomer> setupList(List<EndCustomer> entities) {
		List<ListEndCustomer> listEndCustomers = new ArrayList<>();
		ListEndCustomer listEndCustomer = null;
		EndCustomerUserModel endCustomerUserModel = null;
		EndCustomerModel endCustomerModel = null;
		List<EndCustomerAddressModel> endCustomerAddressModels = null;
		EndCustomerAddressModel endCustomerAddressModel = null;
		for (EndCustomer endCustomer : entities) {
			listEndCustomer = new ListEndCustomer();
			listEndCustomer.setId(endCustomer.getId());
			listEndCustomer.setIsActive(endCustomer.getIsActive());
			endCustomerUserModel = new EndCustomerUserModel();
			endCustomerUserModel.setName(endCustomer.getName());
			endCustomerUserModel.setUsername(endCustomer.getUsername());
			endCustomerUserModel.setEmailId(endCustomer.getEmailId());
			endCustomerUserModel.setMobileNo(endCustomer.getMobileNo());
			listEndCustomer.setEndCustomerUserModel(endCustomerUserModel);
			endCustomerModel = new EndCustomerModel();
			endCustomerModel.setGstinNumber(endCustomer.getGstinNumber());
			endCustomerModel.setAltMobileNo(endCustomer.getAltMobileNo());
			endCustomerModel.setAnniversaryDate(endCustomer.getAnniversaryDate());
			endCustomerModel.setBirthDate(endCustomer.getBirthDate());
			endCustomerModel.setMaritalStatus(endCustomer.getMaritalStatus());
			endCustomerModel.setGenderType(endCustomer.getGenderType());
			endCustomerModel.setRegNo(endCustomer.getRegNo());
			endCustomerModel.setGuid(endCustomer.getGuid());
			endCustomerModel.setBranchId(endCustomer.getBranch().getId());
			endCustomerModel.setImagePath(endCustomer.getImagePath());
			listEndCustomer.setEndCustomerModel(endCustomerModel);
			if (endCustomer.getEndCustomerAddresses() != null && !endCustomer.getEndCustomerAddresses().isEmpty()) {
				endCustomerAddressModels = new ArrayList<>();
				for (EndCustomerAddress endCustomerAddress : endCustomer.getEndCustomerAddresses()) {
					endCustomerAddressModel = new EndCustomerAddressModel();
					endCustomerAddressModel.setId(endCustomerAddress.getId());
					endCustomerAddressModel.setAddressType(endCustomerAddress.getAddressType());
					endCustomerAddressModel.setIsDefault(endCustomerAddress.getIsDefault());
					endCustomerAddressModel
							.setAddressModel(ModelUtil.generateAddressModel(endCustomerAddress.getAddress()));
					endCustomerAddressModels.add(endCustomerAddressModel);
				}
				listEndCustomer.setEndCustomerAddressModels(endCustomerAddressModels);
			}
			listEndCustomers.add(listEndCustomer);
		}
		return listEndCustomers;
	}

	@Override
	protected void validateCreateRequest(CreateEndCustomerRequest createRequest, EndCustomer entity)
			throws ValidationException {
		if (createRequest.getEndCustomerUserModel() != null && !createRequest.getEndCustomerUserModel().getIsWalkin()) {
			List<FieldError> fieldErrors = new ArrayList<>();
			if (StringUtils.isBlank(createRequest.getEndCustomerUserModel().getUsername())) {
				fieldErrors.add(new FieldError("CreateEndCustomerRequest", "endCustomerUserModel.username",
						"Username is required"));
			}
			if (StringUtils.isBlank(createRequest.getEndCustomerUserModel().getPassword())) {
				fieldErrors.add(new FieldError("CreateEndCustomerRequest", "endCustomerUserModel.password",
						"Password is required"));
			}
			if (StringUtils.isBlank(createRequest.getEndCustomerUserModel().getConfirmPassword())) {
				fieldErrors.add(new FieldError("CreateEndCustomerRequest", "endCustomerUserModel.confirmPassword",
						"ConfirmPassword is required"));
			}
			if (fieldErrors.size() > 0) {
				throw new ValidationException(fieldErrors);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public EndCustomer setupCreate(CreateEndCustomerRequest createRequest, EndCustomer entity) {
		return endCustomerManagement.createEndCustomer(createRequest, entity, UserInfoUtil.getUserRoleGUIDs());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.ssms.
	 * hibernate.core.models.BaseModel, com.ssms.hibernate.core.models.CreateEntity)
	 */
	@Override
	protected void afterCreate(EndCustomer entity, CreateEndCustomerRequest createRequest) {
		endCustomerManagement.afterCreate(entity, createRequest);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupUpdate(com.ssms.core.
	 * models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public EndCustomer setupUpdate(UpdateEndCustomerRequest updateRequest, EndCustomer entity) {
		return endCustomerManagement.updateEndCustomer(updateRequest, entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterUpdate(com.ssms.
	 * hibernate.core.models.BaseModel, com.ssms.hibernate.core.models.UpdateEntity)
	 */
	@Override
	protected void afterUpdate(EndCustomer entity, UpdateEndCustomerRequest updateRequest) {
		if (updateRequest.getEndCustomerUserModel() != null && !updateRequest.getEndCustomerUserModel().getIsWalkin()) {
			RegisterUserModel registerUserModel = new RegisterUserModel();
			BeanUtils.copyProperties(updateRequest.getEndCustomerUserModel(), registerUserModel);
			userExchangePublisher.produceUser(registerUserModel, entity.getBranch().getGuid(), entity.getCreatedBy(),
					entity.getLastModifiedBy(), entity.getCreatedDate(), entity.getLastModifiedDate(), true,
					entity.getGuid(), UserType.CUSTOMER, UserInfoUtil.getTenantId(), false);
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
	protected void afterActivateDeactivate(EndCustomer entity) {
		userExchangePublisher.produceUser(null, null, entity.getCreatedBy(), entity.getLastModifiedBy(),
				entity.getCreatedDate(), entity.getLastModifiedDate(), entity.getIsActive(), entity.getGuid(),
				UserType.CUSTOMER, UserInfoUtil.getTenantId(), false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterDelete(java.util.Set)
	 */
	@Override
	protected void afterHardDelete(Set<Long> ids) {
		Set<?> userGUIDS = getUserGUIDs(ids);
		if (userGUIDS != null) {
			try {
				entityOperationExchangePublisher.produceEntityOperation(userGUIDS, EntityOperationType.Deleted,
						SystemUserModel.class);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing end customer delete, Cause : ", e);
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
		Set<?> userGUIDS = getUserGUIDs(ids);
		if (userGUIDS != null) {
			try {
				entityOperationExchangePublisher.produceEntityOperation(userGUIDS, EntityOperationType.Restored,
						SystemUserModel.class);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing end customer restore, Cause : ", e);
			}
		}
	}

	private Set<?> getUserGUIDs(Set<Long> ids) {
		Set<Object> userGUIDs = new HashSet<>();
		Map<String, Object> criterias = new HashMap<>();
		if (ids != null && ids.size() > 0) {
			if (ids.size() == 1) {
				criterias.put("id", ids.toArray()[0]);
			} else {
				criterias.put("id", StringUtils.join(ids, ","));
			}
			for (EndCustomer entity : genericRepository.findByCriteria(EndCustomer.class, criterias)) {
				userGUIDs.add(entity.getGuid());
			}
			return userGUIDs;
		}
		return null;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ListEndCustomer getByGUID(@RequestParam(required = true) String entityGUID) {
		List<EndCustomer> entities = new ArrayList<>(1);
		EndCustomer entity = genericRepository.getByGUID(getEntityClass(), entityGUID);
		if (entity != null) {
			entities.add(entity);
			List<ListEndCustomer> list = setupList(entities);
			return list.get(0);
		}
		return null;
	}

	@RequestMapping(value = "/detail/{customerGUID}", method = RequestMethod.GET)
	public EndCustomerRegistration detailByGUID(@PathVariable("customerGUID") String customerGUID) {
		return endCustomerManagement.detail(customerGUID);
	}

	@RequestMapping(value = "/upsertaddress", method = RequestMethod.POST)
	public void upsertAddress(@RequestBody UpsertEndCustomerAddresses upsertEndCustomerAddresses) {
		EndCustomer endCustomer = genericRepository.getByGUID(EndCustomer.class,
				upsertEndCustomerAddresses.getUserGUID());
		if (endCustomer == null) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("UpsertEndCustomerAddress", "userGUID",
					"Customer does not exists"));
		}
		endCustomerManagement.updateAddress(endCustomer, upsertEndCustomerAddresses.getCustomerAddressModels());
	}

	@RequestMapping(value = "/upload/image", method = RequestMethod.POST)
	public UploadFileResponse uploadImages(@RequestParam("imagePath") MultipartFile multipartFile)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		return endCustomerManagement.uploadImage(multipartFile);
	}

}
