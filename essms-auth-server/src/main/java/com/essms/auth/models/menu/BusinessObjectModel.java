/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.menu;

import java.util.ArrayList;
import java.util.List;

import com.essms.core.enums.RouteType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */
public class BusinessObjectModel {

	private Long id;

	private String icon;

	private String objectName;

	private String displayTag;

	private String url;

	@JsonProperty("menuAction")
	private String action;

	private Integer objectLevel;

	private Integer displayOrder;

	private Boolean isHidden;

	private Boolean isDeleted = Boolean.FALSE;

	private RouteType routeType = RouteType.List;

	private Long parentId;

	private Boolean selected;

	private Boolean selectable;

	private List<BusinessObjectModel> children = new ArrayList<>();

	public BusinessObjectModel() {

	}

	public BusinessObjectModel(Long id, String objectName, String displayTag, String action, RouteType routeType) {
		this.id = id;
		this.objectName = objectName;
		this.displayTag = displayTag;
		this.action = action;
		this.routeType = routeType;
	}

	public BusinessObjectModel(Long id, String icon, String objectName, String displayTag, String action,
			Integer objectLevel, RouteType routeType, Long parentId) {
		this.id = id;
		this.icon = icon;
		this.objectName = objectName;
		this.displayTag = displayTag;
		this.action = action;
		this.objectLevel = objectLevel;
		this.routeType = routeType;
		this.parentId = parentId;
	}

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
	 * @param id
	 *            the id to set
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
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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
	 * @param objectName
	 *            the objectName to set
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
	 * @param displayTag
	 *            the displayTag to set
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
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the action
	 * 
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action
	 * 
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
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
	 * @param objectLevel
	 *            the objectLevel to set
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
	 * @param displayOrder
	 *            the displayOrder to set
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
	 * @param isHidden
	 *            the isHidden to set
	 */
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
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
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	 * @param routeType
	 *            the routeType to set
	 */
	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
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
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Returns the number of immediate children of this BusinessObjectModel.
	 * 
	 * @return the number of immediate children.
	 */
	public int getNumberOfChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	/**
	 * Returns the children
	 * 
	 * @return the children
	 */
	public List<BusinessObjectModel> getChildren() {
		return children;
	}

	/**
	 * Adds a child to the list of children for this BusinessObjectModel. The
	 * addition of the first child will create a new List<BusinessObjectModel>.
	 * 
	 * @param child
	 *            a BusinessObjectModel object to set.
	 */
	public void addChild(BusinessObjectModel child) {
		if (children == null) {
			children = new ArrayList<BusinessObjectModel>();
		}
		children.add(child);
	}

	/**
	 * Inserts a BusinessObjectModel at the specified position in the child list.
	 * Will * throw an ArrayIndexOutOfBoundsException if the index does not exist.
	 * 
	 * @param index
	 *            the position to insert at.
	 * @param child
	 *            the BusinessObjectModel object to insert.
	 * @throws IndexOutOfBoundsException
	 *             if thrown.
	 */
	public void insertChildAt(int index, BusinessObjectModel child) throws IndexOutOfBoundsException {
		if (index == getNumberOfChildren()) {
			// this is really an append
			addChild(child);
			return;
		} else {
			children.get(index); // just to throw the exception, and stop here
			children.add(index, child);
		}
	}

	/**
	 * Remove the BusinessObjectModel element at index index of the
	 * List<BusinessObjectModel>.
	 * 
	 * @param index
	 *            the index of the element to delete.
	 * @throws IndexOutOfBoundsException
	 *             if thrown.
	 */
	public void removeChildAt(int index) throws IndexOutOfBoundsException {
		children.remove(index);
	}

	/**
	 * Returns the selected
	 * 
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}

	/**
	 * Sets the selected
	 * 
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/**
	 * Returns the selectable
	 * 
	 * @return the selectable
	 */
	public Boolean getSelectable() {
		return selectable;
	}

	/**
	 * Sets the selectable
	 * 
	 * @param selectable
	 *            the selectable to set
	 */
	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

}
