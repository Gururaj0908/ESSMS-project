/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import java.util.List;

import javax.validation.Valid;

import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.EndCustomerModel;
import com.essms.admin.models.EndCustomerUserModel;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.models.CreateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */
public class CreateEndCustomerRequest implements CreateEntity {

	@Valid
	@ConnectedForm(fieldName = "endCustomerUserModel")
	private EndCustomerUserModel endCustomerUserModel;

	@Valid
	@ConnectedForm(fieldName = "endCustomerModel")
	private EndCustomerModel endCustomerModel;

	@JsonProperty("customerAddress")
	@ConnectedForm(isArray = true, fieldName = "customerAddress")
	private List<EndCustomerAddressModel> endCustomerAddressModels;

	/**
	 * @return the endCustomerUserModel
	 */
	public EndCustomerUserModel getEndCustomerUserModel() {
		return endCustomerUserModel;
	}

	/**
	 * @param endCustomerUserModel the endCustomerUserModel to set
	 */
	public void setEndCustomerUserModel(EndCustomerUserModel endCustomerUserModel) {
		this.endCustomerUserModel = endCustomerUserModel;
	}

	/**
	 * Will Return the endCustomerModel
	 *
	 * @return the endCustomerModel
	 */
	public EndCustomerModel getEndCustomerModel() {
		return endCustomerModel;
	}

	/**
	 * Pass the endCustomerModel to be set
	 *
	 * @param endCustomerModel the endCustomerModel to set
	 */
	public void setEndCustomerModel(EndCustomerModel endCustomerModel) {
		this.endCustomerModel = endCustomerModel;
	}

	/**
	 * Will Return the endCustomerAddressModels
	 *
	 * @return the endCustomerAddressModels
	 */
	public List<EndCustomerAddressModel> getEndCustomerAddressModels() {
		return endCustomerAddressModels;
	}

	/**
	 * Pass the endCustomerAddressModels to be set
	 *
	 * @param endCustomerAddressModels the endCustomerAddressModels to set
	 */
	public void setEndCustomerAddressModels(List<EndCustomerAddressModel> endCustomerAddressModels) {
		this.endCustomerAddressModels = endCustomerAddressModels;
	}

}
