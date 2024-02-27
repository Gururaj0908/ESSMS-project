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

import com.essms.auth.business.TenantManagement;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired
	private TenantManagement tenantManagement;

	@RequestMapping(value = "/publish/project", method = RequestMethod.GET)
	public void publishProjectItems(@RequestParam("title") String title, @RequestParam("adminGUID") String adminGUID) {
		tenantManagement.publishTenantProjectToMBO(title, adminGUID);
	}

	@RequestMapping(value = "/publish/usermodule", method = RequestMethod.GET)
	public void publishUserProjectItems(@RequestParam("userGUID") String userGUID) {
		tenantManagement.publishUserProjectModuleAccess(userGUID);
	}
}
