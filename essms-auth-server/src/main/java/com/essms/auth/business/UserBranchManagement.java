/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.essms.auth.models.request.UpsertUserBranchRequest;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.FormOption;

/**
 * @author gaurav
 *
 */
public interface UserBranchManagement {

	/**
	 * 
	 * @param branchGUID
	 * @param userGUID
	 * @return
	 */
	List<FormOption> listing(String branchGUID, String userGUID);

	/**
	 * 
	 * @param userGUID
	 * @param branchGUID
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	FormList permissionForm(String userGUID, String branchGUID) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * 
	 * @param upsertUserBranchRequest
	 */
	void upsert(UpsertUserBranchRequest upsertUserBranchRequest);
}
