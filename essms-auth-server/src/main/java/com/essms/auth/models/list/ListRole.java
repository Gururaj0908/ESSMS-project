/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.list;

import com.essms.hibernate.core.models.ListEntity;

/**
 * @author gaurav
 *
 */
public class ListRole implements ListEntity {

	private Long id;

	private String guid;

	private String name;

	private Boolean isActive;

	private Boolean isSelectable;

	private Boolean isSystemDefined;

	private Object permission;

	private String createdBy;

	private String lastModifiedBy;

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
	 * Returns the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the isActive
	 * 
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Sets the isActive
	 * 
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	 * @return the isSystemDefined
	 */
	public Boolean getIsSystemDefined() {
		return isSystemDefined;
	}

	/**
	 * @param isSystemDefined the isSystemDefined to set
	 */
	public void setIsSystemDefined(Boolean isSystemDefined) {
		this.isSystemDefined = isSystemDefined;
	}

	/**
	 * Returns the permission
	 * 
	 * @return the permission
	 */
	public Object getPermission() {
		return permission;
	}

	/**
	 * Sets the permission
	 * 
	 * @param permission the permission to set
	 */
	public void setPermission(Object permission) {
		this.permission = permission;
	}

	/**
	 * Returns the createdBy
	 * 
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the createdBy
	 * 
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Returns the lastModifiedBy
	 * 
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the lastModifiedBy
	 * 
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
