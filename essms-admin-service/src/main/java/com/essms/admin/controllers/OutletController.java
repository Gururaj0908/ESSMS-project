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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.essms.admin.entities.Address;
import com.essms.admin.entities.Branch;
import com.essms.admin.entities.ContactDetail;
import com.essms.admin.entities.Outlet;
import com.essms.admin.entities.OutletContactDetail;
import com.essms.admin.entities.OutletSupportingBrand;
import com.essms.admin.enums.CustomerPageRoute;
import com.essms.admin.models.ContactDetailModel;
import com.essms.admin.models.EntityContactDetailModel;
import com.essms.admin.models.OutletModel;
import com.essms.admin.models.list.ListOutlet;
import com.essms.admin.models.request.CreateOutletRequest;
import com.essms.admin.models.request.UpdateOutletRequest;
import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.admin.publishers.UserExchangePublisher;
import com.essms.admin.utils.EntityUtil;
import com.essms.admin.utils.ModelUtil;
import com.essms.core.enums.CustomerType;
import com.essms.core.enums.RouteType;
import com.essms.core.enums.UserType;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.RandomCodeUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.business.SystemUserManagement;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.BusinessObjectModel;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.queue.SystemUserModel;
import com.essms.hibernate.core.repository.GenericRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/outlet")
public class OutletController
		extends CRUDController<Outlet, CreateOutletRequest, UpdateOutletRequest, ListOutlet, SearchEntity> {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Autowired
	private SystemUserManagement systemUserManagement;

	@Autowired
	private EntityOperationExchangePublisher entityOperationExchangePublisher;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controllers.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("registerUserModel.name",
				new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.username",
				new FieldHeaderLabelAndEntityProperty("Username", "username"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.mobileNo",
				new FieldHeaderLabelAndEntityProperty("Mobile No", "mobileNo"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.emailId",
				new FieldHeaderLabelAndEntityProperty("Email", "emailId"));
		headerLabelAndFieldPropertyMap.put("outlet.gstinNumber",
				new FieldHeaderLabelAndEntityProperty("GSTIN No", "gstinNumber"));
		headerLabelAndFieldPropertyMap.put("outlet.websiteURL",
				new FieldHeaderLabelAndEntityProperty("Website URL", "Website URL"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListOutlet> setupList(List<Outlet> entities) {
		List<ListOutlet> listOutlets = new ArrayList<>();
		ListOutlet listOutlet = null;
		RegisterUserModel registerUserModel = null;
		OutletModel outletModel = null;
		Set<EntityContactDetailModel> entityContactDetailModels = null;
		ContactDetailModel contactDetailModel = null;
		EntityContactDetailModel entityContactDetailModel = null;
		Set<String> supportingBrandGUIDs = null;
		for (Outlet outlet : entities) {
			listOutlet = new ListOutlet();
			listOutlet.setId(outlet.getId());
			registerUserModel = new RegisterUserModel();
			registerUserModel.setName(outlet.getName());
			registerUserModel.setUsername(outlet.getUsername());
			registerUserModel.setEmailId(outlet.getEmailId());
			registerUserModel.setMobileNo(outlet.getMobileNo());
			listOutlet.setRegisterUserModel(registerUserModel);
			outletModel = new OutletModel();
			outletModel.setGstinNumber(outlet.getGstinNumber());
			outletModel.setWebsiteURL(outlet.getWebsiteURL());
			outletModel.setDescription(outlet.getDescription());
			listOutlet.setOutletModel(outletModel);
			supportingBrandGUIDs = new HashSet<>();
			for (OutletSupportingBrand outletSupportingBrand : outlet.getOutletSupportingBrands()) {
				supportingBrandGUIDs.add(outletSupportingBrand.getBrandGUID());
			}
			listOutlet.setSupportingBrandGUIDs(supportingBrandGUIDs);
			listOutlet.setAddressModel(ModelUtil.generateAddressModel(outlet.getAddress()));
			entityContactDetailModels = new HashSet<>();
			for (OutletContactDetail outletContactDetail : outlet.getOutletContactDetails()) {
				entityContactDetailModel = new EntityContactDetailModel();
				entityContactDetailModel.setId(outletContactDetail.getId());
				entityContactDetailModel.setIsSMSEnabled(outletContactDetail.getIsSMSEnabled());
				entityContactDetailModel.setIsEmailEnabled(outletContactDetail.getIsEmailEnabled());
				contactDetailModel = new ContactDetailModel();
				contactDetailModel.setContactEmailId(outletContactDetail.getContactDetail().getContactEmailId());
				contactDetailModel.setContactMobile(outletContactDetail.getContactDetail().getContactMobile());
				contactDetailModel.setContactPerson(outletContactDetail.getContactDetail().getContactPerson());
				contactDetailModel.setDesignation(outletContactDetail.getContactDetail().getDesignation());
				entityContactDetailModel.setContactDetailModel(contactDetailModel);
				entityContactDetailModels.add(entityContactDetailModel);
			}
			listOutlet.setEntityContactDetailModels(entityContactDetailModels);
			listOutlet.setServiceBranchId(outlet.getBranch().getId());
			listOutlet.setIsActive(outlet.getIsActive());
			listOutlets.add(listOutlet);
		}
		return listOutlets;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.request.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Outlet setupCreate(CreateOutletRequest createRequest, Outlet entity) {
		try {
			if (systemUserManagement.getUser(createRequest.getRegisterUserModel().getUsername(),
					createRequest.getRegisterUserModel().getEmailId(),
					createRequest.getRegisterUserModel().getMobileNo(), UserInfoUtil.getTenantId()) != null) {
				List<FieldError> fieldErrors = new ArrayList<>();
				FieldError fieldError = new FieldError("Outlet", "name", "User Already Registered");
				fieldErrors.add(fieldError);
				throw new ValidationException(fieldErrors);
			}
		} catch (JsonProcessingException | RestClientException | JSONException e) {
			ApplicationLogger.logError("An error occurred while checking user exist in Outlet", e);
			throw new ApplicationException(e);
		}
		patchOutletRegistration(entity, createRequest.getRegisterUserModel());
		if (createRequest.getOutletModel() != null) {
			patchOutlet(entity, createRequest.getOutletModel());
		}
		entity.setBranch(genericRepository.getById(Branch.class, createRequest.getServiceBranchId()));
		if (createRequest.getSupportingBrandGUIDs() != null) {
			OutletSupportingBrand outletSupportingBrand = null;
			for (String brandGUId : createRequest.getSupportingBrandGUIDs()) {
				outletSupportingBrand = new OutletSupportingBrand();
				outletSupportingBrand.setBrandGUID(brandGUId);
				entity.addOutletSupportingBrand(outletSupportingBrand);
			}
		}
		OutletContactDetail outletContactDetail = null;
		for (EntityContactDetailModel outletContactDetailModel : createRequest.getEntityContactDetailModels()) {
			outletContactDetail = new OutletContactDetail();
			entity.addOutletContactDetail(patchOutletContact(outletContactDetail, outletContactDetailModel));
		}
		Address address = new Address();
		entity.setAddress(EntityUtil.patchAddress(address, createRequest.getAddressModel(), genericRepository));
		entity.setRegNo(RandomCodeUtil.getForeverUniqueID("O", ""));
		entity.setCustomerType(CustomerType.OUTLET);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.ssms.
	 * hibernate.core.models.BaseModel, com.ssms.hibernate.core.models.CreateEntity)
	 */
	@Override
	protected void afterCreate(Outlet entity, CreateOutletRequest createRequest) {
		userExchangePublisher.produceUser(createRequest.getRegisterUserModel(), entity.getBranch().getGuid(),
				entity.getCreatedBy(), entity.getLastModifiedBy(), entity.getCreatedDate(),
				entity.getLastModifiedDate(), true, entity.getGuid(), UserType.BACKOFFICE, UserInfoUtil.getTenantId(),
				false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupUpdate(com.ssms.core.
	 * models.request.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Outlet setupUpdate(UpdateOutletRequest updateRequest, Outlet entity) {
		if (updateRequest.getRegisterUserModel() != null) {
			patchOutletRegistration(entity, updateRequest.getRegisterUserModel());
		}
		if (updateRequest.getOutletModel() != null) {
			patchOutlet(entity, updateRequest.getOutletModel());
		}
		if (updateRequest.getServiceBranchId() != null && updateRequest.getServiceBranchId() > 0) {
			Branch serviceBranch = new Branch();
			serviceBranch.setId(updateRequest.getServiceBranchId());
			entity.setBranch(serviceBranch);
		}
		if (updateRequest.getAddressModel() != null) {
			EntityUtil.patchAddress(entity.getAddress(), updateRequest.getAddressModel(), genericRepository);
		}
		if (updateRequest.getSupportingBrandGUIDs() != null && !updateRequest.getSupportingBrandGUIDs().isEmpty()) {
			updateSupportedBrands(entity, updateRequest.getSupportingBrandGUIDs());
		}
		if (updateRequest.getEntityContactDetailModels() != null
				&& !updateRequest.getEntityContactDetailModels().isEmpty()) {
			updateContactDetail(entity, updateRequest.getEntityContactDetailModels());
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
	protected void afterUpdate(Outlet entity, UpdateOutletRequest updateRequest) {
		if (updateRequest.getRegisterUserModel() != null) {
			userExchangePublisher.produceUser(updateRequest.getRegisterUserModel(), entity.getBranch().getGuid(),
					entity.getCreatedBy(), entity.getLastModifiedBy(), entity.getCreatedDate(),
					entity.getLastModifiedDate(), true, entity.getGuid(), UserType.BACKOFFICE,
					UserInfoUtil.getTenantId(), false);
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
	protected void afterActivateDeactivate(Outlet entity) {
		userExchangePublisher.produceUser(null, null, entity.getCreatedBy(), entity.getLastModifiedBy(),
				entity.getCreatedDate(), entity.getLastModifiedDate(), entity.getIsActive(), entity.getGuid(),
				UserType.BACKOFFICE, UserInfoUtil.getTenantId(), false);
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
				ApplicationLogger.logError("An error occurred while publishing end outlet delete, Cause : ", e);
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
				ApplicationLogger.logError("An error occurred while publishing end outlet restore, Cause : ", e);
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
			for (Outlet entity : genericRepository.findByCriteria(Outlet.class, criterias)) {
				userGUIDs.add(entity.getGuid());
			}
			return userGUIDs;
		}
		return null;
	}

	private void updateContactDetail(Outlet entity, Set<EntityContactDetailModel> updateEntityContactDetailModels) {
		OutletContactDetail outletContactDetail = null;
		List<Long> updatedOutletContactDetailIds = new ArrayList<>();
		for (EntityContactDetailModel outletContactDetailModel : updateEntityContactDetailModels) {
			// Updating existing outlet contact detail if outletContactDetail id is provided
			if (outletContactDetailModel.getId() != null && outletContactDetailModel.getId() > 0) {
				Optional<OutletContactDetail> opt = entity.getOutletContactDetails().stream()
						.filter(oldOutletContactDetail -> oldOutletContactDetail.getId()
								.equals(outletContactDetailModel.getId()))
						.findAny();
				if (opt.isPresent()) {
					updatedOutletContactDetailIds.add(opt.get().getId());
					patchOutletContact(opt.get(), outletContactDetailModel);
				}
			} else {
				// Inserting new outlet contact detail if outletContactDetail id is not
				// provided, assumption is that if id is not provided then it is a new contact
				// information to be added
				outletContactDetail = new OutletContactDetail();
				entity.addOutletContactDetail(patchOutletContact(outletContactDetail, outletContactDetailModel));
			}
		}
		// Remove outletContactDetail which has not been updated, Assumption is that if
		// a contact detail information is to be removed then the updated outlet contact
		// detail list will not have that entry
		for (Iterator<OutletContactDetail> it = entity.getOutletContactDetails().iterator(); it.hasNext();) {
			Long id = it.next().getId();
			if (id != null && !updatedOutletContactDetailIds.contains(id)) {
				it.remove();
			}
		}
	}

	private void updateSupportedBrands(Outlet entity, Set<String> supportingBrandGUIDs) {
		OutletSupportingBrand newOutletSupportingBrand = null;
		Set<Long> updatedOutletSupportingBrandIds = new HashSet<>();
		for (String supportingBrandGUID : supportingBrandGUIDs) {
			Optional<OutletSupportingBrand> opt = entity.getOutletSupportingBrands().stream()
					.filter(outletSupportingBrand -> outletSupportingBrand.getBrandGUID().equals(supportingBrandGUID))
					.findAny();
			if (opt.isPresent()) {
				updatedOutletSupportingBrandIds.add(opt.get().getId());
			} else {
				// Inserting new outlet supporting brand if outletSupportingBrand id is not
				// provided, assumption is that if id is not provided then it is a new contact
				// information to be added
				newOutletSupportingBrand = new OutletSupportingBrand();
				newOutletSupportingBrand.setBrandGUID(supportingBrandGUID);
				entity.addOutletSupportingBrand(newOutletSupportingBrand);
			}
		}
		// Remove outletSupportingBrand which has not been updated, Assumption is that
		// if a contact detail information is to be removed then the updated outlet
		// SupportingBrand detail list will not have that entry
		for (Iterator<OutletSupportingBrand> it = entity.getOutletSupportingBrands().iterator(); it.hasNext();) {
			Long id = it.next().getId();
			if (id != null && !updatedOutletSupportingBrandIds.contains(id)) {
				it.remove();
			}
		}

	}

	/**
	 * Patches data to Outlet User Registration from Register User Model
	 *
	 * @param entity
	 * @param registerUserModel
	 * @return
	 */
	private Outlet patchOutletRegistration(Outlet entity, RegisterUserModel registerUserModel) {
		entity.setName(registerUserModel.getName());
		entity.setUsername(registerUserModel.getUsername());
		entity.setEmailId(registerUserModel.getEmailId());
		entity.setMobileNo(registerUserModel.getMobileNo());
		return entity;
	}

	/**
	 * Patches data to Outlet from Outlet Model
	 *
	 * @param entity
	 * @param outletModel
	 * @return
	 */
	private Outlet patchOutlet(Outlet entity, OutletModel outletModel) {
		entity.setGstinNumber(outletModel.getGstinNumber());
		entity.setDescription(outletModel.getDescription());
		entity.setWebsiteURL(outletModel.getWebsiteURL());
		return entity;
	}

	/**
	 * Patches data to OutletContactDetail from Outlet Contact Model
	 *
	 * @param outletContactDetail
	 * @param entityContactDetailModel
	 * @return
	 */
	private OutletContactDetail patchOutletContact(OutletContactDetail outletContactDetail,
			EntityContactDetailModel entityContactDetailModel) {
		if (outletContactDetail.getId() == null) {
			outletContactDetail.setGuid(UUID.randomUUID().toString());
		}
		outletContactDetail.setIsSMSEnabled(entityContactDetailModel.getIsSMSEnabled());
		outletContactDetail.setIsEmailEnabled(entityContactDetailModel.getIsEmailEnabled());
		ContactDetail contactDetail = null;
		if (outletContactDetail.getContactDetail() == null) {
			contactDetail = new ContactDetail();
			contactDetail.setGuid(UUID.randomUUID().toString());
		} else {
			contactDetail = outletContactDetail.getContactDetail();
		}
		contactDetail.setContactPerson(entityContactDetailModel.getContactDetailModel().getContactPerson());
		contactDetail.setContactMobile(entityContactDetailModel.getContactDetailModel().getContactMobile());
		contactDetail.setContactEmailId(entityContactDetailModel.getContactDetailModel().getContactEmailId());
		contactDetail.setDesignation(entityContactDetailModel.getContactDetailModel().getDesignation());
		outletContactDetail.setContactDetail(contactDetail);
		return outletContactDetail;
	}

	@PostMapping("/update/{customerPageRoute}")
	public BusinessObjectModel updateWithRoute(
			@PathVariable(value = "customerPageRoute") CustomerPageRoute customerPageRoute,
			@RequestBody UpdateOutletRequest updateOutletRequest, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, CloneNotSupportedException {
		super.update(updateOutletRequest, request, response);
		switch (customerPageRoute) {
		case Receive_Page:
			return new BusinessObjectModel(4000l, "Receive", "Receive", "/essms-admin/customer/listwithforms",
					RouteType.List);
		case Consumption_Page:
			return new BusinessObjectModel(1100l, "Investor Cart", "Investor Cart",
					"/essms-inventory/investorcart/list", RouteType.List);
		}
		return null;
	}

}
