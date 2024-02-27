/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.essms.admin.models.AddressModel;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;
import com.essms.hibernate.core.models.RegisterUserModel;

/**
 * @author gaurav
 *
 */
public class CreateInvestorRequest implements CreateEntity {

	@Valid
	@ConnectedForm(fieldName = "registerUserModel")
	private RegisterUserModel registerUserModel;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.Date, label = "Joining Date", placeHolder = "Joining Date")
	private Date joiningDate;

	@Valid
	@ConnectedForm(fieldName = "addressModel")
	private AddressModel addressModel;

	/**
	 * Will Return the registerUserModel
	 *
	 * @return the registerUserModel
	 */
	public RegisterUserModel getRegisterUserModel() {
		return registerUserModel;
	}

	/**
	 * Pass the registerUserModel to be set
	 *
	 * @param registerUserModel
	 *            the registerUserModel to set
	 */
	public void setRegisterUserModel(RegisterUserModel registerUserModel) {
		this.registerUserModel = registerUserModel;
	}

	/**
	 * Will Return the joiningDate
	 *
	 * @return the joiningDate
	 */
	public Date getJoiningDate() {
		return joiningDate;
	}

	/**
	 * Pass the joiningDate to be set
	 *
	 * @param joiningDate
	 *            the joiningDate to set
	 */
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	/**
	 * Will Return the addressModel
	 *
	 * @return the addressModel
	 */
	public AddressModel getAddressModel() {
		return addressModel;
	}

	/**
	 * Pass the addressModel to be set
	 *
	 * @param addressModel
	 *            the addressModel to set
	 */
	public void setAddressModel(AddressModel addressModel) {
		this.addressModel = addressModel;
	}

}
