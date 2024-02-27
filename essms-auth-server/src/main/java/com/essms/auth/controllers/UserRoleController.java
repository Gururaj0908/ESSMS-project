/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.UserRoleManagement;
import com.essms.auth.models.list.ListUserRole;
import com.essms.auth.models.request.UpsertUserRoleRequest;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/userrole")
public class UserRoleController {

	@Autowired
	private UserRoleManagement userRoleManagement;

	@RequestMapping(value = "/upsert", method = RequestMethod.POST)
	public void create(@RequestBody UpsertUserRoleRequest createUserRoleRequest) {
		userRoleManagement.upsertUserRole(createUserRoleRequest);
	}

	@RequestMapping(value = "/byuserguids", method = RequestMethod.GET)
	public List<ListUserRole> listByUserGUIDs(@RequestParam(name = "userGUIDs") String[] userGUIDs) {
		return userRoleManagement.listByUserGUIDs(userGUIDs);
	}

}
