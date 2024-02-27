/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import java.util.List;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class UpsertUserBranchRequest {

	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Hidden, label = "User GUID", placeHolder = "User GUID")
	private String userGUID;

	@SkipTextTransformation
	@FormFieldProperty(key = "userBranches", placeHolder = "userBranches", label = "userBranches", formEditorType = FormEditorType.CheckBoxList)
	private List<String> userBranches;

	/**
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * @param userGUID the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * @return the userBranches
	 */
	public List<String> getUserBranches() {
		return userBranches;
	}

	/**
	 * @param userBranches the userBranches to set
	 */
	public void setUserBranches(List<String> userBranches) {
		this.userBranches = userBranches;
	}

}
