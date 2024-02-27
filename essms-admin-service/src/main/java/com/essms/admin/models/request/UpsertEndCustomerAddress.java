/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.admin.models.AddressModel;
import com.essms.core.annotation.SkipTextTransformation;
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
public class UpsertEndCustomerAddress implements CreateEntity, UpdateEntity {

	private Long id;

	@Valid
	@NotNull
	@ConnectedForm(fieldName = "addressModel")
	private AddressModel addressModel;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Address Type", placeHolder = "Address Type")
	private AddressType addressType;

	private Boolean isDefault;

	@NotEmpty
	@SkipTextTransformation
	private String userGUID;

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
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

}
