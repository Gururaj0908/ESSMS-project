/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.essms.admin.constants.APIConstant;
import com.essms.admin.models.AddressModel;
import com.essms.admin.models.EntityContactDetailModel;
import com.essms.admin.models.OutletModel;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */
public class CreateOutletRequest implements CreateEntity {

	@Valid
	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Service Branch", placeHolder = "Service Branch", optionsURL = APIConstant.GET_ALL_BRANCH)
	private Long serviceBranchId;

	@FormFieldProperty(formEditorType = FormEditorType.MultiSelectList, label = "Supporting Brand", placeHolder = "Supporting Brand")
	private Set<String> supportingBrandGUIDs;

	@Valid
	@NotNull
	@ConnectedForm(fieldName = "registerUserModel")
	private RegisterUserModel registerUserModel;

	@Valid
	@ConnectedForm(fieldName = "outlet")
	@JsonProperty("outlet")
	private OutletModel outletModel;

	@Valid
	@JsonProperty("address")
	@ConnectedForm(fieldName = "address")
	private AddressModel addressModel;

	@Valid
	@NotNull
	@ConnectedForm(fieldName = "contactDetail", isArray = true)
	@JsonProperty("contactDetail")
	private Set<EntityContactDetailModel> entityContactDetailModels;

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
	 * Will Return the outletModel
	 *
	 * @return the outletModel
	 */
	public OutletModel getOutletModel() {
		return outletModel;
	}

	/**
	 * Pass the outletModel to be set
	 *
	 * @param outletModel
	 *            the outletModel to set
	 */
	public void setOutletModel(OutletModel outletModel) {
		this.outletModel = outletModel;
	}

	/**
	 * Will Return the entityContactDetailModels
	 *
	 * @return the entityContactDetailModels
	 */
	public Set<EntityContactDetailModel> getEntityContactDetailModels() {
		return entityContactDetailModels;
	}

	/**
	 * Pass the entityContactDetailModels to be set
	 *
	 * @param entityContactDetailModels
	 *            the entityContactDetailModels to set
	 */
	public void setEntityContactDetailModels(Set<EntityContactDetailModel> entityContactDetailModels) {
		this.entityContactDetailModels = entityContactDetailModels;
	}

	/**
	 * Will Return the supportingBrandGUIDs
	 *
	 * @return the supportingBrandGUIDs
	 */
	public Set<String> getSupportingBrandGUIDs() {
		return supportingBrandGUIDs;
	}

	/**
	 * Pass the supportingBrandGUIDs to be set
	 *
	 * @param supportingBrandGUIDs
	 *            the supportingBrandGUIDs to set
	 */
	public void setSupportingBrandGUIDs(Set<String> supportingBrandGUIDs) {
		this.supportingBrandGUIDs = supportingBrandGUIDs;
	}

	/**
	 * Will Return the serviceBranchId
	 *
	 * @return the serviceBranchId
	 */
	public Long getServiceBranchId() {
		return serviceBranchId;
	}

	/**
	 * Pass the serviceBranchId to be set
	 *
	 * @param serviceBranchId
	 *            the serviceBranchId to set
	 */
	public void setServiceBranchId(Long serviceBranchId) {
		this.serviceBranchId = serviceBranchId;
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

}
