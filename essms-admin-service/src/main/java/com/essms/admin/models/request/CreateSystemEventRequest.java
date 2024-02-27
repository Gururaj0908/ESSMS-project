package com.essms.admin.models.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.core.enums.MenuCategoryType;
import com.essms.core.enums.RouteType;
import com.essms.core.enums.SystemEventType;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.CreateEntity;

public class CreateSystemEventRequest implements CreateEntity {

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "MenuCategoryType", placeHolder = "MenuCategoryType")
	private MenuCategoryType menuCategory;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "SystemEventType", placeHolder = "SystemEventType")
	private SystemEventType systemEventType;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.Number, label = "Business Object Id", placeHolder = "Business Object Id")
	private Long businessObjectId;

	@NotEmpty
	@Length(min = 1, max = 255)
	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Menu Action", placeHolder = "Menu Action")
	private String menuAction;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "RouteType", placeHolder = "RouteType")
	private RouteType routeType;

	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Detail URL", placeHolder = "Detail URL")
	private String detailUrl;

	@NotNull
	@FormFieldProperty(formEditorType = FormEditorType.Number, label = "Display Order", placeHolder = "Display Order")
	private Integer displayOrder;

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
