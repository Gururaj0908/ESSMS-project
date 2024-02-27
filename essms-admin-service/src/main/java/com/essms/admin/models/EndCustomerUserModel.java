/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import java.util.Set;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.constants.APIConstant;
import com.essms.hibernate.core.enums.FormEditorShowTime;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.enums.OperatorType;
import com.essms.hibernate.core.models.CreateEntity;
import com.essms.validator.annotation.ValidFieldMatch;

/**
 * @author gaurav
 *
 */
@ValidFieldMatch(field = "password", verifyField = "confirmPassword", message = "Password and ConfirmPassword don't match")
public class EndCustomerUserModel implements CreateEntity {

	@NotEmpty
	@Length(min = 1, max = 100)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Name", placeHolder = "Name")
	@Pattern(regexp = "^[A-z\\s\\.]+[A-z\\.]*[A-z\\.]+$", message = "Only alphabets allowed, dots and spaces allowed in between alphabets")
	private String name;

	@NotEmpty
	@Length(min = 10, max = 13)
	@Pattern(regexp = "^[+]?[0-9]*$", message = "Can only be prefixed with optional '+' followed by numbers only")
	@FormFieldProperty(formEditorType = FormEditorType.Phone, label = "Mobile No", placeHolder = "Mobile No")
	private String mobileNo;

	@NotEmpty
	@Length(min = 1, max = 200)
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Please enter a valid email id")
	@FormFieldProperty(formEditorType = FormEditorType.Email, label = "Email", placeHolder = "Email")
	private String emailId;

	@FormFieldProperty(label = "Is Walkin", placeHolder = "Is Walkin", formEditorType = FormEditorType.CheckBox, initialValue = "false", showTime = FormEditorShowTime.NewDataOnly)
	private Boolean isWalkin;

	@Length(min = 2, max = 100)
	@FormFieldProperty(formEditorType = FormEditorType.Text, label = "Username", placeHolder = "Username", validationURL = APIConstant.VALIDATE_USERNAME, showTime = FormEditorShowTime.NewDataOnly, visibilityDependent = true, visibleOnParentValue = "false", visibilityOperator = OperatorType.EQUALS, parentKey = "isWalkin")
	private String username;

	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Password, label = "Password", placeHolder = "Password", showTime = FormEditorShowTime.NewDataOnly, visibilityDependent = true, visibleOnParentValue = "false", visibilityOperator = OperatorType.EQUALS, parentKey = "isWalkin")
	@Length(min = 6, max = 50)
	private String password;

	@SkipTextTransformation
	@FormFieldProperty(formEditorType = FormEditorType.Password, label = "Confirm Password", placeHolder = "Confirm Password", showTime = FormEditorShowTime.NewDataOnly, accept = "password", visibilityDependent = true, visibleOnParentValue = "false", visibilityOperator = OperatorType.EQUALS, parentKey = "isWalkin")
	@Length(min = 6, max = 50)
	private String confirmPassword;

	private Set<String> roleGUIDs;

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
	 * Will Return the mobileNo
	 *
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Pass the mobileNo to be set
	 *
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
	 * @return the isWalkin
	 */
	public Boolean getIsWalkin() {
		return isWalkin;
	}

	/**
	 * @param isWalkin the isWalkin to set
	 */
	public void setIsWalkin(Boolean isWalkin) {
		this.isWalkin = isWalkin;
	}

	/**
	 * Will Return the username
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Pass the username to be set
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Will Return the password
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Pass the password to be set
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Will Return the roleGUIDs
	 *
	 * @return the roleGUIDs
	 */
	public Set<String> getRoleGUIDs() {
		return roleGUIDs;
	}

	/**
	 * Pass the roleGUIDs to be set
	 *
	 * @param roleGUIDs the roleGUIDs to set
	 */
	public void setRoleGUIDs(Set<String> roleGUIDs) {
		this.roleGUIDs = roleGUIDs;
	}

	/**
	 * Will Return the confirmPassword
	 *
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Pass the confirmPassword to be set
	 *
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
