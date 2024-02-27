/**
 * This file is subject to the terms and conditions, as well as 
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.essms.core.annotation.SkipTextTransformation;

/**
 * @author gaurav
 *
 */
public class UpsertRolePermissionRequest {

	@NotNull
	@SkipTextTransformation
	private String roleGUID;

	@NotNull
	private List<Long> ids;

	/**
	 * Returns the roleGUID
	 * 
	 * @return the roleGUID
	 */
	public String getRoleGUID() {
		return roleGUID;
	}

	/**
	 * Sets the roleGUID
	 * 
	 * @param roleGUID
	 *            the roleGUID to set
	 */
	public void setRoleGUID(String roleGUID) {
		this.roleGUID = roleGUID;
	}

	/**
	 * Returns the ids
	 * 
	 * @return the ids
	 */
	public List<Long> getIds() {
		return ids;
	}

	/**
	 * Sets the ids
	 * 
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
