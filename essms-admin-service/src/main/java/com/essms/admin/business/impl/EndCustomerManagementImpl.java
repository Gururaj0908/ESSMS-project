/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import com.essms.admin.business.EndCustomerManagement;
import com.essms.admin.business.FileManagement;
import com.essms.admin.entities.Address;
import com.essms.admin.entities.Branch;
import com.essms.admin.entities.EndCustomer;
import com.essms.admin.entities.EndCustomerAddress;
import com.essms.admin.entities.EndCustomerPreference;
import com.essms.admin.models.AddressModel;
import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.EndCustomerModel;
import com.essms.admin.models.EndCustomerUserModel;
import com.essms.admin.models.request.CreateEndCustomerRequest;
import com.essms.admin.models.request.UpdateEndCustomerRequest;
import com.essms.admin.models.response.UploadFileResponse;
import com.essms.admin.publishers.UserExchangePublisher;
import com.essms.admin.utils.EntityUtil;
import com.essms.core.enums.AddressType;
import com.essms.core.enums.CustomerType;
import com.essms.core.enums.OfficeType;
import com.essms.core.enums.UserType;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.RandomCodeUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.business.SystemUserManagement;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.queue.EndCustomerRegistration;
import com.essms.hibernate.core.repository.GenericRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
@Service
public class EndCustomerManagementImpl implements EndCustomerManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private FileManagement fileManagement;

	@Autowired
	private SystemUserManagement systemUserManagement;

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Value("${" + RolePropertyConstant.ADMIN_ROLE + "}")
	private String adminRoleGUID;

	/**
	 *
	 */
	@Override
	public EndCustomer createEndCustomer(CreateEndCustomerRequest createRequest, EndCustomer entity, String roleGUIDs) {
		try {
			if (createRequest.getEndCustomerUserModel() != null
					&& (createRequest.getEndCustomerUserModel().getIsWalkin() == null
							|| !createRequest.getEndCustomerUserModel().getIsWalkin())) {
				if (systemUserManagement.getUser(createRequest.getEndCustomerUserModel().getUsername(),
						createRequest.getEndCustomerUserModel().getEmailId(),
						createRequest.getEndCustomerUserModel().getMobileNo(), UserInfoUtil.getTenantId()) != null) {
					List<FieldError> fieldErrors = new ArrayList<>();
					FieldError fieldError = new FieldError("EndCustomer", "name", "User Already Registered");
					fieldErrors.add(fieldError);
					throw new ValidationException(fieldErrors);
				}
			}
		} catch (JsonProcessingException | RestClientException | JSONException e) {
			ApplicationLogger.logError("An error occurred while checking user exist in EndCustomer", e);
			throw new ApplicationException(e);
		}
		patchEndCustomerRegistration(entity, createRequest.getEndCustomerUserModel());
		if (createRequest.getEndCustomerModel() != null) {
			patchEndCustomer(entity, createRequest.getEndCustomerModel());
		}
		if (createRequest.getEndCustomerAddressModels() != null
				&& createRequest.getEndCustomerAddressModels().size() > 0) {
			for (EndCustomerAddressModel customerAddressModel : createRequest.getEndCustomerAddressModels()) {
				entity.addEndCustomerAddress(createEndCustomerAddress(customerAddressModel.getAddressModel(),
						customerAddressModel.getAddressType(), customerAddressModel.getIsDefault()));
			}
		}
		entity.setCustomerType(CustomerType.INDIVIDUAL);
		if (createRequest.getEndCustomerModel() != null && createRequest.getEndCustomerModel().getBranchId() == null) {
			// setting main branch
			Map<String, Object> criterias = new HashMap<>();
			criterias.put("officeType", OfficeType.MAIN_BRANCH);
			List<Branch> branches = genericRepository.findByCriteria(Branch.class, criterias);
			if (branches == null || branches.isEmpty()) {
				throw new ValidationException(
						FieldErrorUtil.generateFieldErrors(CreateEndCustomerRequest.class.getSimpleName(), "branchGUID",
								"No Default Main Branch Set, Please contact site administrator"));
			} else {
				entity.setBranch(branches.get(0));
			}
		}
		entity.setGuid(UUID.randomUUID().toString());
		entity.setRegNo(RandomCodeUtil.getForeverUniqueID("C", ""));
		// Creating customer preference
		EndCustomerPreference endCustomerPreference = new EndCustomerPreference();
		endCustomerPreference.setEndCustomer(entity);
		endCustomerPreference.setGuid(UUID.randomUUID().toString());
		entity.setEndCustomerPreference(endCustomerPreference);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.admin.business.EndCustomerManagement#afterCreate(com.ssms.admin.
	 * entities.EndCustomer, com.ssms.admin.models.request.CreateEndCustomerRequest)
	 */
	@Override
	public void afterCreate(EndCustomer entity, CreateEndCustomerRequest createRequest) {
		// Registering user on auth server
		if (createRequest.getEndCustomerUserModel() != null && !createRequest.getEndCustomerUserModel().getIsWalkin()) {
			RegisterUserModel registerUserModel = new RegisterUserModel();
			BeanUtils.copyProperties(createRequest.getEndCustomerUserModel(), registerUserModel);
			userExchangePublisher.produceUser(registerUserModel, entity.getBranch().getGuid(), entity.getCreatedBy(),
					entity.getLastModifiedBy(), entity.getCreatedDate(), entity.getLastModifiedDate(), true,
					entity.getGuid(), UserType.CUSTOMER, UserInfoUtil.getTenantId(), false);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.admin.business.EndCustomerManagement#updateEndCustomer(com.ssms.
	 * admin.models.request.UpdateEndCustomerRequest,
	 * com.ssms.admin.entities.EndCustomer)
	 */
	@Override
	public EndCustomer updateEndCustomer(UpdateEndCustomerRequest updateRequest, EndCustomer entity) {

		if (updateRequest.getEndCustomerUserModel() != null) {
			patchEndCustomerRegistration(entity, updateRequest.getEndCustomerUserModel());
		}
		if (updateRequest.getEndCustomerModel() != null) {
			patchEndCustomer(entity, updateRequest.getEndCustomerModel());
		}
		if (updateRequest.getEndCustomerAddressModels() != null
				&& !updateRequest.getEndCustomerAddressModels().isEmpty()) {
			updateAddress(entity, updateRequest.getEndCustomerAddressModels());
		}
		return entity;

	}

	private EndCustomerAddress createEndCustomerAddress(AddressModel addressModel, AddressType addressType,
			Boolean isDefault) {
		EndCustomerAddress customerAddress = new EndCustomerAddress();
		customerAddress.setAddressType(addressType);
		customerAddress.setIsDefault(isDefault);
		Address address = new Address();
		customerAddress.setGuid(UUID.randomUUID().toString());
		customerAddress.setAddress(EntityUtil.patchAddress(address, addressModel, genericRepository));
		return customerAddress;
	}

	/**
	 * Patches data to End Customer User Registration from Register User Model
	 *
	 * @param address
	 * @return
	 */
	private EndCustomer patchEndCustomerRegistration(EndCustomer entity, EndCustomerUserModel endCustomeUserModel) {
		if (StringUtils.isNotBlank(endCustomeUserModel.getName())) {
			entity.setName(endCustomeUserModel.getName());
		}
		if (StringUtils.isNotBlank(endCustomeUserModel.getUsername())) {
			entity.setUsername(endCustomeUserModel.getUsername());
		}
		if (StringUtils.isNotBlank(endCustomeUserModel.getEmailId())) {
			entity.setEmailId(endCustomeUserModel.getEmailId());
		}
		if (StringUtils.isNotBlank(endCustomeUserModel.getMobileNo())) {
			entity.setMobileNo(endCustomeUserModel.getMobileNo());
		}
		return entity;
	}

	/**
	 * Patches data to End Customer from Vendor Model
	 *
	 * @param address
	 * @return
	 */
	private EndCustomer patchEndCustomer(EndCustomer entity, EndCustomerModel endCustomerModel) {
		if (StringUtils.isNotBlank(endCustomerModel.getGstinNumber())) {
			entity.setGstinNumber(endCustomerModel.getGstinNumber());
		}
		if (StringUtils.isNotBlank(endCustomerModel.getAltMobileNo())) {
			entity.setAltMobileNo(endCustomerModel.getAltMobileNo());
		}
		if (endCustomerModel.getAnniversaryDate() != null) {
			entity.setAnniversaryDate(endCustomerModel.getAnniversaryDate());
		}
		if (endCustomerModel.getBirthDate() != null) {
			entity.setBirthDate(endCustomerModel.getBirthDate());
		}
		if (endCustomerModel.getGenderType() != null) {
			entity.setGenderType(endCustomerModel.getGenderType());
		}
		if (endCustomerModel.getMaritalStatus() != null) {
			entity.setMaritalStatus(endCustomerModel.getMaritalStatus());
		}
		if (endCustomerModel.getBranchId() != null) {
			entity.setBranch(genericRepository.getById(Branch.class, endCustomerModel.getBranchId()));
		}
		return entity;
	}

	@Override
	public void updateAddress(EndCustomer entity, List<EndCustomerAddressModel> customerAddressModels) {
		List<Long> updatedCustomerAddressIds = new ArrayList<>();
		for (EndCustomerAddressModel customerAddressModel : customerAddressModels) {
			// Updating existing customer address detail if customerContactDetail id is
			// provided
			if (customerAddressModel.getId() != null && customerAddressModel.getId() > 0) {
				Optional<EndCustomerAddress> opt = entity.getEndCustomerAddresses().stream()
						.filter(oldCustomerAddress -> oldCustomerAddress.getId().equals(customerAddressModel.getId()))
						.findAny();
				if (opt.isPresent()) {
					updatedCustomerAddressIds.add(opt.get().getId());
					if (customerAddressModel.getAddressType() != null) {
						opt.get().setAddressType(customerAddressModel.getAddressType());
					}
					if (customerAddressModel.getIsDefault() != null) {
						opt.get().setIsDefault(customerAddressModel.getIsDefault());
					}
					if (StringUtils.isNotBlank(customerAddressModel.getAddressModel().getAddressLine2())) {
						opt.get().getAddress()
								.setAddressLine2(customerAddressModel.getAddressModel().getAddressLine2());
					}
					EntityUtil.patchAddress(opt.get().getAddress(), customerAddressModel.getAddressModel(),
							genericRepository);
				}
			} else {
				// Inserting new customer address if customerAddress id is not
				// provided, assumption is that if id is not provided then it is a new contact
				// information to be added
				entity.addEndCustomerAddress(createEndCustomerAddress(customerAddressModel.getAddressModel(),
						customerAddressModel.getAddressType(), customerAddressModel.getIsDefault()));
			}
		}
		// Remove customerAddress which has not been updated, Assumption is that if
		// a customer address information is to be removed then the updated customer
		// address list will not have that entry
		for (Iterator<EndCustomerAddress> it = entity.getEndCustomerAddresses().iterator(); it.hasNext();) {
			Long id = it.next().getId();
			if (id != null && !updatedCustomerAddressIds.contains(id)) {
				it.remove();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.admin.business.EndCustomerManagement#detail(java.lang.String)
	 */
	@Override
	public EndCustomerRegistration detail(String customerGUID) {
		EndCustomer endCustomer = genericRepository.getByGUID(EndCustomer.class, customerGUID);
		EndCustomerRegistration endCustomerRegistration = new EndCustomerRegistration();
		com.essms.hibernate.core.models.queue.EndCustomerModel endCustomerModel = new com.essms.hibernate.core.models.queue.EndCustomerModel();
		endCustomerModel.setAltMobileNo(endCustomer.getAltMobileNo());
		endCustomerModel.setGenderType(endCustomer.getGenderType());
		endCustomerModel.setBirthDate(endCustomer.getBirthDate());
		endCustomerModel.setMaritalStatus(endCustomer.getMaritalStatus());
		endCustomerModel.setBranchId(endCustomer.getBranch().getId());
		endCustomerModel.setGuid(endCustomer.getGuid());
		endCustomerModel.setRegNo(endCustomer.getRegNo());
		endCustomerModel.setAnniversaryDate(endCustomer.getAnniversaryDate());
		endCustomerRegistration.setEndCustomerModel(endCustomerModel);
		com.essms.hibernate.core.models.queue.RegisterUserModel registerUserModel = new com.essms.hibernate.core.models.queue.RegisterUserModel();
		registerUserModel.setEmailId(endCustomer.getEmailId());
		registerUserModel.setMobileNo(endCustomer.getMobileNo());
		registerUserModel.setName(endCustomer.getName());
		registerUserModel.setUsername(endCustomer.getUsername());
		endCustomerRegistration.setRegisterUserModel(registerUserModel);
		endCustomerRegistration
				.setEndCustomerAddressModels(populateEndCustomerAddresses(endCustomer.getEndCustomerAddresses()));
		return endCustomerRegistration;
	}

	private List<com.essms.hibernate.core.models.queue.EndCustomerAddressModel> populateEndCustomerAddresses(
			List<EndCustomerAddress> endCustomerAddresses) {
		List<com.essms.hibernate.core.models.queue.EndCustomerAddressModel> endCustomerAddressModels = null;
		com.essms.hibernate.core.models.queue.EndCustomerAddressModel endCustomerAddressModel = null;
		if (endCustomerAddresses != null && endCustomerAddresses.size() > 0) {
			endCustomerAddressModels = new ArrayList<>();
			for (EndCustomerAddress endCustomerAddress : endCustomerAddresses) {
				endCustomerAddressModel = new com.essms.hibernate.core.models.queue.EndCustomerAddressModel();
				endCustomerAddressModel.setAddressType(endCustomerAddress.getAddressType());
				endCustomerAddressModel.setIsDefault(endCustomerAddress.getIsDefault());
				endCustomerAddressModel.setAddressModel(populateAddressModel(endCustomerAddress.getAddress()));
				endCustomerAddressModels.add(endCustomerAddressModel);
			}
		}
		return endCustomerAddressModels;
	}

	private com.essms.hibernate.core.models.queue.AddressModel populateAddressModel(Address address) {
		com.essms.hibernate.core.models.queue.AddressModel addressModel = new com.essms.hibernate.core.models.queue.AddressModel();
		addressModel.setAddressLine1(address.getAddressLine1());
		addressModel.setAddressLine2(address.getAddressLine2());
		addressModel.setAreaId(address.getArea() == null ? null : address.getArea().getId());
		addressModel.setCityId(address.getCity() == null ? null : address.getCity().getId());
		addressModel.setFaxNo(address.getFaxNo());
		addressModel.setLatitude(address.getLatitude());
		addressModel.setLongitude(address.getLongitude());
		addressModel.setNearestLandMark(address.getNearestLandMark());
		addressModel.setPhoneNo(address.getPhoneNo());
		addressModel.setPinCode(address.getPinCode());
		addressModel.setStateId(address.getCity() == null ? null : address.getCity().getState().getId());
		return addressModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.admin.business.EndCustomerManagement#uploadImage(org.
	 * springframework.web.multipart.MultipartFile)
	 */
	@Override
	public UploadFileResponse uploadImage(MultipartFile multipartFile) throws IOException {
		EndCustomer endCustomer = genericRepository.getByGUID(EndCustomer.class, UserInfoUtil.getUserGUID());
		UploadFileResponse uploadFileResponse = new UploadFileResponse();
		uploadFileResponse
				.setPath(fileManagement.saveUploadedFile(multipartFile, "/profile/" + endCustomer.getRegNo()));
		endCustomer.setImagePath(uploadFileResponse.getPath());
		genericRepository.saveOrUpdate(endCustomer);
		return uploadFileResponse;
	}

}
