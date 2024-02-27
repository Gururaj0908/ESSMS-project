/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.util.List;

import com.essms.auth.entities.BusinessObject;
import com.essms.auth.models.request.UpsertBusiessObjectRequest;

/**
 * @author gaurav
 *
 */
public interface BusinessObjectManagement {

	/**
	 * Get all businessObjects
	 *
	 * @return
	 */
	List<BusinessObject> getAll();

	/**
	 * Get all selectable businessObjects
	 *
	 * @return
	 */
	List<BusinessObject> getAllSelectable();

	/**
	 * Get all businessObjects assigned to roles Returns by the list of
	 *
	 * @param role Array of role names
	 * @return
	 */
	List<BusinessObject> getByRoleAccess(String[] role);

	/**
	 * Get all businessObjects assigned to the user
	 *
	 * @param userGUID
	 * @return
	 */
	List<BusinessObject> getByUserAccess(String userGUID);

	/**
	 *
	 * @param upsertBusiessObjectRequest
	 */
	void upsert(UpsertBusiessObjectRequest upsertBusiessObjectRequest);

	/**
	 * 
	 * @param userGUID
	 * @return
	 */
	List<BusinessObject> getByUserAccessForMobile(String userGUID);

	/**
	 * 
	 * @param branchGUID
	 * @param userGUID
	 * @return
	 */
	List<BusinessObject> getByUserNBranchAccessForMobile(String branchGUID, String userGUID);

	/**
	 * 
	 * @param branchGUID
	 * @param userGUID
	 * @return
	 */
	List<BusinessObject> getByUserNBranchAccess(String branchGUID, String userGUID);

	/**
	 * 
	 * @param roleGUIDs
	 * @return
	 */
	List<BusinessObject> getByRoleAccessForMobile(String[] roleGUIDs);

}
