/**
 *
 */
package com.essms.auth.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.core.enums.UserType;
import com.essms.hibernate.core.auditor.Auditable;
import com.essms.hibernate.core.models.BaseModel;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class SystemUser extends BaseModel implements Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200, nullable = false)
	private String name;

	@Column(length = 20)
	private String mobileNo;

	@Column(length = 200, nullable = false, unique = true)
	private String emailId;

	@Column(length = 100, nullable = false, unique = true)
	private String username;

	@Column(length = 500, nullable = false)
	private String password;

	@SkipTextTransformation
	@Column(name = "guid", length = 50, nullable = false)
	private String guid;

	@SkipTextTransformation
	@Column(name = "branch_guid", length = 50)
	private String branchGUID;

	@Enumerated
	@Column(columnDefinition = "smallint")
	private UserType userType;

	@Column(nullable = false, length = 100)
	private String createdBy;

	private String lastModifiedBy;

	@Column(nullable = false, length = 100)
	private Date createdDate;

	private Date lastModifiedDate;

	private Date lastLoginTime;

	@Column(nullable = false)
	private boolean enabled = true;

	@Column(nullable = false)
	private boolean accountNonExpired = true;

	@Column(nullable = false)
	private boolean accountNonLocked = true;

	@Column(nullable = false)
	private boolean credentialsNonExpired = true;

	@OneToMany(mappedBy = "systemUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserRole> userRoles = new ArrayList<>();

	@OneToMany(mappedBy = "systemUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserBranch> userBranches = new ArrayList<>();

	@Column
	private Boolean isDeleted = Boolean.FALSE;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Pass the id to be set
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param name the name to set
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
	 * @param mobileNo the mobileNo to set
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
	 * @param emailId the emailId to set
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
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Will Return the password
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Pass the password to be set
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
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
	 * Will Return the lastLoginTime
	 *
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * Pass the lastLoginTime to be set
	 *
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * Will Return the createdBy
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Pass the createdBy to be set
	 *
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Will Return the lastModifiedBy
	 *
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Pass the lastModifiedBy to be set
	 *
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Will Return the createdDate
	 *
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Pass the createdDate to be set
	 *
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Will Return the lastModifiedDate
	 *
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * Pass the lastModifiedDate to be set
	 *
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Pass the userRoles to be set
	 *
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * Will Return the enabled
	 *
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Pass the enabled to be set
	 *
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Will Return the accountNonExpired
	 *
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
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
	 * Pass the accountNonExpired to be set
	 *
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * Will Return the accountNonLocked
	 *
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * Pass the accountNonLocked to be set
	 *
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * Will Return the credentialsNonExpired
	 *
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * Pass the credentialsNonExpired to be set
	 *
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * Will Return the userRoles
	 *
	 * @return the userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * Will add the UserRole
	 *
	 * @param userRole
	 */
	public void addUserRole(UserRole userRole) {
		userRole.setSystemUser(this);
		userRoles.add(userRole);
	}

	/**
	 * Will Remove the UserRole
	 *
	 * @param userRole
	 */
	public void removeUserRole(UserRole userRole) {
		userRole.setSystemUser(null);
		this.userRoles.remove(userRole);
	}

	/**
	 * Returns the isDeleted
	 * 
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the isDeleted
	 * 
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the userBranches
	 */
	public List<UserBranch> getUserBranches() {
		return userBranches;
	}

	/**
	 * @param userBranches the userBranches to set
	 */
	public void setUserBranches(List<UserBranch> userBranches) {
		this.userBranches = userBranches;
	}

}
