/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.essms.core.enums.UserType;

/**
 * The Class OauthUser.
 */
public class CustomUserDetails implements UserDetails {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private String userGUID;

	/** The name. */
	private String name;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	private String emailId;

	private String mobileNo;

	private String roleGUIDs;

	private String branchGUID;

	private Map<String, String> allowedBranchGUIDs;

	private UserType userType;

	private Date lastLoginTime;

	private List<GrantedAuthority> authorities = new ArrayList<>();

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is enabled, <code>false</code>
	 *         otherwise
	 */
	private boolean enabled;

	/**
	 * Indicates whether the user's account has expired. An expired account cannot
	 * be authenticated.
	 *
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 *         <code>false</code> if no longer valid (ie expired)
	 */
	boolean accountNonExpired;

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is not locked, <code>false</code>
	 *         otherwise
	 */
	boolean accountNonLocked;

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 *
	 * @return <code>true</code> if the user's credentials are valid (ie
	 *         non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	boolean credentialsNonExpired;

	public Date getLastLoginTime() {
		return lastLoginTime;
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
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getAuthorities( )
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		// we never lock accounts
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// credentials never expire
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
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
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
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
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

}
