/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.essms.auth.entities.SystemUser;
import com.essms.auth.entities.UserRole;

/**
 * @author gaurav
 *
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

	@Modifying
	@Query("delete from UserRole ur where ur.systemUser.guid =:#{#systemUser.guid}")
	void deleteInBulkByUserGUId(@Param("systemUser") SystemUser systemUser);

	@Modifying
	@Query("delete from UserRole ur where ur.systemUser.guid =:#{#systemUser.id}")
	void deleteInBulkByUserId(@Param("systemUser") SystemUser systemUser);

}
