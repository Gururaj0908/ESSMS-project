/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essms.auth.business.BusinessObjectManagement;
import com.essms.auth.entities.BusinessObject;
import com.essms.auth.models.request.UpsertBusiessObjectRequest;
import com.essms.core.locale.Translator;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Service
public class BusinessObjectManagementImpl implements BusinessObjectManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private Translator translator;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.auth.business.BusinessObjectManagement#getAll()
	 */
	@Override
	public List<BusinessObject> getAll() {
		return genericRepository.findByNamedQuery("findAllActive", null);
	}

	@Override
	public List<BusinessObject> getAllSelectable() {
		return genericRepository.findByNamedQuery("findAllActiveNSelectable", null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.auth.business.BusinessObjectManagement#upsert(com.ssms.auth.
	 * models.request.UpsertBusiessObjectRequest)
	 */
	@Override
	public void upsert(UpsertBusiessObjectRequest upsertBusiessObjectRequest) {
		final BusinessObject businessObject = new BusinessObject();
		businessObject.setId(upsertBusiessObjectRequest.getId());
		businessObject.setAction(upsertBusiessObjectRequest.getMenuAction());
		businessObject.setDisplayOrder(upsertBusiessObjectRequest.getDisplayOrder());
		businessObject.setDisplayTag(upsertBusiessObjectRequest.getDisplayTag());
		businessObject.setIsHidden(upsertBusiessObjectRequest.getIsHidden());
		businessObject.setObjectLevel(upsertBusiessObjectRequest.getObjectLevel());
		businessObject.setObjectName(upsertBusiessObjectRequest.getObjectName());
		businessObject.setUrl(upsertBusiessObjectRequest.getUrl());
		if (upsertBusiessObjectRequest.getParentId() != null) {
			final BusinessObject parentBusinessObject = new BusinessObject();
			parentBusinessObject.setId(upsertBusiessObjectRequest.getParentId());
			businessObject.setParent(parentBusinessObject);
		}
		businessObject.setIsDeleted(Boolean.FALSE);
		genericRepository.saveOrUpdate(businessObject);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.BusinessObjectManagement#getByRoleAccess(java.lang.
	 * String[])
	 */

	@Override
	public List<BusinessObject> getByRoleAccess(String[] roleGUIDs) {
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedRoles", StringUtils.join(roleGUIDs, ","));
		return genericRepository.findByNamedQuery("findByRoleAccess", namedParamers);
	}

	@Override
	public List<BusinessObject> getByRoleAccessForMobile(String[] roleGUIDs) {
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedRoles", StringUtils.join(roleGUIDs, ","));
		return genericRepository.findByNamedQuery("findByRoleAccessForMobile", namedParamers);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.BusinessObjectManagement#getByUserAccess(java.lang.
	 * String)
	 */
	@Override
	public List<BusinessObject> getByUserAccess(String userGUID) {
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("userGUID", userGUID);
		List<BusinessObject> businessObjects = genericRepository.findByNamedQuery("findByUserAccess", namedParamers);
		for (BusinessObject businessObject : businessObjects) {
			businessObject.setDisplayTag(translator.toLocale(businessObject.getDisplayTag()));
		}
		return businessObjects;
	}

	@Override
	public List<BusinessObject> getByUserAccessForMobile(String userGUID) {
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("userGUID", userGUID);
		return genericRepository.findByNamedQuery("findByUserAccessForMobile", namedParamers);
	}

	@Override
	public List<BusinessObject> getByUserNBranchAccess(String branchGUID, String userGUID) {
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("userGUID", userGUID);
		namedParamers.put("branchGUID", branchGUID);
		return genericRepository.findByNamedQuery("findByUserNBranchAccess", namedParamers);
	}

	@Override
	public List<BusinessObject> getByUserNBranchAccessForMobile(String branchGUID, String userGUID) {
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("userGUID", userGUID);
		namedParamers.put("branchGUID", branchGUID);
		return genericRepository.findByNamedQuery("findByUserNBranchAccessForMobile", namedParamers);
	}

}
