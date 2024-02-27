/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.response;

import java.util.Map;

import com.essms.auth.models.menu.BusinessObjectModel;
import com.essms.hibernate.core.models.FormList;

/**
 * @author gaurav
 *
 */
public class MenuTreeResponse {

	private FormList formList;

	private BusinessObjectModel businessObjectModel;

	private Map<String, Object> configuration;

	/**
	 * Returns the formList
	 * 
	 * @return the formList
	 */
	public FormList getFormList() {
		return formList;
	}

	/**
	 * Sets the formList
	 * 
	 * @param formList
	 *            the formList to set
	 */
	public void setFormList(FormList formList) {
		this.formList = formList;
	}

	/**
	 * Returns the businessObjectModel
	 * 
	 * @return the businessObjectModel
	 */
	public BusinessObjectModel getBusinessObjectModel() {
		return businessObjectModel;
	}

	/**
	 * Sets the businessObjectModel
	 * 
	 * @param businessObjectModel
	 *            the businessObjectModel to set
	 */
	public void setBusinessObjectModel(BusinessObjectModel businessObjectModel) {
		this.businessObjectModel = businessObjectModel;
	}

	/**
	 * Returns the configuration
	 * 
	 * @return the configuration
	 */
	public Map<String, Object> getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the configuration
	 * 
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(Map<String, Object> configuration) {
		this.configuration = configuration;
	}

}
