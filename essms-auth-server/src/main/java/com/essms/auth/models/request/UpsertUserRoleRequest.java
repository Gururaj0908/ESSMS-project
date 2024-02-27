/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import java.util.Set;

import javax.validation.constraints.NotNull;

/**
 * @author gaurav
 *
 */
public class UpsertUserRoleRequest {

	@NotNull
	private String userGUID;

	@NotNull
	private Set<String> roleGUIDs;

	/**
	 * Will Return the userGUID
	 *
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * Pass the userGUID to be set
	 *
	 * @param userGUID
	 *            the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * Will Return the roleGUIDs
	 *
	 * @return the roleGUIDs
	 */
	public Set<String> getRoleGUIDs() {
		return roleGUIDs;
	}

	/**
	 * Pass the roleGUIDs to be set
	 *
	 * @param roleGUIDs
	 *            the roleGUIDs to set
	 */
	public void setRoleGUIDs(Set<String> roleGUIDs) {
		this.roleGUIDs = roleGUIDs;
	}

}
