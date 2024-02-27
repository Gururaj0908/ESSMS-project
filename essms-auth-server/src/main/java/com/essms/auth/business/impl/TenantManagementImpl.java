/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.essms.auth.business.TenantManagement;
import com.essms.auth.entities.BusinessObject;
import com.essms.auth.entities.UserPermission;
import com.essms.auth.mbo.ProjectItem;
import com.essms.auth.mbo.ProjectItemTree;
import com.essms.auth.mbo.ProjectPermissionSync;
import com.essms.auth.mbo.ProjectTreeHelper;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Service
public class TenantManagementImpl implements TenantManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private ProjectTreeHelper projectTreeHelper;

	@Autowired
	private RestTemplate restTemplate;

	private static final String MBO_SYNC_PROJECT = "http://dotnet-mbo/ProjectItem/create";

	private static final String MBO_SYNC_USER_PROJECT = "http://dotnet-mbo/ProjectItem/updateUserPermission";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.auth.business.TenantManagement#publishTenantProjectToMBO(java
	 * .lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void publishTenantProjectToMBO(String title, String adminUserGUID) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("isDeleted", false);
		List<BusinessObject> businessObjects = genericRepository.findByCriteria(BusinessObject.class, criterias, "id",
				false);
		ProjectItemTree projectItemTree = projectTreeHelper.getProjectItemTree(businessObjects,
				UserInfoUtil.getTenantId(), adminUserGUID);
		ProjectItem projectItem = projectItemTree.getRootElement();
		projectItem = restTemplate.postForObject(MBO_SYNC_PROJECT, projectItem, ProjectItem.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.TenantManagement#publishUserProjectModuleAccess(java.
	 * lang. String)
	 */
	@Override
	public void publishUserProjectModuleAccess(String userGUID) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("userGUID", userGUID);
		List<BusinessObject> businessObjects = generate(
				genericRepository.findByCriteria(UserPermission.class, criterias, "businessObject.id", false));
		List<String> guids = new ArrayList<>();
		BusinessObject temp = null;
		for (BusinessObject businessObject : businessObjects) {
			temp = genericRepository.getById(BusinessObject.class, businessObject.getId());
			if (StringUtils.isNotBlank(temp.getGuid()))
				guids.add(temp.getGuid());
		}
		ProjectPermissionSync projectPermissionSync = new ProjectPermissionSync();
		projectPermissionSync.setUserId(userGUID);
		projectPermissionSync.setProjectItemIds(guids);
		restTemplate.put(MBO_SYNC_USER_PROJECT, projectPermissionSync);
	}

	private List<BusinessObject> generate(List<UserPermission> userPermissions) {
		List<BusinessObject> businessObjects = new ArrayList<>();
		for (UserPermission userPermission : userPermissions) {
			businessObjects.add(userPermission.getBusinessObject());
		}
		return businessObjects;
	}

}
