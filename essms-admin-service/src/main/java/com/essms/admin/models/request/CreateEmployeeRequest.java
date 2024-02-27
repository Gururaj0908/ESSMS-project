/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.essms.admin.models.AddressModel;
import com.essms.admin.models.EmployeeModel;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.models.CreateEntity;
import com.essms.hibernate.core.models.RegisterUserModel;

/**
 * @author gaurav
 *
 */
public class CreateEmployeeRequest implements CreateEntity {

	@Valid
	@NotNull
	@ConnectedForm(fieldName = "registerUserModel")
	private RegisterUserModel registerUserModel;

	@Valid
	@NotNull
	@ConnectedForm(fieldName = "employeeModel")
	private EmployeeModel employeeModel;

	@Valid
	@NotNull
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
	 * Will Return the employeeModel
	 *
	 * @return the employeeModel
	 */
	public EmployeeModel getEmployeeModel() {
		return employeeModel;
	}

	/**
	 * Pass the employeeModel to be set
	 *
	 * @param employeeModel
	 *            the employeeModel to set
	 */
	public void setEmployeeModel(EmployeeModel employeeModel) {
		this.employeeModel = employeeModel;
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
