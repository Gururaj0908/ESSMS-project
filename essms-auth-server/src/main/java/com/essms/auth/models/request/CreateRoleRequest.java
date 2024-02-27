/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;
import com.essms.hibernate.core.models.SystemDefined;

/**
 * @author gaurav
 *
 */
public class CreateRoleRequest extends SystemDefined implements CreateEntity {

	@NotEmpty
	@FormFieldProperty(label = "Name", placeHolder = "Name", formEditorType = FormEditorType.Text, autoFocus = true)
	private String name;

	@FormFieldProperty(label = "Is Selectable", placeHolder = "Is Selectable", formEditorType = FormEditorType.CheckBox, initialValue = "true", roleGUIDproperties = {
			RolePropertyConstant.SUPER_ADMIN_ROLE })
	private Boolean isSelectable;

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

	/**
	 * @return the isSelectable
	 */
	public Boolean getIsSelectable() {
		return isSelectable;
	}

	/**
	 * @param isSelectable the isSelectable to set
	 */
	public void setIsSelectable(Boolean isSelectable) {
		this.isSelectable = isSelectable;
	}

}
