/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.PasswordManagement;
import com.essms.auth.models.request.ChangePasswordRequest;
import com.essms.auth.models.request.ForceResetPasswordRequest;
import com.essms.hibernate.core.models.FormList;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/password")
public class PasswordController {

	@Autowired
	private PasswordManagement passwordManagement;

	@RequestMapping(value = "/reset/form/{userGUID}", method = RequestMethod.GET)
	public FormList resetForm(@PathVariable("userGUID") String userGUID)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		return passwordManagement.resetForm(userGUID);
	}

	@RequestMapping(value = "/force/reset", method = RequestMethod.POST)
	public void forceReset(@RequestBody ForceResetPasswordRequest resetPasswordRequest) {
		passwordManagement.forceReset(resetPasswordRequest);
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		passwordManagement.changePassword(changePasswordRequest);
	}

}
