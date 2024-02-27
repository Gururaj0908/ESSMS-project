/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;

/**
 * @author gaurav
 *
 */
public class CreateTenantConfigurationRequest implements CreateEntity {

	@NotEmpty
	@Length(max = 50)
	@SkipTextTransformation
	@FormFieldProperty(label = "Tenant Id", placeHolder = "Tenant Id", formEditorType = FormEditorType.Text, autoFocus = true)
	private String tenantId;

	@NotEmpty
	@Length(max = 50)
	@SkipTextTransformation
	@FormFieldProperty(label = "MBO Project Id", placeHolder = "MBO Project Id", formEditorType = FormEditorType.Text)
	private String mboProjectId;

	@NotEmpty
	@Length(max = 50)
	@SkipTextTransformation
	@FormFieldProperty(label = "UI Base URL", placeHolder = "UI Base URL", formEditorType = FormEditorType.Url, hint = "Absolute Path starting http:// or https://")
	private String uiBaseUrl;

	/**
	 * Returns the tenantId
	 * 
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * Sets the tenantId
	 * 
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * Returns the mboProjectId
	 * 
	 * @return the mboProjectId
	 */
	public String getMboProjectId() {
		return mboProjectId;
	}

	/**
	 * Sets the mboProjectId
	 * 
	 * @param mboProjectId
	 *            the mboProjectId to set
	 */
	public void setMboProjectId(String mboProjectId) {
		this.mboProjectId = mboProjectId;
	}

	/**
	 * Returns the uiBaseUrl
	 * 
	 * @return the uiBaseUrl
	 */
	public String getUiBaseUrl() {
		return uiBaseUrl;
	}

	/**
	 * Sets the uiBaseUrl
	 * 
	 * @param uiBaseUrl
	 *            the uiBaseUrl to set
	 */
	public void setUiBaseUrl(String uiBaseUrl) {
		this.uiBaseUrl = uiBaseUrl;
	}

}
