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
import javax.persistence.ManyToOne;

import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
public class Address extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String addressLine1;

	private String addressLine2;

	private String nearestLandMark;

	@ManyToOne(fetch = FetchType.LAZY)
	private Area area;

	@ManyToOne(fetch = FetchType.LAZY)
	private City city;

	@Column(nullable = false, length = 20)
	private String pinCode;

	@Column(length = 20)
	private String phoneNo;

	@Column(length = 20)
	private String faxNo;

	@Column(length = 20)
	private Double latitude;

	@Column(length = 20)
	private Double longitude;

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
	 * Will Return the addressLine1
	 *
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * Pass the addressLine1 to be set
	 *
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * Will Return the addressLine2
	 *
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * Pass the addressLine2 to be set
	 *
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * Will Return the nearestLandMark
	 *
	 * @return the nearestLandMark
	 */
	public String getNearestLandMark() {
		return nearestLandMark;
	}

	/**
	 * Pass the nearestLandMark to be set
	 *
	 * @param nearestLandMark the nearestLandMark to set
	 */
	public void setNearestLandMark(String nearestLandMark) {
		this.nearestLandMark = nearestLandMark;
	}

	/**
	 * Will Return the area
	 *
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * Pass the area to be set
	 *
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * Will Return the city
	 *
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Pass the city to be set
	 *
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Will Return the pinCode
	 *
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * Pass the pinCode to be set
	 *
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * Will Return the phoneNo
	 *
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * Pass the phoneNo to be set
	 *
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * Will Return the faxNo
	 *
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * Pass the faxNo to be set
	 *
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * Will Return the latitude
	 *
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Pass the latitude to be set
	 *
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Will Return the longitude
	 *
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Pass the longitude to be set
	 *
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getAddressLine1() + ", " + (getAddressLine2() != null ? getAddressLine2() + ", " : "")
				+ (getArea() != null ? getArea().getName() + ", " : "")
				+ (getCity() != null ? getCity().getName() + ", " + getCity().getState().getName() : "");
	}
}
