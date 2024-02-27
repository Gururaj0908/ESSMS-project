/**
 *
 */
package com.essms.auth.models.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.validator.annotation.ValidFieldMatch;

/**
 * @author gaurav
 *
 */
@ValidFieldMatch(field = "password", verifyField = "confirmPassword", message = "Password and ConfirmPassword don't match")
public class UserSignUpRequest {

	@NotEmpty
	private String name;

	@NotEmpty
	private String mobileNo;

	@NotEmpty
	private String emailId;

	@NotEmpty
	private String username;

	@NotEmpty
	@SkipTextTransformation
	private String password;

	@NotEmpty
	@SkipTextTransformation
	private String confirmPassword;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
