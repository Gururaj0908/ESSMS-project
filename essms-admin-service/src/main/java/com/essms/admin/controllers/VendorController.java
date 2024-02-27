/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

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

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.essms.admin.entities.Address;
import com.essms.admin.entities.ContactDetail;
import com.essms.admin.entities.Vendor;
import com.essms.admin.entities.VendorAddress;
import com.essms.admin.entities.VendorContactDetail;
import com.essms.admin.models.EntityContactDetailModel;
import com.essms.admin.models.VendorAddressModel;
import com.essms.admin.models.VendorModel;
import com.essms.admin.models.list.ListVendor;
import com.essms.admin.models.request.CreateVendorRequest;
import com.essms.admin.models.request.UpdateVendorRequest;
import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.admin.publishers.UserExchangePublisher;
import com.essms.admin.utils.EntityUtil;
import com.essms.admin.utils.ModelUtil;
import com.essms.core.enums.UserType;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.business.SystemUserManagement;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
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
@RequestMapping("/vendor")
public class VendorController
		extends CRUDController<Vendor, CreateVendorRequest, UpdateVendorRequest, ListVendor, SearchEntity> {

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Autowired
	private SystemUserManagement systemUserManagement;

	@Autowired
	private GenericRepository genericRepository;

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
		final Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("registerUserModel.name",
				new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.username",
				new FieldHeaderLabelAndEntityProperty("Username", "username"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.mobileNo",
				new FieldHeaderLabelAndEntityProperty("Mobile No", "mobileNo"));
		headerLabelAndFieldPropertyMap.put("registerUserModel.emailId",
				new FieldHeaderLabelAndEntityProperty("Email", "emailId"));
		headerLabelAndFieldPropertyMap.put("vendor.gstinNumber",
				new FieldHeaderLabelAndEntityProperty("GSTIN No", "gstinNumber"));
		headerLabelAndFieldPropertyMap.put("vendor.vendorURL",
				new FieldHeaderLabelAndEntityProperty("Vendor URL", "vendorURL"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListVendor> setupList(List<Vendor> entities) {
		final List<ListVendor> listVendors = new ArrayList<>();
		ListVendor listVendor = null;
		RegisterUserModel registerUserModel = null;
		VendorModel vendorModel = null;
		List<VendorAddressModel> addressModels = null;
		VendorAddressModel vendorAddressModel = null;
		List<EntityContactDetailModel> createEntityContactDetailModels = null;
		EntityContactDetailModel createEntityContactDetailModel = null;
		for (final Vendor vendor : entities) {
			listVendor = new ListVendor();
			listVendor.setId(vendor.getId());
			registerUserModel = new RegisterUserModel();
			registerUserModel.setName(vendor.getName());
			registerUserModel.setUsername(vendor.getUsername());
			registerUserModel.setEmailId(vendor.getEmailId());
			registerUserModel.setMobileNo(vendor.getMobileNo());
			listVendor.setRegisterUserModel(registerUserModel);
			vendorModel = new VendorModel();
			vendorModel.setGstinNumber(vendor.getGstinNumber());
			vendorModel.setVendorURL(vendor.getVendorURL());
			vendorModel.setDescription(vendor.getDescription());
			listVendor.setVendorModel(vendorModel);
			addressModels = new ArrayList<>();
			for (final VendorAddress vendorAddress : vendor.getVendorAddresses()) {
				vendorAddressModel = new VendorAddressModel();
				vendorAddressModel.setId(vendorAddress.getId());
				vendorAddressModel.setOfficeType(vendorAddress.getOfficeType());
				vendorAddressModel.setAddressModel(ModelUtil.generateAddressModel(vendorAddress.getAddress()));
				createEntityContactDetailModels = new ArrayList<>();
				for (final VendorContactDetail vendorContactDetail : vendorAddress.getVendorContactDetails()) {
					createEntityContactDetailModel = new EntityContactDetailModel();
					createEntityContactDetailModel.setId(vendorContactDetail.getId());
					createEntityContactDetailModel.setIsSMSEnabled(vendorContactDetail.getIsSMSEnabled());
					createEntityContactDetailModel.setIsEmailEnabled(vendorContactDetail.getIsEmailEnabled());
					createEntityContactDetailModel.setContactDetailModel(
							ModelUtil.generateContactDetailModel(vendorContactDetail.getContactDetail()));
					createEntityContactDetailModels.add(createEntityContactDetailModel);
				}
				vendorAddressModel.setEntityContactDetailModels(createEntityContactDetailModels);
				addressModels.add(vendorAddressModel);
			}
			listVendor.setVendorAddressModel(addressModels);
			listVendor.setIsActive(vendor.getIsActive());
			listVendors.add(listVendor);
		}
		return listVendors;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Vendor setupCreate(CreateVendorRequest createRequest, Vendor entity) {
		try {
			if (systemUserManagement.getUser(createRequest.getRegisterUserModel().getUsername(),
					createRequest.getRegisterUserModel().getEmailId(),
					createRequest.getRegisterUserModel().getMobileNo(), UserInfoUtil.getTenantId()) != null) {
				final List<FieldError> fieldErrors = new ArrayList<>();
				final FieldError fieldError = new FieldError("Vendor", "registerUserModel.name",
						"User Already Registered");
				fieldErrors.add(fieldError);
				throw new ValidationException(fieldErrors);
			}
		} catch (JsonProcessingException | RestClientException | JSONException e) {
			ApplicationLogger.logError("An error occurred while checking user exist in Vendor", e);
			throw new ApplicationException(e);
		}
		patchVendorRegistration(entity, createRequest.getRegisterUserModel());
		if (createRequest.getVendorModel() != null) {
			patchVendor(entity, createRequest.getVendorModel());
		}
		for (final VendorAddressModel vendorAddressModel : createRequest.getVendorAddressModels()) {
			entity.addVendorAddress(generateVendorAddress(vendorAddressModel));
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
	protected void afterCreate(Vendor entity, CreateVendorRequest createRequest) {
		userExchangePublisher.produceUser(createRequest.getRegisterUserModel(), null, entity.getCreatedBy(),
				entity.getLastModifiedBy(), entity.getCreatedDate(), entity.getLastModifiedDate(), true,
				entity.getGuid(), UserType.BACKOFFICE, UserInfoUtil.getTenantId(), false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupUpdate(com.ssms.core.
	 * models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Vendor setupUpdate(UpdateVendorRequest updateRequest, Vendor entity) {
		if (updateRequest.getVendorModel() != null) {
			patchVendor(entity, updateRequest.getVendorModel());
		}
		if (updateRequest.getRegisterUserModel() != null) {
			patchVendorRegistration(entity, updateRequest.getRegisterUserModel());
		}
		if (updateRequest.getVendorAddressModels() != null && !updateRequest.getVendorAddressModels().isEmpty()) {
			updateAddress(entity, updateRequest.getVendorAddressModels());
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
	protected void afterUpdate(Vendor entity, UpdateVendorRequest updateRequest) {
		if (updateRequest.getRegisterUserModel() != null) {
			userExchangePublisher.produceUser(updateRequest.getRegisterUserModel(), null, entity.getCreatedBy(),
					entity.getLastModifiedBy(), entity.getCreatedDate(), entity.getLastModifiedDate(), true,
					entity.getGuid(), UserType.BACKOFFICE, UserInfoUtil.getTenantId(), false);
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
	protected void afterActivateDeactivate(Vendor entity) {
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
		final Set<?> userGUIDS = getUserGUIDs(ids);
		if (userGUIDS != null) {
			try {
				entityOperationExchangePublisher.produceEntityOperation(userGUIDS, EntityOperationType.Deleted,
						SystemUserModel.class);
			} catch (final AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing vendor delete, Cause : ", e);
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
				ApplicationLogger.logError("An error occurred while publishing vendor restore, Cause : ", e);
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
			for (final Vendor entity : genericRepository.findByCriteria(Vendor.class, criterias)) {
				userGUIDs.add(entity.getGuid());
			}
			return userGUIDs;
		}
		return null;
	}

	private void updateAddress(Vendor entity, List<VendorAddressModel> vendorAddressModels) {
		final List<Long> updatedVendorAddressIds = new ArrayList<>();
		for (final VendorAddressModel vendorAddressModel : vendorAddressModels) {
			// Updating existing vendor address detail if vendorContactDetail id is provided
			if (vendorAddressModel.getId() != null && vendorAddressModel.getId() > 0) {
				final Optional<VendorAddress> opt = entity.getVendorAddresses().stream()
						.filter(oldVendorAddress -> oldVendorAddress.getId().equals(vendorAddressModel.getId()))
						.findAny();
				if (opt.isPresent()) {
					updatedVendorAddressIds.add(opt.get().getId());
					opt.get().setOfficeType(vendorAddressModel.getOfficeType());
					EntityUtil.patchAddress(opt.get().getAddress(), vendorAddressModel.getAddressModel(),
							genericRepository);
					if (vendorAddressModel.getEntityContactDetailModels() != null
							&& !vendorAddressModel.getEntityContactDetailModels().isEmpty()) {
						updateContactDetail(opt.get(), vendorAddressModel.getEntityContactDetailModels());
					}
				}
			} else {
				// Inserting new vendor address if vendorAddress id is not
				// provided, assumption is that if id is not provided then it is a new contact
				// information to be added
				entity.addVendorAddress(generateVendorAddress(vendorAddressModel));
			}
		}
		// Remove vendorAddress which has not been updated, Assumption is that if
		// a vendor address information is to be removed then the updated vendor address
		// list will not have that entry
		for (final Iterator<VendorAddress> it = entity.getVendorAddresses().iterator(); it.hasNext();) {
			final Long id = it.next().getId();
			if (id != null && !updatedVendorAddressIds.contains(id)) {
				it.remove();
			}
		}
	}

	private VendorAddress generateVendorAddress(VendorAddressModel vendorAddressModel) {
		final VendorAddress vendorAddress = new VendorAddress();
		final Address address = new Address();
		VendorContactDetail vendorContactDetail = null;
		for (final EntityContactDetailModel vendorContactDetailModel : vendorAddressModel
				.getEntityContactDetailModels()) {
			vendorContactDetail = new VendorContactDetail();
			vendorAddress.addVendorContactDetail(patchVendorContact(vendorContactDetail, vendorContactDetailModel));
		}
		vendorAddress
				.setAddress(EntityUtil.patchAddress(address, vendorAddressModel.getAddressModel(), genericRepository));
		vendorAddress.setOfficeType(vendorAddressModel.getOfficeType());
		vendorAddress.setGuid(UUID.randomUUID().toString());
		return vendorAddress;
	}

	private void updateContactDetail(VendorAddress vendorAddress,
			List<EntityContactDetailModel> updateEntityContactDetailModels) {
		VendorContactDetail vendorContactDetail = null;
		final List<Long> updatedVendorContactDetailIds = new ArrayList<>();
		for (final EntityContactDetailModel vendorContactDetailModel : updateEntityContactDetailModels) {
			// Updating existing vendor contact detail if vendorContactDetail id is provided
			if (vendorContactDetailModel.getId() != null && vendorContactDetailModel.getId() > 0) {
				final Optional<VendorContactDetail> opt = vendorAddress.getVendorContactDetails().stream()
						.filter(oldVendorContactDetail -> oldVendorContactDetail.getId()
								.equals(vendorContactDetailModel.getId()))
						.findAny();
				if (opt.isPresent()) {
					updatedVendorContactDetailIds.add(opt.get().getId());
					patchVendorContact(opt.get(), vendorContactDetailModel);
				}
			} else {
				// Inserting new vendor contact detail if vendorContactDetail id is not
				// provided, assumption is that if id is not provided then it is a new contact
				// information to be added
				vendorContactDetail = new VendorContactDetail();
				vendorAddress.addVendorContactDetail(patchVendorContact(vendorContactDetail, vendorContactDetailModel));
			}
		}
		// Remove vendorContactDetail which has not been updated, Assumption is that if
		// a contact detail information is to be removed then the updated vendor contact
		// detail list will not have that entry
		for (final Iterator<VendorContactDetail> it = vendorAddress.getVendorContactDetails().iterator(); it
				.hasNext();) {
			final Long id = it.next().getId();
			if (id != null && !updatedVendorContactDetailIds.contains(id)) {
				it.remove();
			}
		}
	}

	/**
	 * Patches data to Vendor User Registration from Register User Model
	 *
	 * @param address
	 * @return
	 */
	private Vendor patchVendorRegistration(Vendor entity, RegisterUserModel registerUserModel) {
		entity.setName(registerUserModel.getName());
		entity.setUsername(registerUserModel.getUsername());
		entity.setEmailId(registerUserModel.getEmailId());
		entity.setMobileNo(registerUserModel.getMobileNo());
		return entity;
	}

	/**
	 * Patches data to Vendor from Vendor Model
	 *
	 * @param address
	 * @return
	 */
	private Vendor patchVendor(Vendor entity, VendorModel vendorModel) {
		entity.setGstinNumber(vendorModel.getGstinNumber());
		entity.setVendorURL(vendorModel.getVendorURL());
		entity.setDescription(vendorModel.getDescription());
		return entity;
	}

	/**
	 * Patches data to VendorContactDetail from Vendor Contact Model
	 *
	 * @param address
	 * @return
	 */
	private VendorContactDetail patchVendorContact(VendorContactDetail vendorContactDetail,
			EntityContactDetailModel entityContactDetailModel) {
		if (vendorContactDetail.getId() == null) {
			vendorContactDetail.setGuid(UUID.randomUUID().toString());
		}
		vendorContactDetail.setIsSMSEnabled(entityContactDetailModel.getIsSMSEnabled());
		vendorContactDetail.setIsEmailEnabled(entityContactDetailModel.getIsEmailEnabled());
		vendorContactDetail.setGuid(UUID.randomUUID().toString());
		ContactDetail contactDetail = null;
		if (vendorContactDetail.getContactDetail() != null) {
			contactDetail = vendorContactDetail.getContactDetail();
		} else {
			contactDetail = new ContactDetail();
			contactDetail.setGuid(UUID.randomUUID().toString());
		}
		contactDetail.setContactPerson(entityContactDetailModel.getContactDetailModel().getContactPerson());
		contactDetail.setContactMobile(entityContactDetailModel.getContactDetailModel().getContactMobile());
		contactDetail.setContactEmailId(entityContactDetailModel.getContactDetailModel().getContactEmailId());
		contactDetail.setDesignation(entityContactDetailModel.getContactDetailModel().getDesignation());
		vendorContactDetail.setContactDetail(contactDetail);
		return vendorContactDetail;
	}

}
