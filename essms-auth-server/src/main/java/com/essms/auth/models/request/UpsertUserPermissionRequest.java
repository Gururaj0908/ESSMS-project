/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;

/**
 * @author gaurav
 *
 */
public class UpsertUserPermissionRequest {

	@NotEmpty
	@SkipTextTransformation
	private String userGUID;

	@NotEmpty
	@SkipTextTransformation
	private String branchGUID;

	@SkipTextTransformation
	private Set<String> roleGUIDs;

	@NotNull
	private Set<Long> businessObjectIds;

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
	 * @param userGUID the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * @return the branchGUID
	 */
	public String getBranchGUID() {
		return branchGUID;
	}

	/**
	 * @param branchGUID the branchGUID to set
	 */
	public void setBranchGUID(String branchGUID) {
		this.branchGUID = branchGUID;
	}

	/**
	 * @return the businessObjectIds
	 */
	public Set<Long> getBusinessObjectIds() {
		return businessObjectIds;
	}

	/**
	 * Will Return the roleGUIDs
	 *
	 * @return the roleGUIDs
	 */
	public Set<String> getRoleGUIDs() {
		return roleGUIDs;
	}

	/**
	 * Pass the roleGUIDs to be set
	 *
	 * @param roleGUIDs the roleGUIDs to set
	 */
	public void setRoleGUIDs(Set<String> roleGUIDs) {
		this.roleGUIDs = roleGUIDs;
	}

	/**
	 * @param businessObjectIds the businessObjectIds to set
	 */
	public void setBusinessObjectIds(Set<Long> businessObjectIds) {
		this.businessObjectIds = businessObjectIds;
	}

}
