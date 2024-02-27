/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.validator.annotation.ValidFieldMatch;

/**
 * @author gaurav
 *
 */
@ValidFieldMatch(field = "newPassword", verifyField = "confirmNewPassword", message = "New Password and Confirm New Password don't match")
public class ResetOTPPasswordRequest {

	@NotEmpty
	private String emailId;

	@NotEmpty
	private String OTP;

	@NotEmpty
	@Length(max = 50)
	@SkipTextTransformation
	private String newPassword;

	@NotEmpty
	@SkipTextTransformation
	private String confirmNewPassword;

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the oTP
	 */
	public String getOTP() {
		return OTP;
	}

	/**
	 * @param oTP the oTP to set
	 */
	public void setOTP(String oTP) {
		OTP = oTP;
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

	/**
	 * @return the confirmNewPassword
	 */
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	/**
	 * @param confirmNewPassword the confirmNewPassword to set
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

}
