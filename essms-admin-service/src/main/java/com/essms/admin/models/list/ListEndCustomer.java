/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import java.util.List;

import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.EndCustomerModel;
import com.essms.admin.models.EndCustomerUserModel;
import com.essms.hibernate.core.models.ListEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */
public class ListEndCustomer implements ListEntity {

	private Long id;

	private EndCustomerUserModel endCustomerUserModel;

	private EndCustomerModel endCustomerModel;

	@JsonProperty("customerAddress")
	private List<EndCustomerAddressModel> endCustomerAddressModels;

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
