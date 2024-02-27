/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.mbo;

/**
 * @author gaurav
 *
 */
public class AccessControl {

	private String userId;
	private String projectItemId;
	private Boolean canView;
	private Boolean canEdit;
	private Boolean canApprove;

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
	 * Returns the projectItemId
	 * 
	 * @return the projectItemId
	 */
	public String getProjectItemId() {
		return projectItemId;
	}

	/**
	 * Sets the projectItemId
	 * 
	 * @param projectItemId
	 *            the projectItemId to set
	 */
	public void setProjectItemId(String projectItemId) {
		this.projectItemId = projectItemId;
	}

	/**
	 * Returns the canView
	 * 
	 * @return the canView
	 */
	public Boolean getCanView() {
		return canView;
	}

	/**
	 * Sets the canView
	 * 
	 * @param canView
	 *            the canView to set
	 */
	public void setCanView(Boolean canView) {
		this.canView = canView;
	}

	/**
	 * Returns the canEdit
	 * 
	 * @return the canEdit
	 */
	public Boolean getCanEdit() {
		return canEdit;
	}

	/**
	 * Sets the canEdit
	 * 
	 * @param canEdit
	 *            the canEdit to set
	 */
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	/**
	 * Returns the canApprove
	 * 
	 * @return the canApprove
	 */
	public Boolean getCanApprove() {
		return canApprove;
	}

	/**
	 * Sets the canApprove
	 * 
	 * @param canApprove
	 *            the canApprove to set
	 */
	public void setCanApprove(Boolean canApprove) {
		this.canApprove = canApprove;
	}

}
