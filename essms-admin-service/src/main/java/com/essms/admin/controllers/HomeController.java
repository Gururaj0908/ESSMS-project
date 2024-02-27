/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.business.HomeManagement;
import com.essms.admin.models.response.HomePageResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private HomeManagement homeManagement;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public HomePageResponse home() {
		return homeManagement.home();
	}

}
