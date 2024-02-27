/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import com.essms.admin.models.AddressModel;
import com.essms.admin.models.EmployeeModel;
import com.essms.hibernate.core.models.ListEntity;
import com.essms.hibernate.core.models.RegisterUserModel;

/**
 * @author gaurav
 *
 */
public class ListEmployee implements ListEntity {

	private Long id;

	private RegisterUserModel registerUserModel;

	private EmployeeModel employeeModel;

	private AddressModel addressModel;

	private Boolean isActive;

	private Object permission;

	private Object branchPermission;

	private Object resetPassword;

	private String guid;

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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Will Return the registerUserModel
	 *
	 * @return the registerUserModel
	 */
	public RegisterUserModel getRegisterUserModel() {
		return registerUserModel;
	}

	/**
	 * Pass the registerUserModel to be set
	 *
	 * @param registerUserModel the registerUserModel to set
	 */
	public void setRegisterUserModel(RegisterUserModel registerUserModel) {
		this.registerUserModel = registerUserModel;
	}

	/**
	 * Will Return the employeeModel
	 *
	 * @return the employeeModel
	 */
	public EmployeeModel getEmployeeModel() {
		return employeeModel;
	}

	/**
	 * Pass the employeeModel to be set
	 *
	 * @param employeeModel the employeeModel to set
	 */
	public void setEmployeeModel(EmployeeModel employeeModel) {
		this.employeeModel = employeeModel;
	}

	/**
	 * Will Return the addressModel
	 *
	 * @return the addressModel
	 */
	public AddressModel getAddressModel() {
		return addressModel;
	}

	/**
	 * Pass the addressModel to be set
	 *
	 * @param addressModel the addressModel to set
	 */
	public void setAddressModel(AddressModel addressModel) {
		this.addressModel = addressModel;
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
	 * @param isActive the isActive to set
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
	 * @param permission the permission to set
	 */
	public void setPermission(Object permission) {
		this.permission = permission;
	}

	/**
	 * @return the branchPermission
	 */
	public Object getBranchPermission() {
		return branchPermission;
	}

	/**
	 * @param branchPermission the branchPermission to set
	 */
	public void setBranchPermission(Object branchPermission) {
		this.branchPermission = branchPermission;
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
	 * Will Return the guid
	 *
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Pass the guid to be set
	 *
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
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