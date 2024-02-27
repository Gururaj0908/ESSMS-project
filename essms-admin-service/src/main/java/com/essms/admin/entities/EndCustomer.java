/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.essms.core.enums.GenderType;
import com.essms.core.enums.MaritalStatus;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "end_customer")
@PrimaryKeyJoinColumn(name = "id")
public class EndCustomer extends Customer {

	@Column(length = 20)
	private String altMobileNo;

	@Enumerated
	@Column(columnDefinition = "smallint")
	private GenderType genderType;

	@Enumerated
	@Column(columnDefinition = "smallint")
	private MaritalStatus maritalStatus;

	private Date birthDate;

	private Date anniversaryDate;

	@OneToMany(mappedBy = "endCustomer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EndCustomerAddress> endCustomerAddresses = new ArrayList<>();

	@OneToMany(mappedBy = "endCustomer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EndCustomerCard> endCustomerCards;

	@OneToOne(mappedBy = "endCustomer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private EndCustomerPreference endCustomerPreference;

	/**
	 * Will Return the altMobileNo
	 *
	 * @return the altMobileNo
	 */
	public String getAltMobileNo() {
		return altMobileNo;
	}

	/**
	 * Pass the altMobileNo to be set
	 *
	 * @param altMobileNo
	 *            the altMobileNo to set
	 */
	public void setAltMobileNo(String altMobileNo) {
		this.altMobileNo = altMobileNo;
	}

	/**
	 * Will Return the maritalStatus
	 *
	 * @return the maritalStatus
	 */
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * Pass the maritalStatus to be set
	 *
	 * @param maritalStatus
	 *            the maritalStatus to set
	 */
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * Will Return the birthDate
	 *
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Pass the birthDate to be set
	 *
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Will Return the anniversaryDate
	 *
	 * @return the anniversaryDate
	 */
	public Date getAnniversaryDate() {
		return anniversaryDate;
	}

	/**
	 * Pass the anniversaryDate to be set
	 *
	 * @param anniversaryDate
	 *            the anniversaryDate to set
	 */
	public void setAnniversaryDate(Date anniversaryDate) {
		this.anniversaryDate = anniversaryDate;
	}

	/**
	 * Returns the genderType
	 * 
	 * @return the genderType
	 */
	public GenderType getGenderType() {
		return genderType;
	}

	/**
	 * Sets the genderType
	 * 
	 * @param genderType
	 *            the genderType to set
	 */
	public void setGenderType(GenderType genderType) {
		this.genderType = genderType;
	}

	/**
	 * Will Return the endCustomerAddresses
	 *
	 * @return the endCustomerAddresses
	 */
	public List<EndCustomerAddress> getEndCustomerAddresses() {
		return endCustomerAddresses;
	}

	/**
	 * Will add the EndCustomerAddress
	 *
	 * @return the endCustomerAddresses
	 */
	public void addEndCustomerAddress(EndCustomerAddress endCustomerAddress) {
		endCustomerAddress.setEndCustomer(this);
		endCustomerAddresses.add(endCustomerAddress);
	}

	/**
	 * Will Remove the endCustomerAddress
	 *
	 * @param endCustomerAddress
	 */
	public void removeEndCustomerAddress(EndCustomerAddress endCustomerAddress) {
		endCustomerAddress.setEndCustomer(null);
		this.endCustomerAddresses.remove(endCustomerAddress);
	}

	/**
	 * Returns the endCustomerCards
	 * 
	 * @return the endCustomerCards
	 */
	public List<EndCustomerCard> getEndCustomerCards() {
		return endCustomerCards;
	}

	/**
	 * Sets the endCustomerCards
	 * 
	 * @param endCustomerCards
	 *            the endCustomerCards to set
	 */
	public void setEndCustomerCards(List<EndCustomerCard> endCustomerCards) {
		this.endCustomerCards = endCustomerCards;
	}

	/**
	 * Returns the endCustomerPreference
	 * 
	 * @return the endCustomerPreference
	 */
	public EndCustomerPreference getEndCustomerPreference() {
		return endCustomerPreference;
	}

	/**
	 * Sets the endCustomerPreference
	 * 
	 * @param endCustomerPreference
	 *            the endCustomerPreference to set
	 */
	public void setEndCustomerPreference(EndCustomerPreference endCustomerPreference) {
		this.endCustomerPreference = endCustomerPreference;
	}

	/**
	 * Sets the endCustomerAddresses
	 * 
	 * @param endCustomerAddresses
	 *            the endCustomerAddresses to set
	 */
	public void setEndCustomerAddresses(List<EndCustomerAddress> endCustomerAddresses) {
		this.endCustomerAddresses = endCustomerAddresses;
	}

}
