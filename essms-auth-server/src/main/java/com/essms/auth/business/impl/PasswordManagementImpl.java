/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.essms.auth.business.PasswordManagement;
import com.essms.auth.entities.EmailReset;
import com.essms.auth.entities.SystemUser;
import com.essms.auth.models.request.ChangePasswordRequest;
import com.essms.auth.models.request.ForceResetPasswordRequest;
import com.essms.auth.models.request.ForgotPasswordRequest;
import com.essms.auth.models.request.ResetLinkPasswordRequest;
import com.essms.auth.models.request.ResetOTPPasswordRequest;
import com.essms.auth.publishers.UserExchangePublisher;
import com.essms.core.enums.NotificationType;
import com.essms.core.enums.UserType;
import com.essms.core.exception.ValidationException;
import com.essms.core.util.DateUtil;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.RandomCodeUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.business.RemoteNotificationManagement;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.NotificationRecipient;
import com.essms.hibernate.core.models.queue.SystemUserModel;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.core.utils.FormGeneratorUtil;
import com.essms.hibernate.core.utils.NotificationRequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
@Service
public class PasswordManagementImpl implements PasswordManagement {

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RemoteNotificationManagement remoteNotificationManagement;

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	@Value("${bms.tenant.id}")
	private String bmsTenantId;

	@Value("${notification.event.forgot.password}")
	private String eventForgotPassword;

	@Value("${reset.password.expiry.time}")
	private Integer passwordExpiryTime;

