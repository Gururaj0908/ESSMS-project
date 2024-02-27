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
public class CreateAreaRequest implements CreateEntity {

	@NotEmpty
	@Length(min = 1, max = 100)
	@Pattern(regexp = "^[A-z\\s]+[A-z\\s]*[A-z]+$", message = "Only alphabets allowed with spaces in between")
	@FormFieldProperty(label = "Name", autoFocus = true, placeHolder = "Name", formEditorType = FormEditorType.Text)
	private String name;

	@FormFieldProperty(label = "State", placeHolder = "State", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_ALL_STATE)
	private Long stateId;

	@NotNull
	@FormFieldProperty(label = "City", placeHolder = "City", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_CITY_BY_STATE, parentKey = "stateId")
	private Long cityId;

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
	 * Will Return the cityId
	 *
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * Pass the cityId to be set
	 *
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

}
