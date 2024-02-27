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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essms.auth.business.BusinessObjectManagement;
import com.essms.auth.business.RolePermissionManagement;
import com.essms.auth.entities.BusinessObject;
import com.essms.auth.entities.Role;
import com.essms.auth.entities.RolePermission;
import com.essms.auth.models.menu.BusinessObjectTree;
import com.essms.auth.models.menu.BusinessObjectTreeHelper;
import com.essms.auth.models.request.UpsertRolePermissionRequest;
import com.essms.auth.models.response.MenuTreeResponse;
import com.essms.auth.repositories.RolePermissionRepository;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Service
public class RolePermissionManagementImpl implements RolePermissionManagement {

	@Autowired
	private BusinessObjectManagement businessObjectManagement;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private GenericRepository genericRepository;

	@Value("${" + RolePropertyConstant.SUPER_ADMIN_ROLE + "}")
	private String superAdminRoleGUID;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.RolePermissionManagement#upsertRolePermission(com.
	 * ssms.auth.models.request.UpsertRolePermissionRequest)
	 */
	@Override
	@Transactional
	public void upsertRolePermission(UpsertRolePermissionRequest upsertRolePermissionRequest) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("role.guid", upsertRolePermissionRequest.getRoleGUID());
		List<RolePermission> rolePermissions = genericRepository.findByCriteria(RolePermission.class, criterias);
		genericRepository.deleteAll(rolePermissions);
		Role role = genericRepository.getByGUID(Role.class, upsertRolePermissionRequest.getRoleGUID());
		BusinessObject businessObject = null;
		RolePermission rolePermission = null;
		for (Long businessObjectId : upsertRolePermissionRequest.getIds()) {
			rolePermission = new RolePermission();
			businessObject = new BusinessObject();
			businessObject.setId(businessObjectId);
			rolePermission.setRole(role);
			rolePermission.setBusinessObject(businessObject);
			rolePermissionRepository.save(rolePermission);
		}
	}

	@Override
	public MenuTreeResponse getMenuTree(String roleGUID, Boolean viewOnly) {
		MenuTreeResponse menuTreeResponse = new MenuTreeResponse();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("role.guid", roleGUID);
		List<Long> selectedIds = null;
		List<RolePermission> rolePermissions = genericRepository.findByCriteria(RolePermission.class, criterias);
		if (rolePermissions != null && rolePermissions.size() > 0) {
			selectedIds = new ArrayList<>();
			for (RolePermission rolePermission : rolePermissions) {
				selectedIds.add(rolePermission.getBusinessObject().getId());
			}
		}
		BusinessObjectTree businessObjectTree = null;
		if (!UserInfoUtil.getUserRoleGUIDs().contains(superAdminRoleGUID)) {
			businessObjectTree = BusinessObjectTreeHelper.getMenuTree(businessObjectManagement.getAllSelectable(),
					selectedIds);
		} else {
			businessObjectTree = BusinessObjectTreeHelper.getMenuTree(businessObjectManagement.getAll(), selectedIds);
		}
		menuTreeResponse.setBusinessObjectModel(businessObjectTree.getRootElement());
		Map<String, Object> configuration = new HashMap<>();
		configuration.put("titleKey", "displayTag");
		configuration.put("subtitleKey", "routeType");
		configuration.put("tooltipKey", "url");
		configuration.put("parentIdKey", "parentId");
		configuration.put("selectionAllowed", true);
		configuration.put("showChildrenCount", true);
		configuration.put("submitUrl", "/essms-auth/rolepermission/upsert");
		if (viewOnly != null) {
			configuration.put("disabled", true);
		}
		menuTreeResponse.setConfiguration(configuration);
		return menuTreeResponse;
	}
}
