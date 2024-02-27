/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.admin.constants.APIConstant;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;

/**
 * @author gaurav
 *
 */
public class CreateCityRequest implements CreateEntity {

	@NotEmpty
	@Length(min = 1, max = 100)
	@Pattern(regexp = "^[A-z\\s]+[A-z\\s]*[A-z]+$", message = "Only alphabets allowed with spaces in between")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Name", placeHolder = "Name", autoFocus = true)
	private String name;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "State", placeHolder = "State", optionsURL = APIConstant.GET_ALL_STATE)
	private Long stateId;

	/**
	 * Will Return the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Pass the name to be set
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Will Return the stateId
	 *
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * Pass the stateId to be set
	 *
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

}
