/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.essms.core.enums.AddressType;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;
import com.essms.hibernate.core.models.UpdateEntity;

/**
 * @author gaurav
 *
 */
public class EndCustomerAddressModel implements CreateEntity, UpdateEntity {

	private Long id;

	@Valid
	@NotNull
	@ConnectedForm(fieldName = "addressModel")
	private AddressModel addressModel;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Address Type", placeHolder = "Address Type")
	private AddressType addressType;

	private Boolean isDefault;

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

	/**
	 * Will Return the addressType
	 *
	 * @return the addressType
	 */
	public AddressType getAddressType() {
		return addressType;
	}

	/**
	 * Pass the addressType to be set
	 *
	 * @param addressType
	 *            the addressType to set
	 */
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	/**
	 * Will Return the isDefault
	 *
	 * @return the isDefault
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * Pass the isDefault to be set
	 *
	 * @param isDefault
	 *            the isDefault to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
