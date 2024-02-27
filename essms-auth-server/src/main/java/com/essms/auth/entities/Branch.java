/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.essms.core.enums.OfficeType;
import com.essms.hibernate.core.models.BaseModel;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
public class Branch extends BaseModel {

	@Id
	private Long id;

	@Column(length = 100, nullable = false)
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

	@Column(nullable = false)
	private Boolean isDeleted = Boolean.FALSE;

	@Column(nullable = false)
	private Boolean isActive = Boolean.TRUE;

	@Column(nullable = false, length = 50)
	private String guid;

	@Column(nullable = false, length = 100)
	private String createdBy;

	@Column(length = 100)
	private String lastModifiedBy;

	private Date createdDate;

	private Date lastModifiedDate;

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
	 * Will Return the isDeleted
	 *
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Pass the isDeleted to be set
	 *
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
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
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
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
	 * @param createdDate
	 *            the createdDate to set
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
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
