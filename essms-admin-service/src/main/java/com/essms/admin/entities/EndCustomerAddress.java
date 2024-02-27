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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.essms.core.enums.AddressType;
import com.essms.hibernate.core.entities.AuditableBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class EndCustomerAddress extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean isDefault;

	@Enumerated
	@Column(columnDefinition = "smallint", nullable = false)
	private AddressType addressType;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private EndCustomer endCustomer;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
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

	/**
	 * Will Return the endCustomer
	 *
	 * @return the endCustomer
	 */
	public EndCustomer getEndCustomer() {
		return endCustomer;
	}

	/**
	 * Pass the endCustomer to be set
	 *
	 * @param endCustomer
	 *            the endCustomer to set
	 */
	public void setEndCustomer(EndCustomer endCustomer) {
		this.endCustomer = endCustomer;
	}

	/**
	 * Will Return the addressType
	 *
	 * @return the addressType
	 */
	public AddressType getAddressType() {
		return addressType;
	}

	/**
	 * Pass the addressType to be set
	 *
	 * @param addressType
	 *            the addressType to set
	 */
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	/**
	 * Will Return the isDefault
	 *
	 * @return the isDefault
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * Pass the isDefault to be set
	 *
	 * @param isDefault
	 *            the isDefault to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
