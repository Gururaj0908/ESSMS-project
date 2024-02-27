/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.util.List;

import com.essms.auth.entities.Role;
import com.essms.auth.models.request.CreateRoleRequest;
import com.essms.auth.models.request.UpdateRoleRequest;
import com.essms.hibernate.core.models.request.IdsRequest;

/**
 * @author gaurav
 *
 */
public interface RoleManagement {

	/**
	 *
	 * @return
	 */
	List<Role> getRoles();

	/**
	 *
	 * @param createRoleRequest
	 */
	void create(CreateRoleRequest createRoleRequest);

	/**
	 *
	 * @param updateRoleRequest
	 */
	void update(UpdateRoleRequest updateRoleRequest);

	/**
	 *
	 * @param idsRequest
	 */
	void delete(IdsRequest idsRequest);

}
