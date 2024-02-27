/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import com.essms.auth.models.request.UpsertRolePermissionRequest;
import com.essms.auth.models.response.MenuTreeResponse;
import com.essms.core.exception.ApplicationException;

/**
 * @author gaurav
 *
 */
public interface RolePermissionManagement {
	/**
	 *
	 * @param upsertRolePermissionRequest
	 * @throws ApplicationException
	 */
	void upsertRolePermission(UpsertRolePermissionRequest upsertRolePermissionRequest);

	/**
	 * @param roleGUID
	 * @param viewOnly
	 * @return
	 */
	MenuTreeResponse getMenuTree(String roleGUID, Boolean viewOnly);
}
