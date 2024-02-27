/**
 * This file is subject to the terms and conditions, as well as 
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.response;

import com.essms.auth.models.LoginError;
import com.essms.auth.models.LoginSuccess;

/**
 * @author gaurav
 *
 */
public class LoginResponse {

	private LoginSuccess loginSuccess;
	private LoginError loginError;

	/**
	 * @return the loginSuccess
	 */
	public LoginSuccess getLoginSuccess() {
		return loginSuccess;
	}

	/**
	 * @param loginSuccess
	 *            the loginSuccess to set
	 */
	public void setLoginSuccess(LoginSuccess loginSuccess) {
		this.loginSuccess = loginSuccess;
	}

	/**
	 * @return the loginError
	 */
	public LoginError getLoginError() {
		return loginError;
	}

	/**
	 * @param loginError
	 *            the loginError to set
	 */
	public void setLoginError(LoginError loginError) {
		this.loginError = loginError;
	}

}
