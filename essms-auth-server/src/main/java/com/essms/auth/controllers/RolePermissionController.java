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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.RolePermissionManagement;
import com.essms.auth.models.request.UpsertRolePermissionRequest;
import com.essms.auth.models.response.MenuTreeResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/rolepermission")
public class RolePermissionController {

	@Autowired
	private RolePermissionManagement rolePermissionManagement;

	@RequestMapping(value = "/upsert", method = RequestMethod.POST)
	public void upsertRolePermission(@RequestBody UpsertRolePermissionRequest upsertRolePermissionRequest) {
		rolePermissionManagement.upsertRolePermission(upsertRolePermissionRequest);
	}

	@RequestMapping(value = "/fetch", method = RequestMethod.GET)
	public MenuTreeResponse get(@RequestParam("roleGUID") String roleGUID,
			@RequestParam(value = "viewonly", required = false) Boolean viewOnly) {
		return rolePermissionManagement.getMenuTree(roleGUID, viewOnly);
	}

}
