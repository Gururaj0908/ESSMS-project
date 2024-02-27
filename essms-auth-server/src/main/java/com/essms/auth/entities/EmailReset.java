/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.entities.AuditableEntity;

/**
 * 
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class EmailReset extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	@SkipTextTransformation
	private String guid;

	@Column(length = 10)
	private String otp;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@Column(length = 200)
	private String emailId;

	@ManyToOne(fetch = FetchType.LAZY)
	private SystemUser systemUser;

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the guid
	 * 
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Sets the guid
	 * 
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * Returns the expiryDate
	 * 
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the expiryDate
	 * 
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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
	 * Returns the systemUser
	 * 
	 * @return the systemUser
	 */
	public SystemUser getSystemUser() {
		return systemUser;
	}

	/**
	 * Sets the systemUser
	 * 
	 * @param systemUser the systemUser to set
	 */
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

}
