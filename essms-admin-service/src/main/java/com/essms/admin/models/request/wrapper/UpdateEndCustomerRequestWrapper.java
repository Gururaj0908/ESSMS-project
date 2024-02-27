/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request.wrapper;

import javax.validation.Valid;

import com.essms.admin.enums.CustomerPageRoute;
import com.essms.admin.models.request.UpdateEndCustomerRequest;
import com.essms.hibernate.core.annotation.ConnectedForm;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author gaurav
 *
 */
@JsonIgnoreProperties(value = { "endCustomeUserModel.password" })
public class UpdateEndCustomerRequestWrapper {

	@FormFieldProperty(formEditorType = FormEditorType.Hidden, label = "CustomerPageRoute", placeHolder = "CustomerPageRoute")
	private CustomerPageRoute customerPageRoute;

	@Valid
	@ConnectedForm(fieldName = "updateEndCustomerRequest")
	private UpdateEndCustomerRequest updateEndCustomerRequest;

	/**
	 * @return the updateEndCustomerRequest
	 */
	public UpdateEndCustomerRequest getUpdateEndCustomerRequest() {
		return updateEndCustomerRequest;
	}

	/**
	 * @param updateEndCustomerRequest the updateEndCustomerRequest to set
	 */
	public void setUpdateEndCustomerRequest(UpdateEndCustomerRequest updateEndCustomerRequest) {
		this.updateEndCustomerRequest = updateEndCustomerRequest;
	}

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

}