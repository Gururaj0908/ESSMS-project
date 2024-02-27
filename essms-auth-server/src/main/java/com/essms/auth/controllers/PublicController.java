/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.UserManagement;
import com.essms.hibernate.core.models.response.ValueResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserManagement userManagement;

	@RequestMapping(value = "/user/name", method = RequestMethod.GET)
	public ValueResponse userNameByGUID(@RequestParam(value = "userGUID", required = false) String userGUID) {
		return userManagement.getUserNameByGUID(userGUID);
	}
}
