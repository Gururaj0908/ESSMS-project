/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

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
public class CourierContactDetail extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Courier courier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ContactDetail contactDetail;

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
	 * Will Return the courier
	 *
	 * @return the courier
	 */
	public Courier getCourier() {
		return courier;
	}

	/**
	 * Pass the courier to be set
	 *
	 * @param courier
	 *            the courier to set
	 */
	public void setCourier(Courier courier) {
		this.courier = courier;
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

}
