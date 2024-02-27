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
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.essms.core.annotation.SkipAudit;
import com.essms.core.enums.OfficeType;
import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class VendorAddress extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated
	@Column(columnDefinition = "smallint", nullable = false)
	private OfficeType officeType;

	@SkipAudit
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Vendor vendor;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Address address;

	@OneToMany(mappedBy = "vendorAddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<VendorContactDetail> vendorContactDetails = new ArrayList<>();

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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Will Return the vendor
	 *
	 * @return the vendor
	 */
	public Vendor getVendor() {
		return vendor;
	}

	/**
	 * Pass the vendor to be set
	 *
	 * @param vendor the vendor to set
	 */
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Will Return the vendorContactDetails
	 *
	 * @return the vendorContactDetails
	 */
	public List<VendorContactDetail> getVendorContactDetails() {
		return vendorContactDetails;
	}

	/**
	 * Will add the vendorContactDetail
	 *
	 * @param vendorContactDetail
	 */
	public void addVendorContactDetail(VendorContactDetail vendorContactDetail) {
		vendorContactDetail.setVendorAddress(this);
		vendorContactDetails.add(vendorContactDetail);
	}

	/**
	 * Will Remove the vendorContactDetail
	 *
	 * @param vendorContactDetail
	 */
	public void removeVendorContactDetail(VendorContactDetail vendorContactDetail) {
		vendorContactDetail.setVendorAddress(null);
		this.vendorContactDetails.remove(vendorContactDetail);
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
	 * @param officeType the officeType to set
	 */
	public void setOfficeType(OfficeType officeType) {
		this.officeType = officeType;
	}

}
