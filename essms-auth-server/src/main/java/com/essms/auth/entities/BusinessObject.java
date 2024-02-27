/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.core.enums.RouteType;
import com.essms.hibernate.core.auditor.Auditable;
import com.essms.hibernate.core.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
@Table
// @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@NamedQueries({
		@NamedQuery(name = "findByUserAccess", query = "select bo from BusinessObject bo, UserPermission up where up.businessObject.id=bo.id and up.userGUID=:userGUID and bo.isDeleted=false order by bo.objectLevel asc, bo.displayOrder asc"),
		@NamedQuery(name = "findByUserNBranchAccess", query = "select bo from BusinessObject bo, UserPermission up where up.businessObject.id=bo.id and up.userGUID=:userGUID and up.branchGUID=:branchGUID and bo.isDeleted=false order by bo.objectLevel asc, bo.displayOrder asc"),
		@NamedQuery(name = "findByUserAccessForMobile", query = "select bo from BusinessObject bo, UserPermission up where up.businessObject.id=bo.id and up.userGUID=:userGUID and bo.isDeleted=false and bo.displayOnMobile = true and bo.isSelectable=true order by bo.objectLevel asc, bo.displayOrder asc"),
		@NamedQuery(name = "findByUserNBranchAccessForMobile", query = "select bo from BusinessObject bo, UserPermission up where up.businessObject.id=bo.id and up.userGUID=:userGUID and up.branchGUID=:branchGUID and bo.isDeleted=false and bo.displayOnMobile = true and bo.isSelectable=true order by bo.objectLevel asc, bo.displayOrder asc"),
		@NamedQuery(name = "findByRoleAccess", query = "select bo from BusinessObject bo, RolePermission rp where rp.businessObject.id=bo.id and rp.role.guid in :commaSeparatedRoles and bo.isDeleted=false and bo.isSelectable=true order by bo.displayOrder asc, bo.objectLevel asc"),
		@NamedQuery(name = "findByRoleAccessForMobile", query = "select bo from BusinessObject bo, RolePermission rp where rp.businessObject.id=bo.id and rp.role.guid in :commaSeparatedRoles and bo.isDeleted=false and bo.displayOnMobile = true and bo.isSelectable=true order by bo.displayOrder asc, bo.objectLevel asc"),
		@NamedQuery(name = "findAllActive", query = "select bo from BusinessObject bo where bo.isDeleted = false order by bo.objectLevel asc, bo.displayOrder asc"),
		@NamedQuery(name = "findAllActiveNSelectable", query = "select bo from BusinessObject bo where bo.isDeleted = false and bo.isSelectable=true order by bo.objectLevel asc, bo.displayOrder asc") })
public class BusinessObject extends BaseModel implements Comparable<BusinessObject>, Auditable {

	@Override
	@JsonIgnore
	public int compareTo(BusinessObject obj) {
		if (obj == null) {
			return 0;
		}
		if (obj.getDisplayOrder() == null || this.getDisplayOrder() == null) {
			return 0;
		}
		return this.displayOrder - obj.displayOrder;
	}

	@Id
	private Long id;

	@Column(length = 50)
	private String icon;

	@Column(length = 100, nullable = false)
	private String objectName;

	@Column(length = 100, nullable = false)
	private String displayTag;

	@Column(length = 600)
	private String url;

	@Column(length = 100)
	@JsonProperty("menuAction")
	private String action;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private BusinessObject parent;

	@Column(nullable = false)
	private Integer objectLevel;

	@Column(nullable = false)
	private Integer displayOrder;

	private Boolean displayOnMobile = false;

	private Boolean isSelectable = true;

	@Column(nullable = false)
	private Boolean isHidden;

	@Column(nullable = false)
	private Boolean isDeleted = Boolean.FALSE;

	@Column(length = 50)
	@SkipTextTransformation
	private String guid;

	@Enumerated
	@Column(columnDefinition = "smallint default 0", nullable = false)
	private RouteType routeType = RouteType.List;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	// @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@JsonIgnore
	private List<BusinessObject> children = new ArrayList<>(0);

	@Transient
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getObjectLevel() {
		return objectLevel;
	}

	public void setObjectLevel(Integer objectLevel) {
		this.objectLevel = objectLevel;
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

	public BusinessObject getParent() {
		return parent;
	}

	public void setParent(BusinessObject parent) {
		this.parent = parent;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return getParent() == null ? null : getParent().getId();
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Will Return the isDeleted
	 *
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Pass the isDeleted to be set
	 *
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * Returns the guid
	 * 
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Sets the guid
	 * 
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the children
	 */
	public List<BusinessObject> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<BusinessObject> children) {
		this.children = children;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BusinessObject other = (BusinessObject) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
