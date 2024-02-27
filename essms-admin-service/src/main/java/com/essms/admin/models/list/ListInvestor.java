/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import java.util.Date;

import com.essms.admin.models.AddressModel;
import com.essms.hibernate.core.models.ListEntity;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author gaurav
 *
 */
public class ListInvestor implements ListEntity {

	private Long id;

	private RegisterUserModel registerUserModel;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date joiningDate;

	private AddressModel addressModel;

	private Boolean isActive;

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
	 * @param registerUserModel the registerUserModel to set
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
	 * @param joiningDate the joiningDate to set
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
	 * @param addressModel the addressModel to set
	 */
	public void setAddressModel(AddressModel addressModel) {
		this.addressModel = addressModel;
	}

	/**
	 * Will Return the isActive
	 *
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Pass the isActive to be set
	 *
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
