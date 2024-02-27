/**
 * This file is subject to the terms and conditions, as well as 
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.essms.auth.entities.Role;

/**
 * @author gaurav
 *
 */
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

	/**
	 * Select only active roles
	 * @return
	 */
	List<Role> findByIsDeletedFalse();
}
