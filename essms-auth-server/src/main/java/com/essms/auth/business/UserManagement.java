/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletResponse;

import com.essms.auth.models.ValidateEmail;
import com.essms.auth.models.ValidateUsername;
import com.essms.auth.models.request.UserSignUpRequest;
import com.essms.auth.models.response.UserSignUpResponse;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ValidationException;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.response.ValueResponse;

/**
 * @author gaurav
 *
 */
public interface UserManagement {

	/**
	 * To sign up a new user
	 *
	 * @param userSignUpRequest
	 * @return
	 * @throws ApplicationException
	 */
	UserSignUpResponse userSignUp(UserSignUpRequest userSignUpRequest) throws ApplicationException;

	/**
	 * To register a system user
	 *
	 * @param registerUserModel
	 * @param response
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	UserSignUpResponse registerSystemUser(RegisterUserModel registerUserModel, HttpServletResponse response)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException;

	/**
	 * Update system user
	 * 
	 * @param registerUserModel
	 * @param userGUID
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	void updateByUserGUID(RegisterUserModel registerUserModel, String userGUID) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * 
	 * @param validateUsername
	 * @throws ValidationException
	 */
	void validateUsername(ValidateUsername validateUsername) throws ValidationException;

	/**
	 * 
	 * @param validateEmail
	 * @throws ValidationException
	 */
	void validateUniqueEmail(ValidateEmail validateEmail) throws ValidationException;

	/**
	 * 
	 * @param GUID
	 * @return
	 */
	ValueResponse getUserNameByGUID(String GUID);

}
