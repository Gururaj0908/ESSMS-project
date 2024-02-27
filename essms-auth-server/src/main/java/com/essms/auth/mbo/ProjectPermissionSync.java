/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.mbo;

import java.util.List;

/**
 * @author gaurav
 *
 */
public class ProjectPermissionSync {

	private String userId;

	private List<String> projectItemIds;

	/**
	 * Returns the userId
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the userId
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Returns the projectItemIds
	 * 
	 * @return the projectItemIds
	 */
	public List<String> getProjectItemIds() {
		return projectItemIds;
	}

	/**
	 * Sets the projectItemIds
	 * 
	 * @param projectItemIds
	 *            the projectItemIds to set
	 */
	public void setProjectItemIds(List<String> projectItemIds) {
		this.projectItemIds = projectItemIds;
	}

}
