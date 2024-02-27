/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.essms.admin.constants.APIConstant;
import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class AddressModel {

	private Long id;

	@NotEmpty
	@Length(min = 1, max = 100)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Address Line 1", placeHolder = "Address Line 1")
	private String addressLine1;

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Address Line 2", placeHolder = "Address Line 2")
	private String addressLine2;

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Nearest LandMark", placeHolder = "Nearest LandMark")
	private String nearestLandMark;

	@Length(min = 10, max = 13)
	@Pattern(regexp = "^[+]?[0-9]*$", message = "Can only be prefixed with optional '+' followed by numbers only")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Phone No", placeHolder = "Phone No")
	private String phoneNo;

	@Length(min = 0, max = 20)
	@Pattern(regexp = "^[+]?[0-9]*$", message = "Can only be prefixed with optional '+' followed by numbers only")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Fax No", placeHolder = "Fax No")
	private String faxNo;

	@NotNull
	@FormFieldProperty(label = "State", placeHolder = "State", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_ALL_STATE)
	private Long stateId;

	private String state;

	@NotNull
	@FormFieldProperty(label = "City", placeHolder = "City", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_CITY_BY_STATE, parentKey = "stateId")
	private Long cityId;

	private String city;

	@NotNull
	@FormFieldProperty(label = "Area", placeHolder = "Area", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_AREA_BY_CITY, parentKey = "cityId")
	private Long areaId;

	private String area;

	@NotEmpty
	@Length(min = 4, max = 10)
	@Pattern(regexp = "^[0-9]*$", message = "Only numbers are allowed")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Pin Code", placeHolder = "Pin Code")
	private String pinCode;

	@Range(min = 0, max = 20)
	private Double latitude;

	@Range(min = 0, max = 20)
	private Double longitude;

	@SkipTextTransformation
	private String guid;

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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
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
	 * @param areaId the areaId to set
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
	 * @param cityId the cityId to set
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	/**
	 * Returns the state
	 * 
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state
	 * 
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Returns the city
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city
	 * 
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns the area
	 * 
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * Sets the area
	 * 
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
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

	/**
	 * Returns the guid
	 * 
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Sets the guid
	 * 
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

}
