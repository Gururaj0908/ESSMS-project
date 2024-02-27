/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models;

import java.util.Map;

import com.essms.core.enums.UserType;

/**
 * @author gaurav
 *
 */
public class LoginSuccess {

	private String access_token;
	private String token_type;
	private String refresh_token;
	private Integer expires_in;
	private String scope;
	private String userGUID;
	private String roleGUIDs;
	private UserType userType;
	private String name;
	private String emailId;
	private String mobileNo;
	private String username;
	private String branchGUID;
	private Map<String, String> allowedBranchGUIDs;
	private String jti;

	/**
	 * @return the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * @param access_token the access_token to set
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * @return the token_type
	 */
	public String getToken_type() {
		return token_type;
	}

	/**
	 * @param token_type the token_type to set
	 */
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	/**
	 * @return the refresh_token
	 */
	public String getRefresh_token() {
		return refresh_token;
	}

	/**
	 * @param refresh_token the refresh_token to set
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	/**
	 * @return the expires_in
	 */
	public Integer getExpires_in() {
		return expires_in;
	}

	/**
	 * @param expires_in the expires_in to set
	 */
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * @param userGUID the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * Will Return the roleGUIDs
	 *
	 * @return the roleGUIDs
	 */
	public String getRoleGUIDs() {
		return roleGUIDs;
	}

	/**
	 * Pass the roleGUIDs to be set
	 *
	 * @param roleGUIDs the roleGUIDs to set
	 */
	public void setRoleGUIDs(String roleGUIDs) {
		this.roleGUIDs = roleGUIDs;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Will Return the branchGUID
	 *
	 * @return the branchGUID
	 */
	public String getBranchGUID() {
		return branchGUID;
	}

	/**
	 * Pass the branchGUID to be set
	 *
	 * @param branchGUID the branchGUID to set
	 */
	public void setBranchGUID(String branchGUID) {
		this.branchGUID = branchGUID;
	}

	/**
	 * Will Return the userType
	 *
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * Pass the userType to be set
	 *
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the allowedBranchGUIDs
	 */
	public Map<String, String> getAllowedBranchGUIDs() {
		return allowedBranchGUIDs;
	}

	/**
	 * @param allowedBranchGUIDs the allowedBranchGUIDs to set
	 */
	public void setAllowedBranchGUIDs(Map<String, String> allowedBranchGUIDs) {
		this.allowedBranchGUIDs = allowedBranchGUIDs;
	}

	/**
	 * @return the jti
	 */
	public String getJti() {
		return jti;
	}

	/**
	 * @param jti the jti to set
	 */
	public void setJti(String jti) {
		this.jti = jti;
	}

}
