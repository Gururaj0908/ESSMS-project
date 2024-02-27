/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.models.UpdateEntity;

/**
 * @author gaurav
 *
 */
public class UpdateTenantConfigurationRequest implements UpdateEntity {

	private Long id;

	@SkipTextTransformation
	private String tenantId;

	@SkipTextTransformation
	private String mboProjectId;

	@SkipTextTransformation
	private String uiBaseUrl;

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

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
