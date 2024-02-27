/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.essms.auth.entities.UserPermission;

/**
 * @author gaurav
 *
 */
public interface UserPermissionRepository extends CrudRepository<UserPermission, Long> {

	@Modifying
	@Query("delete from UserPermission up where up.userGUID = ?1")
	void deleteInBulkByUserGUID(String userGUID);

	@Modifying
	@Query("delete from UserPermission up where up.userGUID = ?1 and up.branchGUID = ?2")
	void deleteInBulkByUserGUIDAndBranchGUID(String userGUID, String branchGUID);

}
