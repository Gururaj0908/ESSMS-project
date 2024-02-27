/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.util.Set;

import com.essms.auth.entities.SystemUser;
import com.essms.auth.models.request.CheckUserPermissionRequest;
import com.essms.auth.models.request.UpsertUserPermissionRequest;
import com.essms.auth.models.response.CheckUserPermissionResponse;

/**
 * @author gaurav
 *
 */
public interface UserPermissionManagement {
	/**
	 *
	 * @param upsertUserPermissionRequest
	 * @param tenantId
	 */
	void upsertUserPermission(UpsertUserPermissionRequest upsertUserPermissionRequest);

	/**
	 *
	 * @param roleGUIDs
	 * @param systemUser
	 * @param syncToBMS
	 * @param tenantId
	 */
	void upsertUserRole(Set<String> roleGUIDs, SystemUser systemUser, Boolean syncToBMS, String tenantId);

	/**
	 * @param checkUserPermissionRequest
	 * @return
	 */
	CheckUserPermissionResponse checkUserPermission(CheckUserPermissionRequest checkUserPermissionRequest);
}
