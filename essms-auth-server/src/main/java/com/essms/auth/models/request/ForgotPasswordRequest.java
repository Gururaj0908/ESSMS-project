/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author gaurav
 *
 */
public class ForgotPasswordRequest {

	@NotEmpty
	private String emailId;

	/**
	 * Returns the emailId
	 * 
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Sets the emailId
	 * 
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
