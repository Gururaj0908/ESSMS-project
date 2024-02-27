/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;

/**
 * @author gaurav
 *
 */
public class ResetLinkPasswordRequest {

	@NotEmpty
	@SkipTextTransformation
	private String resetGUID;

	@NotEmpty
	@Length(max = 50)
	@SkipTextTransformation
	private String newPassword;

	/**
	 * Returns the resetGUID
	 * 
	 * @return the resetGUID
	 */
	public String getResetGUID() {
		return resetGUID;
	}

	/**
	 * Sets the resetGUID
	 * 
	 * @param resetGUID the resetGUID to set
	 */
	public void setResetGUID(String resetGUID) {
		this.resetGUID = resetGUID;
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
