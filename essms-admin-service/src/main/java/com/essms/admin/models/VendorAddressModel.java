/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.essms.core.enums.OfficeType;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */
public class VendorAddressModel {

	private Long id;

	@Valid
	@ConnectedForm(fieldName = "addressModel")
	private AddressModel addressModel;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Office Type", placeHolder = "Office Type")
	private OfficeType officeType;

	@Valid
	@NotNull
	@JsonProperty("contactDetail")
	@ConnectedForm(fieldName = "contactDetail", isArray = true)
	private List<EntityContactDetailModel> entityContactDetailModels;

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
	 * Will Return the officeType
	 *
	 * @return the officeType
	 */
	public OfficeType getOfficeType() {
		return officeType;
	}

	/**
	 * Pass the officeType to be set
	 *
	 * @param officeType
	 *            the officeType to set
	 */
	public void setOfficeType(OfficeType officeType) {
		this.officeType = officeType;
	}

	/**
	 * Will Return the entityContactDetailModels
	 *
	 * @return the entityContactDetailModels
	 */
	public List<EntityContactDetailModel> getEntityContactDetailModels() {
		return entityContactDetailModels;
	}

	/**
	 * Pass the entityContactDetailModels to be set
	 *
	 * @param entityContactDetailModels
	 *            the entityContactDetailModels to set
	 */
	public void setEntityContactDetailModels(List<EntityContactDetailModel> entityContactDetailModels) {
		this.entityContactDetailModels = entityContactDetailModels;
	}

}
