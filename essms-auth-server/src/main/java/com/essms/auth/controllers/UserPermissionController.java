/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.UserPermissionManagement;
import com.essms.auth.models.request.CheckUserPermissionRequest;
import com.essms.auth.models.request.UpsertUserPermissionRequest;
import com.essms.auth.models.response.CheckUserPermissionResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/userpermission")
public class UserPermissionController {

	@Value("${bms.tenant.id}")
	private String bmsTenantId;

	@Autowired
	private UserPermissionManagement userPermissionManagement;

	@RequestMapping(value = "/upsert", method = { RequestMethod.POST, RequestMethod.PUT })
	public void upsertRolePermission(@RequestBody UpsertUserPermissionRequest upsertUserPermissionRequest)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		userPermissionManagement.upsertUserPermission(upsertUserPermissionRequest);
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public CheckUserPermissionResponse checkUserPermission(
			@RequestBody CheckUserPermissionRequest checkUserPermissionRequest) {
		return userPermissionManagement.checkUserPermission(checkUserPermissionRequest);
	}
}
