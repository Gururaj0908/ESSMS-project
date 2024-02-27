/**
 * This file is subject to the terms and conditions, as well as 
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.response;

/**
 * @author gaurav
 *
 */
public class UserSignUpResponse {

	private String userGUID;

	/**
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * @param userGUID
	 *            the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

}
