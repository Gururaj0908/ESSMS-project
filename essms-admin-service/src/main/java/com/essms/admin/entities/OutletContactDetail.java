/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import javax.persistence.CascadeType;
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
public class OutletContactDetail extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Outlet outlet;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private ContactDetail contactDetail;

	private Boolean isSMSEnabled;

	private Boolean isEmailEnabled;

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
	 * Will Return the outlet
	 *
	 * @return the outlet
	 */
	public Outlet getOutlet() {
		return outlet;
	}

	/**
	 * Pass the outlet to be set
	 *
	 * @param outlet
	 *            the outlet to set
	 */
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	/**
	 * Will Return the contactDetail
	 *
	 * @return the contactDetail
	 */
	public ContactDetail getContactDetail() {
		return contactDetail;
	}

	/**
	 * Pass the contactDetail to be set
	 *
	 * @param contactDetail
	 *            the contactDetail to set
	 */
	public void setContactDetail(ContactDetail contactDetail) {
		this.contactDetail = contactDetail;
	}

	/**
	 * Will Return the isSMSEnabled
	 *
	 * @return the isSMSEnabled
	 */
	public Boolean getIsSMSEnabled() {
		return isSMSEnabled;
	}

	/**
	 * Pass the isSMSEnabled to be set
	 *
	 * @param isSMSEnabled
	 *            the isSMSEnabled to set
	 */
	public void setIsSMSEnabled(Boolean isSMSEnabled) {
		this.isSMSEnabled = isSMSEnabled;
	}

	/**
	 * Will Return the isEmailEnabled
	 *
	 * @return the isEmailEnabled
	 */
	public Boolean getIsEmailEnabled() {
		return isEmailEnabled;
	}

	/**
	 * Pass the isEmailEnabled to be set
	 *
	 * @param isEmailEnabled
	 *            the isEmailEnabled to set
	 */
	public void setIsEmailEnabled(Boolean isEmailEnabled) {
		this.isEmailEnabled = isEmailEnabled;
	}

}
