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
public class ListUserRole implements ListEntity {

	Long id;
	String userGUID;
	Long roleId;
	String roleGUID;
	String roleName;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Pass the id to be set
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Will Return the userGUID
	 *
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * Pass the userGUID to be set
	 *
	 * @param userGUID
	 *            the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * Will Return the roleId
	 *
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * Pass the roleId to be set
	 *
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Will Return the roleGUID
	 *
	 * @return the roleGUID
	 */
	public String getRoleGUID() {
		return roleGUID;
	}

	/**
	 * Pass the roleGUID to be set
	 *
	 * @param roleGUID
	 *            the roleGUID to set
	 */
	public void setRoleGUID(String roleGUID) {
		this.roleGUID = roleGUID;
	}

	/**
	 * Will Return the roleName
	 *
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Pass the roleName to be set
	 *
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
