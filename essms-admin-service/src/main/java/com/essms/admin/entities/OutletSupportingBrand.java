/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

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
public class OutletSupportingBrand extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Outlet outlet;

	@Column(nullable = false)
	private String brandGUID;

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
	 * Will Return the brandGUID
	 *
	 * @return the brandGUID
	 */
	public String getBrandGUID() {
		return brandGUID;
	}

	/**
	 * Pass the brandGUID to be set
	 *
	 * @param brandGUID
	 *            the brandGUID to set
	 */
	public void setBrandGUID(String brandGUID) {
		this.brandGUID = brandGUID;
	}

}
