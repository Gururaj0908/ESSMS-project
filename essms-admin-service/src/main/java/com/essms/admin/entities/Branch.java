/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.essms.core.enums.OfficeType;
import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class Branch extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 100, unique = true)
	private String code;

	@Column(length = 20)
	private String gstinNumber;

	@Column(length = 200)
	private String emailId;

	private Boolean isServiceCenter;

	@Enumerated
	@Column(columnDefinition = "smallint", nullable = false)
	private OfficeType officeType;

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
	 * Will Return the code
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Pass the code to be set
	 *
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * Will Return the isServiceCenter
	 *
	 * @return the isServiceCenter
	 */
	public Boolean getIsServiceCenter() {
		return isServiceCenter;
	}

	/**
	 * Pass the isServiceCenter to be set
	 *
	 * @param isServiceCenter
	 *            the isServiceCenter to set
	 */
	public void setIsServiceCenter(Boolean isServiceCenter) {
		this.isServiceCenter = isServiceCenter;
	}

	/**
	 * Will Return the officeType
	 *
	 * @return the officeType
	 */
	public OfficeType getOfficeType() {
		return officeType;
	}

	/**
	 * Pass the officeType to be set
	 *
	 * @param officeType
	 *            the officeType to set
	 */
	public void setOfficeType(OfficeType officeType) {
		this.officeType = officeType;
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
