/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;

/**
 * @author gaurav
 *
 */
public class ChangePasswordRequest {

	@NotEmpty
	@SkipTextTransformation
	private String userGUID;

	@NotEmpty
	@SkipTextTransformation
	private String oldPassword;

	@NotEmpty
	@SkipTextTransformation
	private String newPassword;

	/**
	 * Returns the userGUID
	 * 
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * Sets the userGUID
	 * 
	 * @param userGUID the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * Returns the oldPassword
	 * 
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * Sets the oldPassword
	 * 
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * Returns the newPassword
	 * 
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Sets the newPassword
	 * 
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
