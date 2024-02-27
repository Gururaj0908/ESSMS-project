/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import java.util.Map;

import com.essms.core.enums.RouteType;

/**
 * @author gaurav
 *
 */
public class SystemEventModel {

	private Long totalCount;

	private Long businessObjectId;

	private String menuAction;

	private String detailUrl;

	private RouteType routeType;

	private Map<String, Long> branchEventMap;

	/**
	 * @return the totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the businessObjectId
	 */
	public Long getBusinessObjectId() {
		return businessObjectId;
	}

	/**
	 * @param businessObjectId the businessObjectId to set
	 */
	public void setBusinessObjectId(Long businessObjectId) {
		this.businessObjectId = businessObjectId;
	}

	/**
	 * @return the menuAction
	 */
	public String getMenuAction() {
		return menuAction;
	}

	/**
	 * @param menuAction the menuAction to set
	 */
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	/**
	 * @return the detailUrl
	 */
	public String getDetailUrl() {
		return detailUrl;
	}

	/**
	 * @param detailUrl the detailUrl to set
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	/**
	 * @return the routeType
	 */
	public RouteType getRouteType() {
		return routeType;
	}

	/**
	 * @param routeType the routeType to set
	 */
	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	/**
	 * @return the branchEventMap
	 */
	public Map<String, Long> getBranchEventMap() {
		return branchEventMap;
	}

	/**
	 * @param branchEventMap the branchEventMap to set
	 */
	public void setBranchEventMap(Map<String, Long> branchEventMap) {
		this.branchEventMap = branchEventMap;
	}

}