	@Value("${reset.password.ui.url}")
	private String passwordResetURL;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.auth.business.PasswordManagement#resetForm(java.lang.String)
	 */
	@Override
	public FormList resetForm(String userGUID) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		ForceResetPasswordRequest forceResetPasswordRequest = new ForceResetPasswordRequest();
		forceResetPasswordRequest.setUserGUID(userGUID);
		FormList formList = formGeneratorUtil.generateFormList(ForceResetPasswordRequest.class,
				forceResetPasswordRequest, FormDisplayMode.StrictlyVertical);
		formList.setUrl("/essms-auth/password/force/reset");
		return formList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.auth.business.PasswordManagement#resetPassword(com.essms.auth.
	 * models.request.ResetPasswordRequest)
	 */
	@Override
	public void forceReset(ForceResetPasswordRequest resetPasswordRequest) {
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("guid", resetPasswordRequest.getUserGUID());
		final List<SystemUser> systemUsers = genericRepository.findByCriteria(SystemUser.class, criterias);
		publishPwdChange(systemUsers.get(0), resetPasswordRequest.getNewPassword());
		final SystemUser systemUser = systemUsers.get(0);
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("USER", systemUser.getName());
		final List<NotificationRecipient> notificationRecipients = new ArrayList<>();
		notificationRecipients.add(new NotificationRecipient(systemUser.getEmailId(), map));
		List<NotificationType> notificationTypes = new ArrayList<>();
		notificationTypes.add(NotificationType.Email);
		try {
			remoteNotificationManagement.initiate(NotificationRequestUtil.generate(notificationRecipients,
					"Password change", "Your password has been changed successfully", notificationTypes), UserInfoUtil.getTenantId());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.PasswordManagement#changePassword(com.essms.auth.
	 * models.request.ChangePasswordRequest)
	 */
	@Override
	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("guid", changePasswordRequest.getUserGUID());
		final List<SystemUser> systemUsers = genericRepository.findByCriteria(SystemUser.class, criterias);
		final SystemUser systemUser = systemUsers.get(0);
		if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), systemUser.getPassword())) {
			throw new ValidationException(
					FieldErrorUtil.generateFieldErrors("ChangePasswordRequest", "oldPassword", "Wrong old password"));
		}
		publishPwdChange(systemUser, changePasswordRequest.getNewPassword());
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("USER", systemUser.getName());
		final List<NotificationRecipient> notificationRecipients = new ArrayList<>();
		notificationRecipients.add(new NotificationRecipient(systemUser.getEmailId(), map));
		List<NotificationType> notificationTypes = new ArrayList<>();
		notificationTypes.add(NotificationType.Email);
		try {
			remoteNotificationManagement.initiate(NotificationRequestUtil.generate(notificationRecipients,
					"Password change", "Your password has been changed successfully", notificationTypes), UserInfoUtil.getTenantId());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.PasswordManagement#forgotPassword(com.essms.auth.
	 * models.request.ForgotPasswordRequest)
	 */
	@Override
	public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest, Boolean sendOTP)
			throws JsonProcessingException {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("tenantId", UserInfoUtil.getTenantId());
//		final List<TenantConfiguration> tenantConfigurations = genericRepository
//				.findByCriteria(TenantConfiguration.class, criterias);
//		if (tenantConfigurations == null || tenantConfigurations.size() <= 0) {
//			throw new ValidationException(
//					FieldErrorUtil.generateFieldErrors(ForgotPasswordRequest.class.getSimpleName(), "emailId",
//							"Tenant Base URL not configured, please contact site administrator"));
//		}
		criterias = new HashMap<>();
		criterias.put("systemUser.emailId", forgotPasswordRequest.getEmailId());
		criterias.put("enabled", true);
		final List<EmailReset> emailResets = genericRepository.findByCriteria(EmailReset.class, criterias);
		String OTP = String.valueOf(RandomCodeUtil.OTP(6));
		EmailReset emailReset = null;
		SystemUser systemUser = null;
		if (emailResets != null && emailResets.size() > 0) {
			emailReset = emailResets.get(0);
			emailReset.setGuid(UUID.randomUUID().toString());
			emailReset.setOtp(OTP);
			systemUser = emailReset.getSystemUser();
		} else {
			criterias = new HashMap<>();
			criterias.put("emailId", forgotPasswordRequest.getEmailId());
			final List<SystemUser> systemUsers = genericRepository.findByCriteria(SystemUser.class, criterias);
			if (systemUsers != null && systemUsers.size() > 0) {
				systemUser = systemUsers.get(0);
			} else {
				throw new ValidationException(FieldErrorUtil.generateFieldErrors(
						ForgotPasswordRequest.class.getSimpleName(), "emailId", "We could not identify this email"));
			}
			emailReset = new EmailReset();
			emailReset.setGuid(UUID.randomUUID().toString());
			emailReset.setOtp(OTP);
			emailReset.setEmailId(forgotPasswordRequest.getEmailId());
			emailReset.setSystemUser(systemUser);
			emailReset.setExpiryDate(DateTime.now(DateTimeZone.UTC).plusHours(passwordExpiryTime).toDate());
		}
		genericRepository.saveOrUpdate(emailReset);
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("USER", systemUser.getName());
		final List<NotificationRecipient> notificationRecipients = new ArrayList<>();
		notificationRecipients.add(new NotificationRecipient(systemUser.getEmailId(), map));
		if (sendOTP) {
			List<NotificationType> notificationTypes = new ArrayList<>();
			notificationTypes.add(NotificationType.Email);
			remoteNotificationManagement.initiate(NotificationRequestUtil.generate(notificationRecipients,
					"OTP To reset password", OTP, notificationTypes), UserInfoUtil.getTenantId());
		} else {
//			map.put("URL",
//					tenantConfigurations.get(0).getUiBaseUrl() + "/" + passwordResetURL + "/" + emailReset.getGuid());
			remoteNotificationManagement.initiate(
					NotificationRequestUtil.generate(eventForgotPassword, notificationRecipients, map),
					UserInfoUtil.getTenantId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.PasswordManagement#resetPasswordViaLink(com.essms.
	 * auth.models.request.ResetLinkPasswordRequest)
	 */
	@Override
	public void resetPasswordViaLink(ResetLinkPasswordRequest resetLinkPasswordRequest) {
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("guid", resetLinkPasswordRequest);
		final List<EmailReset> emailResets = genericRepository.findByCriteria(EmailReset.class, criterias);
		if (emailResets == null || emailResets.size() <= 0) {
			throw new ValidationException(FieldErrorUtil
					.generateFieldErrors(ResetLinkPasswordRequest.class.getSimpleName(), "resetGUID", "Link Expired"));
		}
		if (DateUtil.isExpired(emailResets.get(0).getExpiryDate())) {
			throw new ValidationException(FieldErrorUtil
					.generateFieldErrors(ResetLinkPasswordRequest.class.getSimpleName(), "resetGUID", "Link Expired"));
		}
		publishPwdChange(emailResets.get(0).getSystemUser(), resetLinkPasswordRequest.getNewPassword());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.auth.business.PasswordManagement#resetPasswordViaOTP(com.essms.auth
	 * .models.request.ResetOTPPasswordRequest)
	 */
	@Override
	public void resetPasswordViaOTP(ResetOTPPasswordRequest resetOTPPasswordRequest) {
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("otp", resetOTPPasswordRequest.getOTP());
		criterias.put("emailId", resetOTPPasswordRequest.getEmailId());
		final List<EmailReset> emailResets = genericRepository.findByCriteria(EmailReset.class, criterias);
		if (emailResets == null || emailResets.size() <= 0) {
			throw new ValidationException(FieldErrorUtil
					.generateFieldErrors(ResetOTPPasswordRequest.class.getSimpleName(), "OTP", "OTP Expired"));
		}
		if (DateUtil.isExpired(emailResets.get(0).getExpiryDate())) {
			throw new ValidationException(FieldErrorUtil
					.generateFieldErrors(ResetOTPPasswordRequest.class.getSimpleName(), "OTP", "OTP Expired"));
		}
		publishPwdChange(emailResets.get(0).getSystemUser(), resetOTPPasswordRequest.getNewPassword());
	}

	private void publishPwdChange(SystemUser systemUser, String newPassword) {
		final SystemUserModel systemUserModel = new SystemUserModel();
		systemUserModel.setPassword(newPassword);
		systemUserModel.setUserGUID(systemUser.getGuid());
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase(bmsTenantId)
				&& systemUser.getUserType().equals(UserType.BACKOFFICE)) {
			systemUserModel.setSyncToBMS(true);
		}
		systemUserModel.setTenantId(UserInfoUtil.getTenantId());
		userExchangePublisher.produceUser(systemUserModel);
	}

}
