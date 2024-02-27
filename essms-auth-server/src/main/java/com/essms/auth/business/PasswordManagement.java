/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business;

import java.lang.reflect.InvocationTargetException;

import com.essms.auth.models.request.ChangePasswordRequest;
import com.essms.auth.models.request.ForceResetPasswordRequest;
import com.essms.auth.models.request.ForgotPasswordRequest;
import com.essms.auth.models.request.ResetLinkPasswordRequest;
import com.essms.auth.models.request.ResetOTPPasswordRequest;
import com.essms.hibernate.core.models.FormList;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
public interface PasswordManagement {

	/**
	 * 
	 * @param userGUID
	 * @return
	 */
	FormList resetForm(String userGUID) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * Reset password of a user
	 * 
	 * @param resetPasswordRequest
	 */
	void forceReset(ForceResetPasswordRequest resetPasswordRequest);

	/**
	 * Change password for a user
	 * 
	 * @param changePasswordRequest
	 */
	void changePassword(ChangePasswordRequest changePasswordRequest);

	/**
	 * 
	 * @param forgotPasswordRequest
	 * @param sendOTP
	 * @throws JsonProcessingException
	 */
	void forgotPassword(ForgotPasswordRequest forgotPasswordRequest, Boolean sendOTP) throws JsonProcessingException;

	/**
	 * 
	 * @param resetOTPPasswordRequest
	 */
	void resetPasswordViaOTP(ResetOTPPasswordRequest resetOTPPasswordRequest);

	/**
	 * 
	 * @param resetLinkPasswordRequest
	 */
	void resetPasswordViaLink(ResetLinkPasswordRequest resetLinkPasswordRequest);

}
