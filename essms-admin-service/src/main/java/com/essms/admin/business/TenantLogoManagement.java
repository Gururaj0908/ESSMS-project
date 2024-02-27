/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import com.essms.admin.models.Logo;

/**
 * @author gaurav
 *
 */
public interface TenantLogoManagement {

	/**
	 *
	 * @param tenantLogo
	 * 
	 */
	void upload(Logo tenantLogo);

	/**
	 *
	 * @return
	 */
	Logo view();
}