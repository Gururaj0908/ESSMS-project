/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.essms.auth.entities.RolePermission;

/**
 * @author gaurav
 *
 */
public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {

	@Modifying
	@Query("delete from RolePermission rp where rp.role.id = ?1")
	void deleteInBulkByRoleId(long roleId);

	@Modifying
	@Query("delete from RolePermission rp where rp.role.guid = ?1")
	void deleteInBulkByRoleGUID(String roleGUID);

}
