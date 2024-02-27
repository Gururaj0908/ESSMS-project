/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.business.TenantLogoManagement;
import com.essms.admin.business.TenantThemeManagement;
import com.essms.admin.models.Logo;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

	@Autowired
	private TenantLogoManagement tenantLogoManagement;

	@Autowired
	private TenantThemeManagement tenantThemeManagement;

	@RequestMapping(value = "/logo/upload", method = RequestMethod.POST)
	public void upload(@RequestBody Logo logo) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		tenantLogoManagement.upload(logo);
	}

	@RequestMapping(value = "/logo/view", method = RequestMethod.GET)
	public Logo view() {
		return tenantLogoManagement.view();
	}

	@RequestMapping(value = "/public/css", method = RequestMethod.GET)
	public String tenantCSS(HttpServletRequest request) throws IOException {
		return tenantThemeManagement.getThemeCSS(request);
	}

}
