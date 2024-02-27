/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.essms.core.enums.Designation;
import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class ContactDetail extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String contactPerson;

	@Column(nullable = false, length = 20)
	private String contactMobile;

	@Column(length = 200)
	private String contactEmailId;

	@Enumerated
	@Column(columnDefinition = "smallint")
	private Designation designation;

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
	 * Will Return the contactPerson
	 *
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * Pass the contactPerson to be set
	 *
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * Will Return the contactMobile
	 *
	 * @return the contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * Pass the contactMobile to be set
	 *
	 * @param contactMobile
	 *            the contactMobile to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * Will Return the contactEmailId
	 *
	 * @return the contactEmailId
	 */
	public String getContactEmailId() {
		return contactEmailId;
	}

	/**
	 * Pass the contactEmailId to be set
	 *
	 * @param contactEmailId
	 *            the contactEmailId to set
	 */
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}

	/**
	 * Will Return the designation
	 *
	 * @return the designation
	 */
	public Designation getDesignation() {
		return designation;
	}

	/**
	 * Pass the designation to be set
	 *
	 * @param designation
	 *            the designation to set
	 */
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

}
