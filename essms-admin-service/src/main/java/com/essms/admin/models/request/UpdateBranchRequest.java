/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.essms.admin.constants.APIConstant;
import com.essms.core.enums.OfficeType;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.UpdateEntity;

/**
 * @author gaurav
 *
 */
public class UpdateBranchRequest implements UpdateEntity {

	@NotNull
	private Long id;

	@NotEmpty
	@Length(min = 1, max = 100)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Name", placeHolder = "Name", autoFocus = true)
	private String name;

	@NotEmpty
	@Length(min = 1, max = 100)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Code", placeHolder = "Code")
	private String code;

	@Length(min = 0, max = 20)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "GSTIN Number", placeHolder = "GSTIN Number")
	private String gstinNumber;

	@Length(min = 0, max = 200)
	@FormFieldProperty(formEditorType = FormEditorType.Email, label = "Email", placeHolder = "Email")
	private String emailId;

	@FormFieldProperty(formEditorType = FormEditorType.CheckBox, label = "IsServiceCenter", placeHolder = "IsServiceCenter")
	private Boolean isServiceCenter;

	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Office Type", placeHolder = "Office Type")
	private OfficeType officeType;

	@NotEmpty
	@Length(min = 1, max = 100)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Address Line 1", placeHolder = "Address Line 1")
	private String addressLine1;

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Address Line 2", placeHolder = "Address Line 2")
	private String addressLine2;

	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Nearest Landmark", placeHolder = "Nearest Landmark")
	private String nearestLandMark;

	@Length(min = 0, max = 20)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Phone No", placeHolder = "Phone No")
	private String phoneNo;

	@Length(min = 0, max = 20)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Fax No", placeHolder = "Fax No")
	private String faxNo;

	@FormFieldProperty(label = "State", placeHolder = "State", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_ALL_STATE)
	private Long stateId;

	@FormFieldProperty(label = "City", placeHolder = "City", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_CITY_BY_STATE, parentKey = "stateId")
	private Long cityId;

	@FormFieldProperty(label = "Area", placeHolder = "Area", formEditorType = FormEditorType.SelectList, optionsURL = APIConstant.GET_AREA_BY_CITY, parentKey = "cityId")
	private Long areaId;

	@NotEmpty
	@Length(min = 4, max = 20)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Pin Code", placeHolder = "Pin Code")
	private String pinCode;

	@Range(min = 0, max = 20)
	private Double latitude;

	@Range(min = 0, max = 20)
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
	@Override
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
	 * @param name the name to set
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
	 * @param code the code to set
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
	 * @param gstinNumber the gstinNumber to set
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
	 * @param emailId the emailId to set
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
	 * @param isServiceCenter the isServiceCenter to set
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
	 * @param officeType the officeType to set
	 */
	public void setOfficeType(OfficeType officeType) {
		this.officeType = officeType;
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

}
