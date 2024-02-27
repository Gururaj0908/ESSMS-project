/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import java.lang.reflect.InvocationTargetException;

import com.essms.admin.models.request.SyncDataRequest;
import com.essms.hibernate.core.models.FormList;

/**
 * @author gaurav
 *
 */
public interface SystemConfigManagement {

	/**
	 * Get the form for system configuration management
	 * 
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	FormList form() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * To sync data to all the available microservices via rabbitmq
	 */
	void syncData(SyncDataRequest syncDataRequest);
}
