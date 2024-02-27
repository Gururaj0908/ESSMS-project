/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.search;

import org.hibernate.validator.constraints.Length;

import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class SearchCustomer {

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Name", placeHolder = "Name")
	private String name;

	@Length(min = 10, max = 15)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Mobile No", placeHolder = "Mobile No")
	private String mobileNo;

	@FormFieldProperty(formEditorType = FormEditorType.Email, label = "Email", placeHolder = "Email")
	private String emailId;

	/**
	 * Will Return the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Pass the name to be set
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Will Return the mobileNo
	 *
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Pass the mobileNo to be set
	 *
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Will Return the emailId
	 *
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Pass the emailId to be set
	 *
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
