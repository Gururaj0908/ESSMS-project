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
 * @author ekamra.nayak
 *
 */
public class BookConsumptionSearchCustomer {

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Customer Name", placeHolder = "Name")
	private String name;

	@Length(min = 10, max = 15)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Mobile No", placeHolder = "Mobile No")
	private String mobileNo;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}
