/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.essms.admin.business.InvestorManagement;
import com.essms.admin.business.UserManagement;
import com.essms.admin.entities.Address;
import com.essms.admin.entities.Area;
import com.essms.admin.entities.City;
import com.essms.admin.entities.Investor;
import com.essms.admin.models.list.ListInvestor;
import com.essms.admin.models.request.CreateInvestorRequest;
import com.essms.admin.models.request.UpdateInvestorRequest;
import com.essms.admin.utils.ExchangePublisherUtil;
import com.essms.admin.utils.ModelUtil;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.queue.SystemUserModel;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/investor")
public class InvestorController
		extends CRUDController<Investor, CreateInvestorRequest, UpdateInvestorRequest, ListInvestor, SearchEntity> {

	@Autowired
	private UserManagement userManagement;

	@Autowired
	private InvestorManagement investorManagement;

	@Autowired
	private ExchangePublisherUtil publisherUtil;

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
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListInvestor> setupList(List<Investor> entities) {
		List<ListInvestor> listInvestors = new ArrayList<>();
		ListInvestor listInvestor = null;
		RegisterUserModel registerUserModel = null;
		for (Investor investor : entities) {
			listInvestor = new ListInvestor();
			listInvestor.setId(investor.getId());
			listInvestor.setIsActive(investor.getIsActive());
			listInvestor.setJoiningDate(investor.getJoiningDate());
			registerUserModel = new RegisterUserModel();
			registerUserModel.setName(investor.getName());
			registerUserModel.setEmailId(investor.getEmailId());
			registerUserModel.setMobileNo(investor.getMobileNo());
			registerUserModel.setUsername(investor.getUsername());
			listInvestor.setRegisterUserModel(registerUserModel);
			listInvestor.setAddressModel(ModelUtil.generateAddressModel(investor.getAddress()));
			listInvestors.add(listInvestor);
		}
		return listInvestors;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Investor setupCreate(CreateInvestorRequest createRequest, Investor entity) {
		try {
			if (userManagement.getUser(createRequest.getRegisterUserModel().getUsername(),
					createRequest.getRegisterUserModel().getEmailId(),
					createRequest.getRegisterUserModel().getMobileNo()) != null) {
				List<FieldError> fieldErrors = new ArrayList<>();
				FieldError fieldError = new FieldError("Investor", "name", "User Already Registered");
				fieldErrors.add(fieldError);
				throw new ValidationException(fieldErrors);
			}
		} catch (JsonProcessingException | RestClientException | EncoderException | JSONException e) {
			ApplicationLogger.logError("An error occurred while checking user exist in Investor", e);
			throw new ApplicationException(e);
		}
		entity.setEmailId(createRequest.getRegisterUserModel().getEmailId());
		entity.setMobileNo(createRequest.getRegisterUserModel().getMobileNo());
		entity.setName(createRequest.getRegisterUserModel().getName());
		entity.setUsername(createRequest.getRegisterUserModel().getUsername());
		entity.setJoiningDate(createRequest.getJoiningDate());
		Address address = new Address();
		address.setAddressLine1(createRequest.getAddressModel().getAddressLine1());
		address.setAddressLine2(createRequest.getAddressModel().getAddressLine2());
		address.setFaxNo(createRequest.getAddressModel().getFaxNo());
		address.setNearestLandMark(createRequest.getAddressModel().getNearestLandMark());
		address.setPhoneNo(createRequest.getAddressModel().getPhoneNo());
		address.setPinCode(createRequest.getAddressModel().getPinCode());
		address.setLatitude(createRequest.getAddressModel().getLatitude());
		address.setLongitude(createRequest.getAddressModel().getLongitude());
		address.setGuid(UUID.randomUUID().toString());
		if (createRequest.getAddressModel().getAreaId() != null && createRequest.getAddressModel().getAreaId() > 0) {
			address.setArea(genericRepository.getById(Area.class, createRequest.getAddressModel().getAreaId()));
		}
		if (createRequest.getAddressModel().getCityId() != null && createRequest.getAddressModel().getCityId() > 0) {
			address.setCity(genericRepository.getById(City.class, createRequest.getAddressModel().getCityId()));
		}
		entity.setAddress(address);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.ssms.
	 * hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterCreate(Investor entity, CreateInvestorRequest createRequest) {
		userManagement.publishUser(entity, createRequest.getRegisterUserModel().getPassword());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupUpdate(com.ssms.core.
	 * models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Investor setupUpdate(UpdateInvestorRequest updateRequest, Investor entity) {
		if (updateRequest.getRegisterUserModel() != null) {
			entity.setEmailId(updateRequest.getRegisterUserModel().getEmailId());
			entity.setMobileNo(updateRequest.getRegisterUserModel().getMobileNo());
			entity.setName(updateRequest.getRegisterUserModel().getName());
			entity.setUsername(updateRequest.getRegisterUserModel().getUsername());
		}
		if (updateRequest.getJoiningDate() != null) {
			entity.setJoiningDate(updateRequest.getJoiningDate());
		}
		if (updateRequest.getAddressModel() != null) {
			Address address = entity.getAddress();
			address.setAddressLine1(updateRequest.getAddressModel().getAddressLine1());
			address.setAddressLine2(updateRequest.getAddressModel().getAddressLine2());
			address.setFaxNo(updateRequest.getAddressModel().getFaxNo());
			address.setNearestLandMark(updateRequest.getAddressModel().getNearestLandMark());
			address.setPhoneNo(updateRequest.getAddressModel().getPhoneNo());
			address.setPinCode(updateRequest.getAddressModel().getPinCode());
			if (updateRequest.getAddressModel().getLatitude() != null
					&& updateRequest.getAddressModel().getLatitude().longValue() != 0) {
				address.setLatitude(updateRequest.getAddressModel().getLatitude());
			}
			if (updateRequest.getAddressModel().getLongitude() != null
					&& updateRequest.getAddressModel().getLongitude().longValue() != 0) {
				address.setLongitude(updateRequest.getAddressModel().getLongitude());
			}
			if (updateRequest.getAddressModel().getAreaId() != null
					&& updateRequest.getAddressModel().getAreaId() > 0) {
				address.setArea(genericRepository.getById(Area.class, updateRequest.getAddressModel().getAreaId()));
			}
			if (updateRequest.getAddressModel().getCityId() != null
					&& updateRequest.getAddressModel().getCityId() > 0) {
				address.setCity(genericRepository.getById(City.class, updateRequest.getAddressModel().getCityId()));
			}
			entity.setAddress(address);
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
	protected void afterUpdate(Investor entity, UpdateInvestorRequest updateRequest) {
		userManagement.publishUser(entity, updateRequest.getRegisterUserModel() == null ? null
				: updateRequest.getRegisterUserModel().getPassword());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterActivateDeactivate(com
	 * .ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterActivateDeactivate(Investor entity) {
		userManagement.publishUser(entity, null);
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
			publisherUtil.produceEntityOperation(userGUIDS, EntityOperationType.Deleted, SystemUserModel.class);
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
			publisherUtil.produceEntityOperation(userGUIDS, EntityOperationType.Restored, SystemUserModel.class);
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
			for (Investor entity : genericRepository.findByCriteria(Investor.class, criterias)) {
				userGUIDs.add(entity.getGuid());
			}
			return userGUIDs;
		}
		return null;
	}

	@RequestMapping(value = "/hyperlink/{investorGUID}", method = RequestMethod.GET)
	public List<DetailModel> getInvestorDetail(@PathVariable("investorGUID") String investorGUID) {
		return investorManagement.getInvestorDetail(investorGUID);
	}

}
