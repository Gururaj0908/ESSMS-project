/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;

/**
 * @author gaurav
 *
 */
public class CreateStateRequest implements CreateEntity {

	@NotEmpty
	@Length(min = 2, max = 2)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Code", placeHolder = "Code", autoFocus = true)
	private String code;

	@NotEmpty
	@Length(min = 2, max = 2)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "TIN", placeHolder = "TIN")
	private String tin;

	@NotEmpty
	@Length(min = 1, max = 100)
	@Pattern(regexp = "^[A-z\\s]+[A-z\\s]*[A-z]+$", message = "Only alphabets allowed with spaces in between")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Name", placeHolder = "Name")
	private String name;

	/**
	 * Returns the code
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code
	 * 
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the tin
	 * 
	 * @return the tin
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * Sets the tin
	 * 
	 * @param tin the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
