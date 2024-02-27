/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gaurav
 *
 */
public class SyncDataRequest {

	@JsonIgnore
	@FormFieldProperty(label = "Sync Shared Data", placeHolder = "Sync Shared Data", formEditorType = FormEditorType.Separator)
	private String separatorSync;

	@FormFieldProperty(formEditorType = FormEditorType.CheckBox, label = "Location", placeHolder = "Location")
	private boolean isSyncLocation;

	/**
	 * @return the isSyncLocation
	 */
	public boolean isSyncLocation() {
		return isSyncLocation;
	}

	/**
	 * @param isSyncLocation the isSyncLocation to set
	 */
	public void setSyncLocation(boolean isSyncLocation) {
		this.isSyncLocation = isSyncLocation;
	}

}
