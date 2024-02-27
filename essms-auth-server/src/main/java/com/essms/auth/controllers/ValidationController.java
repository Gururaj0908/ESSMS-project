/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.UserManagement;
import com.essms.auth.models.ValidateEmail;
import com.essms.auth.models.ValidateUsername;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/public/validate")
public class ValidationController {

	@Autowired
	private UserManagement userManagement;

	@RequestMapping(value = "/email/unique", method = RequestMethod.POST)
	public void validateUniqueEmail(@RequestBody ValidateEmail validateEmail) {
		userManagement.validateUniqueEmail(validateEmail);
	}

	@RequestMapping(value = "/username/unique", method = RequestMethod.POST)
	public void validateUserName(@RequestBody ValidateUsername validateUsername) {
		userManagement.validateUsername(validateUsername);
	}
}
