/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class ForceResetPasswordRequest {

	@SkipTextTransformation
	@FormFieldProperty(label = "UserGUID", formEditorType = FormEditorType.Hidden, placeHolder = "UserGUID")
	private String userGUID;

	@NotEmpty
	@SkipTextTransformation
	@Length(min = 6, max = 50)
	@FormFieldProperty(label = "New Password", formEditorType = FormEditorType.Password, placeHolder = "New Password")
	private String newPassword;

	@NotEmpty
	@SkipTextTransformation
	@Length(min = 6, max = 50)
	@FormFieldProperty(label = "Confirm New Password", formEditorType = FormEditorType.Password, placeHolder = "Confirm New Password", accept = "newPassword")
	private String confirmNewPassword;

	/**
	 * Returns the userGUID
	 * 
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * Sets the userGUID
	 * 
	 * @param userGUID the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * Returns the newPassword
	 * 
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Sets the newPassword
	 * 
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmNewPassword
	 */
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	/**
	 * @param confirmNewPassword the confirmNewPassword to set
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

}
