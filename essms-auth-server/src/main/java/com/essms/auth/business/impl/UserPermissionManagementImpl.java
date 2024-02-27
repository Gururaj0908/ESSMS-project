/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essms.auth.business.TenantManagement;
import com.essms.auth.business.UserPermissionManagement;
import com.essms.auth.entities.BusinessObject;
import com.essms.auth.entities.Role;
import com.essms.auth.entities.SystemUser;
import com.essms.auth.entities.UserPermission;
import com.essms.auth.entities.UserRole;
import com.essms.auth.models.request.CheckUserPermissionRequest;
import com.essms.auth.models.request.UpsertUserPermissionRequest;
import com.essms.auth.models.response.CheckUserPermissionResponse;
import com.essms.auth.publishers.UserExchangePublisher;
import com.essms.auth.repositories.UserPermissionRepository;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.RedisUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.models.queue.SystemUserModel;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Service
public class UserPermissionManagementImpl implements UserPermissionManagement {

	private final String[] rolesToSync = new String[] { "c13c54d4-f23b-4c98-a343-26050ba49ad2" };

	@Value("${bms.tenant.id}")
	private String bmsTenantId;

	@Value("${" + RolePropertyConstant.SUPER_ADMIN_ROLE + "}")
	private String superAdminRoleGUID;

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private UserPermissionRepository userPermissionRepository;

	@Autowired
	private TenantManagement tenantManagement;

