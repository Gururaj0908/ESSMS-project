/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class Employee extends AuditableBaseEntity {

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

	private Date birthDate;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Address address;

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
	 * Will Return the birthDate
	 *
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Pass the birthDate to be set
	 *
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Will Return the branch
	 *
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * Pass the branch to be set
	 *
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * Will Return the address
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Pass the address to be set
	 *
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

}
