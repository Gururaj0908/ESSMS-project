/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.list;

import com.essms.auth.entities.BusinessObject;
import com.essms.core.enums.RouteType;
import com.essms.hibernate.core.models.ListEntity;

/**
 * @author gaurav
 *
 */
public class ListBusinessObject implements ListEntity {

	private Long id;

	private String icon;

	private RouteType routeType;

	private String objectName;

	private String displayTag;

	private String url;

	private String menuAction;

	private BusinessObject parent;

	private Integer objectLevel;

	private Integer displayOrder;

	private Boolean isHidden;

	private Boolean displayOnMobile;

	private Boolean isDeleted = Boolean.FALSE;

	private Boolean isSelectable;

	private Long parentId;

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the icon
	 * 
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the icon
	 * 
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Returns the routeType
	 * 
	 * @return the routeType
	 */
	public RouteType getRouteType() {
		return routeType;
	}

	/**
	 * Sets the routeType
	 * 
	 * @param routeType the routeType to set
	 */
	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	/**
	 * Returns the objectName
	 * 
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * Sets the objectName
	 * 
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * Returns the displayTag
	 * 
	 * @return the displayTag
	 */
	public String getDisplayTag() {
		return displayTag;
	}

	/**
	 * Sets the displayTag
	 * 
	 * @param displayTag the displayTag to set
	 */
	public void setDisplayTag(String displayTag) {
		this.displayTag = displayTag;
	}

	/**
	 * Returns the url
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url
	 * 
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the menuAction
	 * 
	 * @return the menuAction
	 */
	public String getMenuAction() {
		return menuAction;
	}

	/**
	 * Sets the menuAction
	 * 
	 * @param menuAction the menuAction to set
	 */
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	/**
	 * Returns the parent
	 * 
	 * @return the parent
	 */
	public BusinessObject getParent() {
		return parent;
	}

	/**
	 * Sets the parent
	 * 
	 * @param parent the parent to set
	 */
	public void setParent(BusinessObject parent) {
		this.parent = parent;
	}

	/**
	 * Returns the objectLevel
	 * 
	 * @return the objectLevel
	 */
	public Integer getObjectLevel() {
		return objectLevel;
	}

	/**
	 * Sets the objectLevel
	 * 
	 * @param objectLevel the objectLevel to set
	 */
	public void setObjectLevel(Integer objectLevel) {
		this.objectLevel = objectLevel;
	}

	/**
	 * Returns the displayOrder
	 * 
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * Sets the displayOrder
	 * 
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * Returns the isHidden
	 * 
	 * @return the isHidden
	 */
	public Boolean getIsHidden() {
		return isHidden;
	}

	/**
	 * Sets the isHidden
	 * 
	 * @param isHidden the isHidden to set
	 */
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * @return the displayOnMobile
	 */
	public Boolean getDisplayOnMobile() {
		return displayOnMobile;
	}

	/**
	 * @param displayOnMobile the displayOnMobile to set
	 */
	public void setDisplayOnMobile(Boolean displayOnMobile) {
		this.displayOnMobile = displayOnMobile;
	}

	/**
	 * Returns the isDeleted
	 * 
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the isDeleted
	 * 
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	/**
	 * Returns the parentId
	 * 
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the parentId
	 * 
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