	@Autowired
	private RedisUtil redisUtil;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.UserPermissionManagement#upsertUserPermission(com.ssms
	 * .auth.models.request.UpsertUserPermissionRequest)
	 */
	@Override
	@Transactional
	public void upsertUserPermission(UpsertUserPermissionRequest upsertUserPermissionRequest) {
		userPermissionRepository.deleteInBulkByUserGUIDAndBranchGUID(upsertUserPermissionRequest.getUserGUID(),
				upsertUserPermissionRequest.getBranchGUID());
		redisUtil.deleteOne(UserInfoUtil.getTenantId() + ":" + upsertUserPermissionRequest.getUserGUID());
		UserPermission userPermission = null;
		BusinessObject businessObject = null;
		Map<String, Object> criterias = new HashMap<>();
		if (upsertUserPermissionRequest.getBusinessObjectIds() != null) {
			if (upsertUserPermissionRequest.getRoleGUIDs() != null
					&& upsertUserPermissionRequest.getRoleGUIDs().contains(superAdminRoleGUID)) {
				criterias.put("isSelectable", false);
				List<BusinessObject> businessObjects = genericRepository.findByCriteria(BusinessObject.class,
						criterias);
				for (BusinessObject businessObject2 : businessObjects) {
					upsertUserPermissionRequest.getBusinessObjectIds().add(businessObject2.getId());
				}
			}
			for (Long businessObjectId : upsertUserPermissionRequest.getBusinessObjectIds()) {
				userPermission = new UserPermission();
				userPermission.setUserGUID(upsertUserPermissionRequest.getUserGUID());
				userPermission.setBranchGUID(upsertUserPermissionRequest.getBranchGUID());
				businessObject = genericRepository.getById(BusinessObject.class, businessObjectId);
				userPermission.setBusinessObject(businessObject);
				userPermissionRepository.save(userPermission);
				if (StringUtils.isNotBlank(businessObject.getUrl())) {
					List<String> allowedUrls = Arrays.asList(businessObject.getUrl().split("\\s*,\\s*"));
					for (String str : allowedUrls) {
						redisUtil.putMap(UserInfoUtil.getTenantId() + ":" + userPermission.getUserGUID() + ":"
								+ UserInfoUtil.getBranchGUID(), str, str);
					}
				}
			}
		}
		criterias = new HashMap<>();
		criterias.put("guid", upsertUserPermissionRequest.getUserGUID());
		Boolean syncToBMS = false;
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase(bmsTenantId)) {
			syncToBMS = true;
			try {
				tenantManagement.publishUserProjectModuleAccess(upsertUserPermissionRequest.getUserGUID());
			} catch (Exception e) {
				ApplicationLogger.logError("Publish user module to MBO failed, Cause: ", e);
			}
		}
		upsertUserRole(upsertUserPermissionRequest.getRoleGUIDs(),
				genericRepository.findByCriteria(SystemUser.class, criterias).get(0), syncToBMS,
				UserInfoUtil.getTenantId());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.essms.auth.business.UserPermissionManagement#upsertUserRole(java.util.
	 * Set, com.essms.auth.entities.SystemUser, java.lang.Boolean)
	 */
	@Override
	@Transactional
	public void upsertUserRole(Set<String> roleGUIDs, SystemUser systemUser, Boolean syncToBMS, String tenantId) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("systemUser.guid", systemUser.getGuid());
		List<UserRole> userRoles = genericRepository.findByCriteria(UserRole.class, criterias);
		if (roleGUIDs == null || roleGUIDs.isEmpty()) {
			roleGUIDs = new HashSet<>();
		}
		Boolean found = false;
		for (String roleGUID : roleGUIDs) {
			if (syncToBMS && tenantId.equalsIgnoreCase(bmsTenantId) && !Arrays.asList(rolesToSync).contains(roleGUID)) {
				continue;
			}
			found = false;
			for (UserRole userRole : userRoles) {
				if (roleGUID.equals(userRole.getRole().getGuid())) {
					// This role is already set so skip it
					found = true;
					continue;
				}
			}
			if (!found) {
				UserRole userRole = new UserRole();
				criterias = new HashMap<>();
				criterias.put("guid", roleGUID);
				userRole.setRole(genericRepository.findByCriteria(Role.class, criterias).get(0));
				userRole.setSystemUser(systemUser);
				genericRepository.saveOrUpdate(userRole);
			}
		}
		// Unassigned role needs to be removed, assumption is if a role is not set in
		// the updated user role set and it
		// is previously present then the role assignment needs to be removed
		for (UserRole userRole : userRoles) {
			if (userRole.getRole().getGuid().equalsIgnoreCase(superAdminRoleGUID)) {
				continue;
			}
			if (!syncToBMS && !roleGUIDs.contains(userRole.getRole().getGuid())) {
				genericRepository.delete(userRole);
			}
			if (syncToBMS && tenantId.equalsIgnoreCase(bmsTenantId) && !roleGUIDs.contains(userRole.getRole().getGuid())
					&& Arrays.asList(rolesToSync).contains(userRole.getRole().getGuid())) {
				genericRepository.delete(userRole);
			}
		}
		if (syncToBMS && !tenantId.equalsIgnoreCase(bmsTenantId)) {
			syncUserToBMS(systemUser.getGuid(), roleGUIDs);
		}
	}

	private void syncUserToBMS(String userGUID, Set<String> roleGUIDs) {
		SystemUserModel systemUserModel = new SystemUserModel();
		systemUserModel.setSyncToBMS(true);
		systemUserModel.setTenantId(bmsTenantId);
		systemUserModel.setRoleGUIDs(roleGUIDs);
		systemUserModel.setUserGUID(userGUID);
		userExchangePublisher.produceUser(systemUserModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.UserPermissionManagement#checkUserPermission(com.
	 * essms.auth.models.request.CheckUserPermissionRequest)
	 */
	@Override
	public CheckUserPermissionResponse checkUserPermission(CheckUserPermissionRequest checkUserPermissionRequest) {
		CheckUserPermissionResponse checkUserPermissionResponse = new CheckUserPermissionResponse();
		Map<String, Object> criterias = new HashMap<>();
		System.out.println(checkUserPermissionRequest.getUserGUID());
		System.out.println(checkUserPermissionRequest.getUrl());
		criterias.put("userGUID", checkUserPermissionRequest.getUserGUID());
		criterias.put("branchGUID", checkUserPermissionRequest.getBranchGUID());
		criterias.put("businessObject.url", checkUserPermissionRequest.getUrl());
		List<UserPermission> userPermissions = genericRepository.findByCriteria(UserPermission.class, criterias);
		System.out.println(userPermissions);
		if (userPermissions != null && !userPermissions.isEmpty()) {
			System.out.println("true block");
			checkUserPermissionResponse.setHasPermission(Boolean.TRUE);
		} else {
			System.out.println("false block");
			checkUserPermissionResponse.setHasPermission(Boolean.FALSE);
		}
		return checkUserPermissionResponse;
	}
}
