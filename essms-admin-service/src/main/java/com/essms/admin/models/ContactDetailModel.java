/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.enums.Designation;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class ContactDetailModel {

	private Long id;

	@NotEmpty
	@Pattern(regexp = "^[A-z\\s\\.]+[A-z\\.]*[A-z\\.]+$", message = "Only alphabets allowed, dots and spaces allowed in between alphabets")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Contact Person", placeHolder = "Contact Person")
	private String contactPerson;

	@NotEmpty
	@Length(min = 10, max = 13)
	@Pattern(regexp = "^[+]?[0-9]*$", message = "Can only be prefixed with optional '+' followed by numbers only")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Mobile No", placeHolder = "Mobile No")
	private String contactMobile;

	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Please enter a valid email id")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Email", placeHolder = "Email")
	private String contactEmailId;

	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Designation", placeHolder = "Designation")
	private Designation designation;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Pass the id to be set
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Will Return the contactPerson
	 *
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * Pass the contactPerson to be set
	 *
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * Will Return the contactMobile
	 *
	 * @return the contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * Pass the contactMobile to be set
	 *
	 * @param contactMobile the contactMobile to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * Will Return the contactEmailId
	 *
	 * @return the contactEmailId
	 */
	public String getContactEmailId() {
		return contactEmailId;
	}

	/**
	 * Pass the contactEmailId to be set
	 *
	 * @param contactEmailId the contactEmailId to set
	 */
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}

	/**
	 * Will Return the designation
	 *
	 * @return the designation
	 */
	public Designation getDesignation() {
		return designation;
	}

	/**
	 * Pass the designation to be set
	 *
	 * @param designation the designation to set
	 */
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

}
