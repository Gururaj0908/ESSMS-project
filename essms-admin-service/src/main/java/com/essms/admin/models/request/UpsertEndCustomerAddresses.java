/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.core.annotation.SkipTextTransformation;

/**
 * @author gaurav
 *
 */
public class UpsertEndCustomerAddresses {

	@NotEmpty
	@SkipTextTransformation
	private String userGUID;

	@NotNull
	private List<EndCustomerAddressModel> customerAddressModels;

	/**
	 * Returns the userGUID
	 * 
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * Sets the userGUID
	 * 
	 * @param userGUID
	 *            the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * Returns the customerAddressModels
	 * 
	 * @return the customerAddressModels
	 */
	public List<EndCustomerAddressModel> getCustomerAddressModels() {
		return customerAddressModels;
	}

	/**
	 * Sets the customerAddressModels
	 * 
	 * @param customerAddressModels
	 *            the customerAddressModels to set
	 */
	public void setCustomerAddressModels(List<EndCustomerAddressModel> customerAddressModels) {
		this.customerAddressModels = customerAddressModels;
	}

}
