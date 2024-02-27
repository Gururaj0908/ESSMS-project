/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essms.auth.business.RoleManagement;
import com.essms.auth.entities.Role;
import com.essms.auth.models.request.CreateRoleRequest;
import com.essms.auth.models.request.UpdateRoleRequest;
import com.essms.auth.repositories.RoleRepository;
import com.essms.hibernate.core.models.request.IdsRequest;

/**
 * @author gaurav
 *
 */
@Service
public class RoleManagementImpl implements RoleManagement {

	@Autowired
	private RoleRepository roleRepository;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.admin.managment.RoleManagement#getRoles(com.ssms.admin.models
	 * .requests.GetRoleRequest)
	 */
	@Override
	public List<Role> getRoles() {
		return roleRepository.findByIsDeletedFalse();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.admin.managment.RoleManagement#createRole(com.ssms.admin.
	 * models.requests.CreateRoleRequest)
	 */
	@Override
	public void create(CreateRoleRequest createRoleRequest) {
		Role role = new Role();
		role.setName(createRoleRequest.getName());
		roleRepository.save(role);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.admin.business.RoleManagement#update(com.ssms.admin.models.
	 * requests.UpdateRoleRequest)
	 */
	@Override
	public void update(UpdateRoleRequest updateRoleRequest) {
		Role role = roleRepository.findOne(updateRoleRequest.getId());
		role.setName(updateRoleRequest.getName());
		roleRepository.save(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssms.auth.business.RoleManagement#delete(com.ssms.hibernate.core.models.
	 * request.IdsRequest)
	 */
	@Override
	@Transactional
	public void delete(IdsRequest idsRequest) {
		for (Long id : idsRequest.getIds()) {
			roleRepository.delete(id);
		}
	}

}
