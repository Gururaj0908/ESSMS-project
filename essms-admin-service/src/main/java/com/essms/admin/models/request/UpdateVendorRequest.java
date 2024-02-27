/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.essms.admin.models.VendorAddressModel;
import com.essms.admin.models.VendorModel;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.UpdateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */
public class UpdateVendorRequest implements UpdateEntity {

	@NotNull
	private Long id;

	private RegisterUserModel registerUserModel;

	@JsonProperty("vendor")
	private VendorModel vendorModel;

	@JsonProperty("vendorAddress")
	private List<VendorAddressModel> vendorAddressModels;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Pass the id to be set
	 *
	 * @param id
	 *            the id to set
	 */
	@Override
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
	 * Will Return the vendorAddressModels
	 *
	 * @return the vendorAddressModels
	 */
	public List<VendorAddressModel> getVendorAddressModels() {
		return vendorAddressModels;
	}

	/**
	 * Pass the vendorAddressModels to be set
	 *
	 * @param vendorAddressModels
	 *            the vendorAddressModels to set
	 */
	public void setVendorAddressModels(List<VendorAddressModel> vendorAddressModels) {
		this.vendorAddressModels = vendorAddressModels;
	}

}