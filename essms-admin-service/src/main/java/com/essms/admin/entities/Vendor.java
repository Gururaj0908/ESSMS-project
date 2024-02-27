/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
public class Vendor extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200, nullable = false)
	private String name;

	@Column(length = 20, nullable = false)
	private String mobileNo;

	@Column(length = 200, nullable = false, unique = true)
	private String emailId;

	@Column(length = 100, nullable = false, unique = true)
	private String username;

	@Column(length = 20)
	private String gstinNumber;

	private String vendorURL;

	private String description;

	@OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VendorAddress> vendorAddresses = new ArrayList<>();

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
	 * @param id
	 *            the id to set
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
	 * Pass the vendorAddresses to be set
	 *
	 * @param vendorAddresses
	 *            the vendorAddresses to set
	 */
	public void setVendorAddresses(List<VendorAddress> vendorAddresses) {
		this.vendorAddresses = vendorAddresses;
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
	 * Will Return the vendorURL
	 *
	 * @return the vendorURL
	 */
	public String getVendorURL() {
		return vendorURL;
	}

	/**
	 * Pass the vendorURL to be set
	 *
	 * @param vendorURL
	 *            the vendorURL to set
	 */
	public void setVendorURL(String vendorURL) {
		this.vendorURL = vendorURL;
	}

	/**
	 * Will Return the description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Pass the description to be set
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Will Return the outletAddresses
	 *
	 * @return the outletAddresses
	 */
	public List<VendorAddress> getVendorAddresses() {
		return vendorAddresses;
	}

	/**
	 * Will add the VendorAddress
	 *
	 * @param vendorAddress
	 */
	public void addVendorAddress(VendorAddress vendorAddress) {
		vendorAddress.setVendor(this);
		vendorAddresses.add(vendorAddress);
	}

	/**
	 * Will Remove the vendorAddress
	 *
	 * @param vendorAddress
	 */
	public void removeVendorAddress(VendorAddress vendorAddress) {
		vendorAddress.setVendor(null);
		this.vendorAddresses.remove(vendorAddress);
	}

}
