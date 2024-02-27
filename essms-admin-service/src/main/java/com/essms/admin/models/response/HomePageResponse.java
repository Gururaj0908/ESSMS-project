/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.response;

import java.util.List;
import java.util.Map;

import com.essms.admin.models.SystemEventModel;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;

/**
 * @author gaurav
 *
 */
public class HomePageResponse {

	private Map<String, Map<String, List<SystemEventModel>>> categoryEventMap;

	private List<ListWithCountAndFormsResponse> extraDetails;

	/**
	 * @return the categoryEventMap
	 */
	public Map<String, Map<String, List<SystemEventModel>>> getCategoryEventMap() {
		return categoryEventMap;
	}

	/**
	 * @param categoryEventMap the categoryEventMap to set
	 */
	public void setCategoryEventMap(Map<String, Map<String, List<SystemEventModel>>> categoryEventMap) {
		this.categoryEventMap = categoryEventMap;
	}

	/**
	 * @return the extraDetails
	 */
	public List<ListWithCountAndFormsResponse> getExtraDetails() {
		return extraDetails;
	}

	/**
	 * @param extraDetails the extraDetails to set
	 */
	public void setExtraDetails(List<ListWithCountAndFormsResponse> extraDetails) {
		this.extraDetails = extraDetails;
	}

}
