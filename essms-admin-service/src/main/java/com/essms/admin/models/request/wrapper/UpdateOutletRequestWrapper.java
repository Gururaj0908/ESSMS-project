/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request.wrapper;

import javax.validation.Valid;

import com.essms.admin.enums.CustomerPageRoute;
import com.essms.admin.models.request.UpdateOutletRequest;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author gaurav
 *
 */
@JsonIgnoreProperties(value = { "registerUserModel.password" })
public class UpdateOutletRequestWrapper {

	@FormFieldProperty(formEditorType = FormEditorType.Hidden, label = "CustomerPageRoute", placeHolder = "CustomerPageRoute")
	private CustomerPageRoute customerPageRoute;

	@Valid
	@ConnectedForm(fieldName = "updateOutletRequest")
	private UpdateOutletRequest updateOutletRequest;

	/**
	 * @return the customerPageRoute
	 */
	public CustomerPageRoute getCustomerPageRoute() {
		return customerPageRoute;
	}

	/**
	 * @param customerPageRoute the customerPageRoute to set
	 */
	public void setCustomerPageRoute(CustomerPageRoute customerPageRoute) {
		this.customerPageRoute = customerPageRoute;
	}

	/**
	 * @return the updateOutletRequest
	 */
	public UpdateOutletRequest getUpdateOutletRequest() {
		return updateOutletRequest;
	}

	/**
	 * @param updateOutletRequest the updateOutletRequest to set
	 */
	public void setUpdateOutletRequest(UpdateOutletRequest updateOutletRequest) {
		this.updateOutletRequest = updateOutletRequest;
	}

}
