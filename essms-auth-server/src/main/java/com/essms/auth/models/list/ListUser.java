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
public class ListUser implements ListEntity {

	private Long id;

	private String userGUID;

	private String branchGUID;

	private String name;

	private String mobileNo;

	private String emailId;

	private String username;

	private Boolean isActive;

	private Object permission;

	private Object resetPassword;

	private String createdBy;

	private String lastModifiedBy;

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
	 * Will Return the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Pass the name to be set
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Will Return the mobileNo
	 *
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Pass the mobileNo to be set
	 *
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Will Return the emailId
	 *
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Pass the emailId to be set
	 *
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Will Return the username
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Pass the username to be set
	 *
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Will Return the isActive
	 *
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Pass the isActive to be set
	 *
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(Object permission) {
		this.permission = permission;
	}

	/**
	 * @return the resetPassword
	 */
	public Object getResetPassword() {
		return resetPassword;
	}

	/**
	 * @param resetPassword the resetPassword to set
	 */
	public void setResetPassword(Object resetPassword) {
		this.resetPassword = resetPassword;
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
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
