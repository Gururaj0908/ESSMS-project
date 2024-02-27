/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import javax.validation.Valid;

import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class EntityContactDetailModel {

	private Long id;

	@Valid
	@ConnectedForm(fieldName = "contactDetailModel")
	private ContactDetailModel contactDetailModel;

	@FormFieldProperty(formEditorType = FormEditorType.CheckBox, label = "Is SMS Enabled", placeHolder = "Is SMS Enabled")
	private Boolean isSMSEnabled = Boolean.FALSE;

	@FormFieldProperty(formEditorType = FormEditorType.CheckBox, label = "Is Email Enabled", placeHolder = "Is Email Enabled")
	private Boolean isEmailEnabled = Boolean.FALSE;

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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Will Return the contactDetailModel
	 *
	 * @return the contactDetailModel
	 */
	public ContactDetailModel getContactDetailModel() {
		return contactDetailModel;
	}

	/**
	 * Pass the contactDetailModel to be set
	 *
	 * @param contactDetailModel
	 *            the contactDetailModel to set
	 */
	public void setContactDetailModel(ContactDetailModel contactDetailModel) {
		this.contactDetailModel = contactDetailModel;
	}

	/**
	 * Will Return the isSMSEnabled
	 *
	 * @return the isSMSEnabled
	 */
	public Boolean getIsSMSEnabled() {
		return isSMSEnabled;
	}

	/**
	 * Pass the isSMSEnabled to be set
	 *
	 * @param isSMSEnabled
	 *            the isSMSEnabled to set
	 */
	public void setIsSMSEnabled(Boolean isSMSEnabled) {
		this.isSMSEnabled = isSMSEnabled;
	}

	/**
	 * Will Return the isEmailEnabled
	 *
	 * @return the isEmailEnabled
	 */
	public Boolean getIsEmailEnabled() {
		return isEmailEnabled;
	}

	/**
	 * Pass the isEmailEnabled to be set
	 *
	 * @param isEmailEnabled
	 *            the isEmailEnabled to set
	 */
	public void setIsEmailEnabled(Boolean isEmailEnabled) {
		this.isEmailEnabled = isEmailEnabled;
	}

}