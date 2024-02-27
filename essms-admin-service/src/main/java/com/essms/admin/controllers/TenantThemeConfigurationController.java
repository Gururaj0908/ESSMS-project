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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.business.TenantThemeManagement;
import com.essms.admin.models.request.UpsertTenantThemeConfigRequest;
import com.essms.hibernate.core.models.FormList;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/tenant/theme/configuration")
public class TenantThemeConfigurationController {

	@Autowired
	private TenantThemeManagement tenantThemeManagement;

	@GetMapping("/form")
	public FormList form() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		return tenantThemeManagement.form();
	}

	@RequestMapping(value = "/upsert", method = { RequestMethod.POST, RequestMethod.PUT })
	public void upsert(@RequestBody UpsertTenantThemeConfigRequest upsertTenantThemeConfigRequest)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		tenantThemeManagement.upsert(upsertTenantThemeConfigRequest);
	}
}
