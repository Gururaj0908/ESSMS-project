/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.search;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class SearchState {

	@NotEmpty
	@FormFieldProperty(label = "Name", placeHolder = "Name", formEditorType = FormEditorType.Text)
	private String name;

	/**
	 * Returns the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
