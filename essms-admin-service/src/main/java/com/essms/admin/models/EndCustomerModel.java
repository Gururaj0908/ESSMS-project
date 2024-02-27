/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.essms.admin.constants.APIConstant;
import com.essms.core.enums.GenderType;
import com.essms.core.enums.MaritalStatus;
import com.essms.core.util.DateUtil;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.enums.FormEditorType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author gaurav
 *
 */
public class EndCustomerModel {

	private String imagePath;

	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Only alphanumberic characters allowed")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "GSTIN No", placeHolder = "GSTIN No")
	private String gstinNumber;

	@Length(max = 13)
	@Pattern(regexp = "^[+]?[0-9]*$", message = "Only numbers are allowed")
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Alt Mobile No", placeHolder = "Alt Mobile No")
	private String altMobileNo;

	@JsonFormat(pattern = DateUtil.YYYY_MM_DD)
	@FormFieldProperty(formEditorType = FormEditorType.Date, label = "Birth Date", placeHolder = "Birth Date")
	private Date birthDate;

	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Gender", placeHolder = "Gender")
	private GenderType genderType;

	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Marital Status", placeHolder = "Marital Status")
	private MaritalStatus maritalStatus;

	@JsonFormat(pattern = DateUtil.YYYY_MM_DD)
	@FormFieldProperty(formEditorType = FormEditorType.Date, label = "Anniversary Date", placeHolder = "Anniversary Date")
	private Date anniversaryDate;

	@FormFieldProperty(formEditorType = FormEditorType.SelectList, label = "Branch", placeHolder = "Branch", optionsURL = APIConstant.GET_ALL_BRANCH, roleGUIDproperties = RolePropertyConstant.ADMIN_ROLE)
	private Long branchId;

	private String regNo;

	private String guid;

	/**
	 * Returns the imagePath
	 * 
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Sets the imagePath
	 * 
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the gstinNumber
	 */
	public String getGstinNumber() {
		return gstinNumber;
	}

	/**
	 * @param gstinNumber the gstinNumber to set
	 */
	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

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
	 * @param altMobileNo the altMobileNo to set
	 */
	public void setAltMobileNo(String altMobileNo) {
		this.altMobileNo = altMobileNo;
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
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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
	 * @param genderType the genderType to set
	 */
	public void setGenderType(GenderType genderType) {
		this.genderType = genderType;
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
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
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
	 * @param anniversaryDate the anniversaryDate to set
	 */
	public void setAnniversaryDate(Date anniversaryDate) {
		this.anniversaryDate = anniversaryDate;
	}

	/**
	 * Will Return the branchId
	 *
	 * @return the branchId
	 */
	public Long getBranchId() {
		return branchId;
	}

	/**
	 * Pass the branchId to be set
	 *
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	/**
	 * Will Return the regNo
	 *
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}

	/**
	 * Pass the regNo to be set
	 *
	 * @param regNo the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * Will Return the guid
	 *
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Pass the guid to be set
	 *
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

}
