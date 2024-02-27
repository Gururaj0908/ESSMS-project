/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import com.essms.admin.models.AddressModel;
import com.essms.core.enums.AddressType;
import com.essms.hibernate.core.models.ListEntity;

/**
 * @author gaurav
 *
 */
public class ListEndCustomerAddress implements ListEntity {

	private Long id;

	private Boolean isDefault;

	private AddressType addressType;

	private AddressModel addressModel;

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the isDefault
	 * 
	 * @return the isDefault
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * Sets the isDefault
	 * 
	 * @param isDefault
	 *            the isDefault to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * Returns the addressType
	 * 
	 * @return the addressType
	 */
	public AddressType getAddressType() {
		return addressType;
	}

	/**
	 * Sets the addressType
	 * 
	 * @param addressType
	 *            the addressType to set
	 */
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	/**
	 * Returns the addressModel
	 * 
	 * @return the addressModel
	 */
	public AddressModel getAddressModel() {
		return addressModel;
	}

	/**
	 * Sets the addressModel
	 * 
	 * @param addressModel
	 *            the addressModel to set
	 */
	public void setAddressModel(AddressModel addressModel) {
		this.addressModel = addressModel;
	}

}
