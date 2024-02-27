/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.util.List;

import com.essms.auth.models.list.ListUserRole;
import com.essms.auth.models.request.UpsertUserRoleRequest;
import com.essms.core.exception.ApplicationException;

/**
 * @author gaurav
 *
 */
public interface UserRoleManagement {

	/**
	 *
	 * @param upsertUserRoleRequest
	 * @throws ApplicationException
	 */
	void upsertUserRole(UpsertUserRoleRequest upsertUserRoleRequest);

	/**
	 *
	 * @param userGUIDs
	 * @return
	 */
	List<ListUserRole> listByUserGUIDs(String[] userGUIDs);

}
