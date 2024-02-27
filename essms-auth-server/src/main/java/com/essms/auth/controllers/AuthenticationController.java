/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.AuthenticationManagement;
import com.essms.auth.models.request.LoginRequest;
import com.essms.auth.models.request.RefreshTokenRequest;
import com.essms.auth.models.response.LoginResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationManagement authenticationManagement;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		return authenticationManagement.login(loginRequest, request, response);
	}

	@RequestMapping(value = "/refresh/accesstoken", method = RequestMethod.POST)
	public LoginResponse refreshTokem(@RequestBody RefreshTokenRequest refreshTokenRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		return authenticationManagement.refreshAccessToken(refreshTokenRequest.getRefresh_token(), request, response);
	}

}
