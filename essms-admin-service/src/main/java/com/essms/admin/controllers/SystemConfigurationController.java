/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.business.SystemConfigManagement;
import com.essms.admin.models.request.SyncDataRequest;
import com.essms.hibernate.core.models.FormList;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/systemconfig")
public class SystemConfigurationController {

	@Autowired
	private SystemConfigManagement systemConfigManagement;

	@GetMapping("/form")
	public FormList form() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		return systemConfigManagement.form();
	}

	@GetMapping("/sync/location")
	public void syncLocation(@RequestBody SyncDataRequest syncDataRequest) {
		systemConfigManagement.syncData(syncDataRequest);
	}
}
