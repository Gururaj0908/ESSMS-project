/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import java.util.List;

import com.essms.admin.models.VendorAddressModel;
import com.essms.admin.models.VendorModel;
import com.essms.hibernate.core.models.ListEntity;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */
public class ListVendor implements ListEntity {

	private Long id;

	private RegisterUserModel registerUserModel;

	@JsonProperty("vendor")
	private VendorModel vendorModel;

	@JsonProperty("vendorAddress")
	private List<VendorAddressModel> vendorAddressModel;

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
	 * @param id
	 *            the id to set
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
	 * @param registerUserModel
	 *            the registerUserModel to set
	 */
	public void setRegisterUserModel(RegisterUserModel registerUserModel) {
		this.registerUserModel = registerUserModel;
	}

	/**
	 * Will Return the vendorModel
	 *
	 * @return the vendorModel
	 */
	public VendorModel getVendorModel() {
		return vendorModel;
	}

	/**
	 * Pass the vendorModel to be set
	 *
	 * @param vendorModel
	 *            the vendorModel to set
	 */
	public void setVendorModel(VendorModel vendorModel) {
		this.vendorModel = vendorModel;
	}

	/**
	 * Will Return the vendorAddressModel
	 *
	 * @return the vendorAddressModel
	 */
	public List<VendorAddressModel> getVendorAddressModel() {
		return vendorAddressModel;
	}

	/**
	 * Pass the vendorAddressModel to be set
	 *
	 * @param vendorAddressModel
	 *            the vendorAddressModel to set
	 */
	public void setVendorAddressModel(List<VendorAddressModel> vendorAddressModel) {
		this.vendorAddressModel = vendorAddressModel;
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
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
