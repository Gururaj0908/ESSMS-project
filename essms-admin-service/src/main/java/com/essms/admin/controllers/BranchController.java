/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.entities.Address;
import com.essms.admin.entities.Area;
import com.essms.admin.entities.Branch;
import com.essms.admin.entities.City;
import com.essms.admin.models.list.ListBranch;
import com.essms.admin.models.request.CreateBranchRequest;
import com.essms.admin.models.request.UpdateBranchRequest;
import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.admin.publishers.LocationExchangePublisher;
import com.essms.core.logger.ApplicationLogger;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.queue.BranchModel;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/branch")
public class BranchController
		extends CRUDController<Branch, CreateBranchRequest, UpdateBranchRequest, ListBranch, SearchEntity> {

	@Autowired
	private LocationExchangePublisher locationExchangePublisher;

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
		headerLabelAndFieldPropertyMap.put("code", new FieldHeaderLabelAndEntityProperty("Code", "code"));
		headerLabelAndFieldPropertyMap.put("name", new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("officeType",
				new FieldHeaderLabelAndEntityProperty("Office Type", "officeType"));
		headerLabelAndFieldPropertyMap.put("isServiceCenter",
				new FieldHeaderLabelAndEntityProperty("Is Service Center", "isServiceCenter"));
		headerLabelAndFieldPropertyMap.put("area", new FieldHeaderLabelAndEntityProperty("Area", "area.name"));
		headerLabelAndFieldPropertyMap.put("city", new FieldHeaderLabelAndEntityProperty("City", "city.name"));
		headerLabelAndFieldPropertyMap.put("phoneNo", new FieldHeaderLabelAndEntityProperty("PhoneNo", "phoneNo"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListBranch> setupList(List<Branch> entities) {
		List<ListBranch> branches = new ArrayList<>();
		ListBranch listBranch = null;
		for (Branch branch : entities) {
			listBranch = new ListBranch();
			listBranch.setId(branch.getId());
			listBranch.setCode(branch.getCode());
			listBranch.setName(branch.getName());
			listBranch.setEmailId(branch.getEmailId());
			listBranch.setGstinNumber(branch.getGstinNumber());
			listBranch.setIsServiceCenter(branch.getIsServiceCenter());
			listBranch.setOfficeType(branch.getOfficeType());
			if (branch.getAddress() != null) {
				listBranch.setAddressId(branch.getAddress().getId());
				listBranch.setAddressLine1(branch.getAddress().getAddressLine1());
				listBranch.setAddressLine2(branch.getAddress().getAddressLine2());
				listBranch.setPinCode(branch.getAddress().getPinCode());
				if (branch.getAddress().getArea() != null) {
					listBranch.setAreaId(branch.getAddress().getArea().getId());
					listBranch.setArea(branch.getAddress().getArea().getName());
					listBranch.setCityId(branch.getAddress().getArea().getCity().getId());
					listBranch.setCity(branch.getAddress().getCity().getName());
					listBranch.setStateId(branch.getAddress().getArea().getCity().getState().getId());
					listBranch.setState(branch.getAddress().getCity().getState().getName());
				}
				listBranch.setNearestLandMark(branch.getAddress().getNearestLandMark());
				listBranch.setPhoneNo(branch.getAddress().getPhoneNo());
				listBranch.setFaxNo(branch.getAddress().getFaxNo());
				listBranch.setLatitude(branch.getAddress().getLatitude());
				listBranch.setLongitude(branch.getAddress().getLongitude());
			}
			listBranch.setIsActive(branch.getIsActive());
			branches.add(listBranch);
		}
		return branches;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Branch setupCreate(CreateBranchRequest createRequest, Branch entity) {
		entity.setCode(createRequest.getCode());
		entity.setEmailId(createRequest.getEmailId());
		entity.setGstinNumber(createRequest.getGstinNumber());
		entity.setIsServiceCenter(createRequest.getIsServiceCenter());
		entity.setName(createRequest.getName());
		entity.setOfficeType(createRequest.getOfficeType());
		Address address = new Address();
		address.setAddressLine1(createRequest.getAddressLine1());
		address.setAddressLine2(createRequest.getAddressLine2());
		address.setFaxNo(createRequest.getFaxNo());
		address.setLatitude(createRequest.getLatitude());
		address.setLongitude(createRequest.getLongitude());
		address.setNearestLandMark(createRequest.getNearestLandMark());
		address.setPhoneNo(createRequest.getPhoneNo());
		address.setPinCode(createRequest.getPinCode());
		if (createRequest.getCityId() != null && createRequest.getCityId() > 0) {
			City city = genericRepository.getById(City.class, createRequest.getCityId());
			address.setCity(city);
		}
		if (createRequest.getAreaId() != null && createRequest.getAreaId() > 0) {
			Area area = genericRepository.getById(Area.class, createRequest.getAreaId());
			address.setArea(area);
		}
		address.setGuid(UUID.randomUUID().toString());
		entity.setAddress(address);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.
	 * ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterCreate(Branch entity, CreateBranchRequest createRequest) {
		try {
			locationExchangePublisher.produceBranch(entity);
		} catch (AmqpException e) {
			ApplicationLogger.logError("An error occurred while publishing state, Cause : ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupUpdate(com.ssms.core.
	 * models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Branch setupUpdate(UpdateBranchRequest updateRequest, Branch entity) {
		if (StringUtils.isNotBlank(updateRequest.getCode())) {
			entity.setCode(updateRequest.getCode());
		}
		if (StringUtils.isNotBlank(updateRequest.getEmailId())) {
			entity.setEmailId(updateRequest.getEmailId());
		}
		if (StringUtils.isNotBlank(updateRequest.getGstinNumber())) {
			entity.setGstinNumber(updateRequest.getGstinNumber());
		}
		if (updateRequest.getIsServiceCenter() != null) {
			entity.setIsServiceCenter(updateRequest.getIsServiceCenter());
		}
		if (StringUtils.isNotBlank(updateRequest.getName())) {
			entity.setName(updateRequest.getName());
		}
		if (updateRequest.getOfficeType() != null) {
			entity.setOfficeType(updateRequest.getOfficeType());
		}
		if (entity.getAddress() == null) {
			entity.setAddress(new Address());
			entity.getAddress().setGuid(UUID.randomUUID().toString());
		}
		if (StringUtils.isNotBlank(updateRequest.getAddressLine1())) {
			entity.getAddress().setAddressLine1(updateRequest.getAddressLine1());
		}
		if (StringUtils.isNotBlank(updateRequest.getAddressLine2())) {
			entity.getAddress().setAddressLine2(updateRequest.getAddressLine2());
		}
		if (StringUtils.isNotBlank(updateRequest.getFaxNo())) {
			entity.getAddress().setFaxNo(updateRequest.getFaxNo());
		}
		if (updateRequest.getLatitude() != null && updateRequest.getLatitude() != 0) {
			entity.getAddress().setLatitude(updateRequest.getLatitude());
		}
		if (updateRequest.getLongitude() != null && updateRequest.getLongitude() != 0) {
			entity.getAddress().setLongitude(updateRequest.getLongitude());
		}
		if (StringUtils.isNotBlank(updateRequest.getNearestLandMark())) {
			entity.getAddress().setNearestLandMark(updateRequest.getNearestLandMark());
		}
		if (StringUtils.isNotBlank(updateRequest.getPhoneNo())) {
			entity.getAddress().setPhoneNo(updateRequest.getPhoneNo());
		}
		if (StringUtils.isNotBlank(updateRequest.getPinCode())) {
			entity.getAddress().setPinCode(updateRequest.getPinCode());
		}
		if (updateRequest.getCityId() != null && updateRequest.getCityId() > 0) {
			City city = genericRepository.getById(City.class, updateRequest.getCityId());
			entity.getAddress().setCity(city);
		}
		if (updateRequest.getAreaId() != null && updateRequest.getAreaId() > 0) {
			Area area = genericRepository.getById(Area.class, updateRequest.getAreaId());
			entity.getAddress().setArea(area);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterUpdate(com.ssms.
	 * hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterUpdate(Branch entity, UpdateBranchRequest updateRequest) {
		afterCreate(entity, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterActivateDeactivate(com
	 * .ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterActivateDeactivate(Branch entity) {
		afterCreate(entity, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterDelete(java.util.Set)
	 */
	@Override
	protected void afterHardDelete(Set<Long> ids) {
		try {
			entityOperationExchangePublisher.produceEntityOperation(ids, EntityOperationType.Deleted,
					BranchModel.class);
		} catch (AmqpException e) {
			ApplicationLogger.logError("An error occurred while publishing state, Cause : ", e);
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
		Map<String, Object> criterias = new HashMap<>();
		if (ids != null && ids.size() > 0) {
			if (ids.size() == 1) {
				criterias.put("id", ids.toArray()[0]);
			} else {
				criterias.put("id", StringUtils.join(ids, ","));
			}
			for (Branch entity : genericRepository.findByCriteria(Branch.class, criterias)) {
				afterCreate(entity, null);
			}
		}
	}

	@RequestMapping(value = "/detail/{branchGUID}", method = RequestMethod.GET)
	public ListBranch getByGUID(@PathVariable("branchGUID") String branchGUID) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("guid", branchGUID);
		List<ListBranch> listBranches = setupList(genericRepository.findByCriteria(Branch.class, criterias));
		if (listBranches != null && !listBranches.isEmpty()) {
			return listBranches.get(0);
		}
		return null;
	}

}
