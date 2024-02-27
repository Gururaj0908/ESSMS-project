/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

/**
 * TODO Move to BMS
 * 
 * @author gaurav
 *
 */
public interface TenantManagement {

	/**
	 * @param title
	 * @param adminUserGUID
	 */
	void publishTenantProjectToMBO(String title, String adminUserGUID);

	/**
	 * To publish tenant specific module/submodule to MBO
	 * 
	 * @param userGUID
	 */
	void publishUserProjectModuleAccess(String userGUID);

}
