/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.response;

/**
 * @author ekamra.nayak
 *
 */
public class CheckUserPermissionResponse {
	
	private Boolean hasPermission;

	/**
	 * @return the hasPermission
	 */
	public Boolean getHasPermission() {
		return hasPermission;
	}

	/**
	 * @param hasPermission the hasPermission to set
	 */
	public void setHasPermission(Boolean hasPermission) {
		this.hasPermission = hasPermission;
	}
	
	

}
