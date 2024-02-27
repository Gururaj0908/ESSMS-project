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

import com.essms.auth.business.UserRoleManagement;
import com.essms.auth.entities.Role;
import com.essms.auth.entities.SystemUser;
import com.essms.auth.entities.UserRole;
import com.essms.auth.models.list.ListUserRole;
import com.essms.auth.models.request.UpsertUserRoleRequest;
import com.essms.auth.repositories.UserRoleRepository;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Service
public class UserRoleManagementImpl implements UserRoleManagement {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private GenericRepository genericRepository;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.UserRoleManagement#upsertUserRole(com.ssms.auth.models
	 * .request.UpsertUserRoleRequest)
	 */
	@Override
	@Transactional
	public void upsertUserRole(UpsertUserRoleRequest upsertUserRoleRequest) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("guid", upsertUserRoleRequest.getUserGUID());
		SystemUser systemUser = genericRepository.findByCriteria(SystemUser.class, criterias).get(0);
		criterias = new HashMap<>();
		criterias.put("systemUser.guid", upsertUserRoleRequest.getUserGUID());
		userRoleRepository.delete(genericRepository.findByCriteria(UserRole.class, criterias));
		List<Role> roles = null;
		UserRole userRole = null;
		for (String roleGUID : upsertUserRoleRequest.getRoleGUIDs()) {
			criterias = new HashMap<>();
			criterias.put("guid", roleGUID);
			roles = genericRepository.findByCriteria(Role.class, criterias);
			if (roles == null || roles.isEmpty()) {
				continue;
			} else {
				userRole = new UserRole();
				criterias = new HashMap<>();
				criterias.put("guid", roleGUID);
				userRole.setSystemUser(systemUser);
				userRole.setRole(roles.get(0));
				userRoleRepository.save(userRole);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.UserRoleManagement#listByUserGUIDs(java.lang.String[])
	 */
	@Override
	public List<ListUserRole> listByUserGUIDs(String[] userGUIDs) {
		Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedUserGUIDs", StringUtils.join(userGUIDs, ","));
		List<ListUserRole> listUserRoles = new ArrayList<>();
		ListUserRole listUserRole = null;
		List<UserRole> userRoles = genericRepository.findByNamedQuery("findByUserGUIDs", namedParamers);
		for (UserRole userRole : userRoles) {
			listUserRole = new ListUserRole();
			listUserRole.setId(userRole.getId());
			listUserRole.setUserGUID(userRole.getSystemUser().getGuid());
			listUserRole.setRoleId(userRole.getRole().getId());
			listUserRole.setRoleName(userRole.getRole().getName());
			listUserRole.setRoleGUID(userRole.getRole().getGuid());
			listUserRoles.add(listUserRole);
		}
		return listUserRoles;
	}
}
