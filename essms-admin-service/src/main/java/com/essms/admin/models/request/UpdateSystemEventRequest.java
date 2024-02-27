package com.essms.admin.models.request;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.core.enums.MenuCategoryType;
import com.essms.core.enums.RouteType;
import com.essms.core.enums.SystemEventType;
import com.essms.hibernate.core.models.UpdateEntity;

public class UpdateSystemEventRequest implements UpdateEntity {

	private Long id;

	private MenuCategoryType menuCategory;

	private SystemEventType systemEventType;

	private Long businessObjectId;

	@SkipTextTransformation
	private String menuAction;

	@SkipTextTransformation
	private String detailUrl;

	private RouteType routeType;

	private Integer displayOrder;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the menuCategory
	 */
	public MenuCategoryType getMenuCategory() {
		return menuCategory;
	}

	/**
	 * @param menuCategory the menuCategory to set
	 */
	public void setMenuCategory(MenuCategoryType menuCategory) {
		this.menuCategory = menuCategory;
	}

	/**
	 * @return the systemEventType
	 */
	public SystemEventType getSystemEventType() {
		return systemEventType;
	}

	/**
	 * @param systemEventType the systemEventType to set
	 */
	public void setSystemEventType(SystemEventType systemEventType) {
		this.systemEventType = systemEventType;
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
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

}
