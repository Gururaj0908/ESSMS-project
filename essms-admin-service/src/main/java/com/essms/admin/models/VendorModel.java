/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import javax.validation.constraints.Pattern;

import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class VendorModel {

	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Only alphanumberic characters allowed")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "GSTIN Number", placeHolder = "GSTIN Number")
	private String gstinNumber;

	@Pattern(regexp = "^[A-z\\s\\.]+[A-z\\.]*[A-z\\.]+$", message = "Only alphabets allowed, dots and spaces allowed in between alphabets")
	@FormFieldProperty(formEditorType = FormEditorType.MultilineText, label = "Description", placeHolder = "Description")
	private String description;

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Vendor URL", placeHolder = "Vendor URL")
	private String vendorURL;

	/**
	 * Will Return the gstinNumber
	 *
	 * @return the gstinNumber
	 */
	public String getGstinNumber() {
		return gstinNumber;
	}

	/**
	 * Pass the gstinNumber to be set
	 *
	 * @param gstinNumber
	 *            the gstinNumber to set
	 */
	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

	/**
	 * Will Return the description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Pass the description to be set
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Will Return the vendorURL
	 *
	 * @return the vendorURL
	 */
	public String getVendorURL() {
		return vendorURL;
	}

	/**
	 * Pass the vendorURL to be set
	 *
	 * @param vendorURL
	 *            the vendorURL to set
	 */
	public void setVendorURL(String vendorURL) {
		this.vendorURL = vendorURL;
	}

}
