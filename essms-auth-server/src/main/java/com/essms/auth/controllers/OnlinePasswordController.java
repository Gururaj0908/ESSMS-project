/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.PasswordManagement;
import com.essms.auth.models.request.ForgotPasswordRequest;
import com.essms.auth.models.request.ResetOTPPasswordRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/public/password")
public class OnlinePasswordController {

	@Autowired
	private PasswordManagement passwordManagement;

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public void forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest)
			throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		passwordManagement.forgotPassword(forgotPasswordRequest, true);
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public void reset(@RequestBody ResetOTPPasswordRequest resetOTPPasswordRequest) {
		passwordManagement.resetPasswordViaOTP(resetOTPPasswordRequest);
	}
}
