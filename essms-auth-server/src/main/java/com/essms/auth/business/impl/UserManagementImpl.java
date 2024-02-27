/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essms.auth.business.UserManagement;
import com.essms.auth.business.UserRoleManagement;
import com.essms.auth.entities.SystemUser;
import com.essms.auth.models.ValidateEmail;
import com.essms.auth.models.ValidateUsername;
import com.essms.auth.models.request.UpsertUserRoleRequest;
import com.essms.auth.models.request.UserSignUpRequest;
import com.essms.auth.models.response.UserSignUpResponse;
import com.essms.core.enums.UserType;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ExceptionCode;
import com.essms.core.exception.ExceptionMessage;
import com.essms.core.exception.ValidationException;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.response.ValueResponse;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.tenant.context.TenantContext;

/**
 * @author gaurav
 *
 */
@Service
public class UserManagementImpl implements UserManagement {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private UserRoleManagement userRoleManagement;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.auth.business.UserManagement#userSignUp(com.ssms.auth.models.
	 * request.UserSignUpRequest)
	 */
	@Override
	public UserSignUpResponse userSignUp(UserSignUpRequest userSignUpRequest) {
		UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
		String tenantId = UserInfoUtil.getTenantId();
		if (StringUtils.isEmpty(tenantId)) {
			throw new ApplicationException(ExceptionMessage.MISSING_HEADER_TENANTID, ExceptionCode.INTERNAL_SERVER);
		}
		TenantContext.setCurrentTenant(tenantId);
		SystemUser systemUser = new SystemUser();
		String guid = UUID.randomUUID().toString();
		systemUser.setName(userSignUpRequest.getName());
		systemUser.setUsername(userSignUpRequest.getUsername());
		systemUser.setPassword(bCryptPasswordEncoder.encode(userSignUpRequest.getPassword()));
		systemUser.setEmailId(userSignUpRequest.getEmailId());
		systemUser.setMobileNo(userSignUpRequest.getMobileNo());
		systemUser.setGuid(guid);
		systemUser.setUserType(UserType.CUSTOMER);
		systemUser.setCreatedBy(userSignUpRequest.getUsername());
		systemUser.setLastModifiedBy(userSignUpRequest.getUsername());
		systemUser.setCreatedDate(new Date());
		systemUser.setLastModifiedDate(new Date());
		genericRepository.save(systemUser);
		userSignUpResponse.setUserGUID(guid);
		return userSignUpResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.UserManagement#registerSystemUser(com.ssms.core.models
	 * .RegisterUserModel, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public UserSignUpResponse registerSystemUser(RegisterUserModel registerUserModel, HttpServletResponse response)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
		String tenantId = UserInfoUtil.getTenantId();
		if (StringUtils.isEmpty(tenantId)) {
			throw new ApplicationException(ExceptionMessage.MISSING_HEADER_TENANTID, ExceptionCode.INTERNAL_SERVER);
		}
		TenantContext.setCurrentTenant(tenantId);
		SystemUser systemUser = new SystemUser();
		String guid = UUID.randomUUID().toString();
		systemUser.setName(registerUserModel.getName());
		systemUser.setUsername(registerUserModel.getUsername());
		systemUser.setPassword(bCryptPasswordEncoder.encode(registerUserModel.getPassword()));
		systemUser.setEmailId(registerUserModel.getEmailId());
		systemUser.setMobileNo(registerUserModel.getMobileNo());
		systemUser.setGuid(guid);
		genericRepository.save(systemUser);
		if (registerUserModel.getRoleGUIDs() != null && !registerUserModel.getRoleGUIDs().isEmpty()) {
			UpsertUserRoleRequest upsertUserRoleRequest = new UpsertUserRoleRequest();
			upsertUserRoleRequest.setUserGUID(guid);
			upsertUserRoleRequest.setRoleGUIDs(registerUserModel.getRoleGUIDs());
			userRoleManagement.upsertUserRole(upsertUserRoleRequest);
		}
		userSignUpResponse.setUserGUID(guid);
		return userSignUpResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.UserManagement#updateByUserGUID(com.ssms.hibernate.
	 * core.models.RegisterUserModel, java.lang.String)
	 */
	@Override
	@Transactional
	public void updateByUserGUID(RegisterUserModel registerUserModel, String userGUID) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String tenantId = UserInfoUtil.getTenantId();
		if (StringUtils.isEmpty(tenantId)) {
			throw new ApplicationException(ExceptionMessage.MISSING_HEADER_TENANTID, ExceptionCode.INTERNAL_SERVER);
		}
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("guid", userGUID);
		SystemUser systemUser = genericRepository.findByCriteria(SystemUser.class, criterias).get(0);
		if (StringUtils.isNotBlank(registerUserModel.getName())) {
			systemUser.setName(registerUserModel.getName());
		}
		if (StringUtils.isNotBlank(registerUserModel.getUsername())) {
			systemUser.setUsername(registerUserModel.getUsername());
		}
		if (StringUtils.isNotBlank(registerUserModel.getPassword())) {
			systemUser.setPassword(bCryptPasswordEncoder.encode(registerUserModel.getPassword()));
		}
		if (StringUtils.isNotBlank(registerUserModel.getEmailId())) {
			systemUser.setEmailId(registerUserModel.getEmailId());
		}
		if (StringUtils.isNotBlank(registerUserModel.getMobileNo())) {
			systemUser.setMobileNo(registerUserModel.getMobileNo());
		}
		if (registerUserModel.getRoleGUIDs() != null && !registerUserModel.getRoleGUIDs().isEmpty()) {
			UpsertUserRoleRequest upsertUserRoleRequest = new UpsertUserRoleRequest();
			upsertUserRoleRequest.setUserGUID(userGUID);
			upsertUserRoleRequest.setRoleGUIDs(registerUserModel.getRoleGUIDs());
			userRoleManagement.upsertUserRole(upsertUserRoleRequest);
		}
		genericRepository.saveOrUpdate(systemUser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.UserManagement#validateUsername(java.lang.String)
	 */
	@Override
	public void validateUsername(ValidateUsername validateUsername) throws ValidationException {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("username", validateUsername.getUsername());
		if (!genericRepository.isUnique(SystemUser.class, validateUsername.getId(), criterias)) {
			throw new ValidationException(
					FieldErrorUtil.generateFieldErrors("SystemUser", "username", "Username already taken"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.UserManagement#validateEmail(com.essms.auth.models.
	 * ValidateEmail)
	 */
	@Override
	public void validateUniqueEmail(ValidateEmail validateEmail) throws ValidationException {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("emailId", validateEmail.getEmailId());
		if (!genericRepository.isUnique(SystemUser.class, null, criterias)) {
			throw new ValidationException(
					FieldErrorUtil.generateFieldErrors("SystemUser", "emailId", "Email already registered"));
		}
	}

	@Override
	public ValueResponse getUserNameByGUID(String GUID) {
		ValueResponse valueResponse = new ValueResponse();
		if (StringUtils.isBlank(GUID)) {
			return valueResponse;
		}
		SystemUser systemUser = genericRepository.getByGUID(SystemUser.class, GUID);
		valueResponse.setValue(systemUser == null ? "" : systemUser.getName());
		return valueResponse;
	}

}
