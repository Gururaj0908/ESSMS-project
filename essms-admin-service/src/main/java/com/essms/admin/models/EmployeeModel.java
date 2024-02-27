/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import java.util.Date;

import com.essms.admin.constants.APIConstant;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.enums.FormEditorType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author gaurav
 *
 */
public class EmployeeModel {

	@JsonFormat(pattern = "yyyy-MM-dd")
	@FormFieldProperty(formEditorType = FormEditorType.Date, label = "Birth Date", placeHolder = "Birth Date")
	private Date birthDate;

	@FormFieldProperty(label = "Branch", placeHolder = "Branch", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_ALL_BRANCH, roleGUIDproperties = RolePropertyConstant.ADMIN_ROLE)
	private Long branchId;

	private String branch;

	/**
	 * Will Return the birthDate
	 *
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Pass the birthDate to be set
	 *
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Will Return the branchId
	 *
	 * @return the branchId
	 */
	public Long getBranchId() {
		return branchId;
	}

	/**
	 * Pass the branchId to be set
	 *
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	/**
	 * Will Return the branch
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Pass the branch to be set
	 *
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

}
