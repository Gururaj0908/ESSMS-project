/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import com.essms.core.enums.OfficeType;
import com.essms.hibernate.core.models.ListEntity;

/**
 * @author gaurav
 *
 */
public class ListBranch implements ListEntity {

	private Long id;

	private String code;

	private String name;

	private OfficeType officeType;

	private String gstinNumber;

	private String emailId;

	private Boolean isServiceCenter;

	private Long addressId;

	private String addressLine1;

	private String addressLine2;

	private String nearestLandMark;

	private Long areaId;

	private String area;

	private Long cityId;

	private String city;

	private Long stateId;

	private String state;

	private String pinCode;

	private String phoneNo;

	private String faxNo;

	private Double latitude;

	private Double longitude;

	private Boolean isActive;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
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
	 * Will Return the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Pass the name to be set
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Will Return the code
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Pass the code to be set
	 *
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Will Return the gstinNumber
	 *
	 * @return the gstinNumber
	 */
	public String getGstinNumber() {
		return gstinNumber;
	}

	/**
	 * Pass the gstinNumber to be set
	 *
	 * @param gstinNumber
	 *            the gstinNumber to set
	 */
	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

	/**
	 * Will Return the emailId
	 *
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Pass the emailId to be set
	 *
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Will Return the isServiceCenter
	 *
	 * @return the isServiceCenter
	 */
	public Boolean getIsServiceCenter() {
		return isServiceCenter;
	}

	/**
	 * Pass the isServiceCenter to be set
	 *
	 * @param isServiceCenter
	 *            the isServiceCenter to set
	 */
	public void setIsServiceCenter(Boolean isServiceCenter) {
		this.isServiceCenter = isServiceCenter;
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
	 * @param officeType
	 *            the officeType to set
	 */
	public void setOfficeType(OfficeType officeType) {
		this.officeType = officeType;
	}

	/**
	 * Will Return the addressId
	 *
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * Pass the addressId to be set
	 *
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
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
	 * @param addressLine1
	 *            the addressLine1 to set
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
	 * @param addressLine2
	 *            the addressLine2 to set
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
	 * @param nearestLandMark
	 *            the nearestLandMark to set
	 */
	public void setNearestLandMark(String nearestLandMark) {
		this.nearestLandMark = nearestLandMark;
	}

	/**
	 * Will Return the area
	 *
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * Pass the area to be set
	 *
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * Will Return the city
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Pass the city to be set
	 *
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Will Return the state
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Pass the state to be set
	 *
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
	 * @param pinCode
	 *            the pinCode to set
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
	 * @param phoneNo
	 *            the phoneNo to set
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
	 * @param faxNo
	 *            the faxNo to set
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
	 * @param latitude
	 *            the latitude to set
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
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Will Return the areaId
	 *
	 * @return the areaId
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * Pass the areaId to be set
	 *
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * Will Return the cityId
	 *
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * Pass the cityId to be set
	 *
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	/**
	 * Will Return the stateId
	 *
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * Pass the stateId to be set
	 *
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * Will Return the isActive
	 *
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Pass the isActive to be set
	 *
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
