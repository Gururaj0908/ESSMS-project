/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.essms.auth.models.request.LoginRequest;
import com.essms.auth.models.response.LoginResponse;

/**
 * @author gaurav
 *
 */
public interface AuthenticationManagement {
	/**
	 *
	 * @param loginRequest
	 * @param request
	 * @return
	 * @throws IOException
	 */
	LoginResponse login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException;

	/**
	 * @param refreshToken
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	LoginResponse refreshAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response)
			throws IOException;
}
