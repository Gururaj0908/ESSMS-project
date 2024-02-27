/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.core.enums.RouteType;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;
import com.essms.hibernate.core.models.UpdateEntity;

/**
 * @author gaurav
 *
 */
public class UpsertBusiessObjectRequest implements CreateEntity, UpdateEntity {

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.Number, label = "Id", placeHolder = "Id", autoFocus = true)
	private Long id;

	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Icon", placeHolder = "Icon")
	private String icon;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Route Type", placeHolder = "Route Type")
	private RouteType routeType;

	@NotEmpty
	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Object Name", placeHolder = "Object Name")
	private String objectName;

	@NotEmpty
	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Display Tag", placeHolder = "Display Tag")
	private String displayTag;

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Action", placeHolder = "Action")
	@SkipTextTransformation
	private String menuAction;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.Number, label = "Object Level", placeHolder = "Object Level")
	private Integer objectLevel;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.Number, label = "Display Order", placeHolder = "Display Order")
	private Integer displayOrder;

	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.MultilineText, label = "URL", placeHolder = "URL")
	private String url;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.CheckBox, label = "Is Hidden", placeHolder = "Is Hidden", initialValue = "false")
	private Boolean isHidden;

	@FormFieldProperty(formEditorType = FormEditorType.CheckBox, label = "Display On Mobile", placeHolder = "Display On Mobile", initialValue = "false")
	private Boolean displayOnMobile;

	@FormFieldProperty(label = "Is Selectable", placeHolder = "Is Selectable", formEditorType = FormEditorType.CheckBox, initialValue = "true", roleGUIDproperties = {
			RolePropertyConstant.SUPER_ADMIN_ROLE })
	private Boolean isSelectable;

	@FormFieldProperty(formEditorType = FormEditorType.Number, label = "Parent Id", placeHolder = "Parent Id")
	private Long parentId;

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

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getDisplayTag() {
		return displayTag;
	}

	public void setDisplayTag(String displayTag) {
		this.displayTag = displayTag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Will Return the menuAction
	 *
	 * @return the menuAction
	 */
	public String getMenuAction() {
		return menuAction;
	}

	/**
	 * Pass the menuAction to be set
	 *
	 * @param menuAction the menuAction to set
	 */
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	public Integer getObjectLevel() {
		return objectLevel;
	}

	public void setObjectLevel(Integer objectLevel) {
		this.objectLevel = objectLevel;
	}

	/**
	 * @return the isHidden
	 */
	public Boolean getIsHidden() {
		return isHidden;
	}

	/**
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

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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

}
