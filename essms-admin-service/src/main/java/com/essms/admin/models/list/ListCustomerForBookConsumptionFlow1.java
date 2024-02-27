/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import com.essms.hibernate.core.models.ListEntity;

/**
 * @author gaurav
 *
 */
public class ListCustomerForBookConsumptionFlow1 implements ListEntity {

	private Object id;

	private Object name;
	
	private Object itemIds;

	private String mobileNo;

	private String emailId;

	private String username;

	private Object regNo;

	private String gstinNumber;

	private Object customerType;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
	public Object getId() {
		return id;
	}

	/**
	 * Pass the id to be set
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Object id) {
		this.id = id;
	}

	/**
	 * Will Return the name
	 *
	 * @return the name
	 */
	public Object getName() {
		return name;
	}

	/**
	 * Pass the name to be set
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(Object name) {
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
	 * Will Return the regNo
	 *
	 * @return the regNo
	 */
	public Object getRegNo() {
		return regNo;
	}

	/**
	 * Pass the regNo to be set
	 *
	 * @param regNo
	 *            the regNo to set
	 */
	public void setRegNo(Object regNo) {
		this.regNo = regNo;
	}

	/**
	 * Will Return the gstinNumber
	 *
	 * @return the gstinNumber
	 */
	public String getGstinNumber() {
		return gstinNumber;
	}

	/**
	 * Pass the gstinNumber to be set
	 *
	 * @param gstinNumber
	 *            the gstinNumber to set
	 */
	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

	/**
	 * Will Return the customerType
	 *
	 * @return the customerType
	 */
	public Object getCustomerType() {
		return customerType;
	}

	/**
	 * Pass the customerType to be set
	 *
	 * @param customerType
	 *            the customerType to set
	 */
	public void setCustomerType(Object customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the itemIds
	 */
	public Object getItemIds() {
		return itemIds;
	}

	/**
	 * @param itemIds the itemIds to set
	 */
	public void setItemIds(Object itemIds) {
		this.itemIds = itemIds;
	}

	
	
	

}
