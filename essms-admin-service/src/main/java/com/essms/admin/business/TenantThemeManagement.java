/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import com.essms.admin.models.request.UpsertTenantThemeConfigRequest;
import com.essms.hibernate.core.models.FormList;

/**
 * @author gaurav
 *
 */
public interface TenantThemeManagement {

	FormList form() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * 
	 * @param upsertTenantThemeConfigRequest
	 */
	void upsert(UpsertTenantThemeConfigRequest upsertTenantThemeConfigRequest);

	String getThemeCSS(HttpServletRequest request) throws IOException;

}
