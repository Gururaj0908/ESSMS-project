/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.business.UserBranchManagement;
import com.essms.auth.models.request.UpsertUserBranchRequest;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.FormOption;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/userbranch")
public class UserBranchController {

	@Autowired
	private UserBranchManagement userBranchManagement;

	@GetMapping(value = "/listing/{userGUID}/{branchGUID}")
	public List<FormOption> listing(@PathVariable("userGUID") String userGUID,
			@PathVariable("branchGUID") String branchGUID) {
		return userBranchManagement.listing(branchGUID, userGUID);
	}

	@RequestMapping(value = "/permission/form/{userGUID}/{branchGUID}", method = RequestMethod.GET)
	public FormList resetForm(@PathVariable("userGUID") String userGUID, @PathVariable("branchGUID") String branchGUID)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		return userBranchManagement.permissionForm(userGUID, branchGUID);
	}

	@PostMapping("/permission/upsert")
	public void upsert(@RequestBody UpsertUserBranchRequest upsertUserBranchRequest) {
		userBranchManagement.upsert(upsertUserBranchRequest);
	}

}
