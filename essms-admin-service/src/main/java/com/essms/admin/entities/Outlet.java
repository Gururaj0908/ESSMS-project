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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "outlet")
public class Outlet extends Customer {

	private String description;

	@Column(length = 100)
	private String websiteURL;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Address address;

	@OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OutletContactDetail> outletContactDetails = new ArrayList<>();

	@OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OutletSupportingBrand> outletSupportingBrands = new ArrayList<>();

	/**
	 * Will Return the outletContactDetails
	 *
	 * @return the outletContactDetails
	 */
	public List<OutletContactDetail> getOutletContactDetails() {
		return outletContactDetails;
	}

	/**
	 * Will add the outletContactDetail
	 *
	 * @param outletContactDetail
	 */
	public void addOutletContactDetail(OutletContactDetail outletContactDetail) {
		outletContactDetail.setOutlet(this);
		outletContactDetails.add(outletContactDetail);
	}

	/**
	 * Will Remove the outletContactDetail
	 *
	 * @param outletContactDetail
	 */
	public void removeOutletContactDetail(OutletContactDetail outletContactDetail) {
		outletContactDetail.setOutlet(null);
		this.outletContactDetails.remove(outletContactDetail);
	}

	/**
	 * Will Return the outletSupportingBrands
	 *
	 * @return the outletSupportingBrands
	 */
	public List<OutletSupportingBrand> getOutletSupportingBrands() {
		return outletSupportingBrands;
	}

	/**
	 * Will add the outletSupportingBrand
	 *
	 * @param outletSupportingBrand
	 */
	public void addOutletSupportingBrand(OutletSupportingBrand outletSupportingBrand) {
		outletSupportingBrand.setOutlet(this);
		outletSupportingBrands.add(outletSupportingBrand);
	}

	/**
	 * Will Remove the outletSupportingBrand
	 *
	 * @param outletSupportingBrand
	 */
	public void removeOutletSupportingBrand(OutletSupportingBrand outletSupportingBrand) {
		outletSupportingBrand.setOutlet(null);
		this.outletSupportingBrands.remove(outletSupportingBrand);
	}

	/**
	 * Will Return the description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Pass the description to be set
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Will Return the websiteURL
	 *
	 * @return the websiteURL
	 */
	public String getWebsiteURL() {
		return websiteURL;
	}

	/**
	 * Pass the websiteURL to be set
	 *
	 * @param websiteURL
	 *            the websiteURL to set
	 */
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
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
