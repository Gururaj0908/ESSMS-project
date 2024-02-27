/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.essms.core.enums.MenuCategoryType;
import com.essms.core.enums.RouteType;
import com.essms.core.enums.SystemEventType;
import com.essms.hibernate.core.entities.AuditableBaseEntity;
import com.essms.hibernate.core.models.ListEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
public class SystemEvent extends AuditableBaseEntity implements ListEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated
	@Column(columnDefinition = "smallint", nullable = false)
	private MenuCategoryType menuCategory;

	@Enumerated
	@Column(columnDefinition = "smallint", nullable = false, unique = true)
	private SystemEventType systemEventType;

	private Long totalCount = 0l;

	private Long businessObjectId;

	private String menuAction;

	private String detailUrl;

	@Enumerated
	@Column(columnDefinition = "smallint", nullable = false)
	private RouteType routeType;

	private Integer displayOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "systemEvent", fetch = FetchType.LAZY)
	private List<BranchEvent> branchEvents;

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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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

	/**
	 * @return the branchEvents
	 */
	public List<BranchEvent> getBranchEvents() {
		return branchEvents;
	}

	/**
	 * @param branchEvents the branchEvents to set
	 */
	public void setBranchEvents(List<BranchEvent> branchEvents) {
		this.branchEvents = branchEvents;
	}

}