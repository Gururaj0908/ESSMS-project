/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.subscribers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.essms.auth.business.UserPermissionManagement;
import com.essms.auth.entities.SystemUser;
import com.essms.auth.publishers.UserExchangePublisher;
import com.essms.core.enums.UserType;
import com.essms.hibernate.core.models.queue.SystemUserModel;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.tenant.context.TenantContext;

/**
 * @author gaurav
 *
 */
@Component
public class UserQueueSubscriber {

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	UserPermissionManagement userPermissionManagement;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${bms.tenant.id}")
	private String bmsTenantId;

	@RabbitListener(queues = "${auth.rabbitmq.queue.user}")
	public void receivedMessage(SystemUserModel userModel) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		TenantContext.setCurrentTenant(userModel.getTenantId());
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("guid", userModel.getUserGUID());
		List<SystemUser> systemUsers = genericRepository.findByCriteria(SystemUser.class, criterias);
		SystemUser systemUser = null;
		if (systemUsers != null && !systemUsers.isEmpty()) {
			systemUser = updateUser(systemUsers.get(0), userModel);
		} else {
			// Tenant's back office users are registered into BMS as UserType Customer
			systemUser = enrolNewUser(userModel, userModel.getUserType());
		}
		userPermissionManagement.upsertUserRole(userModel.getRoleGUIDs(), systemUser, userModel.getSyncToBMS(),
				userModel.getTenantId());
		if (userModel.getSyncToBMS() && !userModel.getTenantId().equals(bmsTenantId)) {
			userModel.setTenantId(bmsTenantId);
			userModel.setSyncToBMS(false);
			userExchangePublisher.produceUser(userModel);
		}
		TenantContext.clear();
	}

	private SystemUser updateUser(SystemUser systemUser, SystemUserModel userModel) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (StringUtils.isNotBlank(userModel.getName())) {
			systemUser.setName(userModel.getName());
		}
		if (StringUtils.isNotBlank(userModel.getUsername())) {
			systemUser.setUsername(userModel.getUsername());
		}
		if (StringUtils.isNotBlank(userModel.getMobileNo())) {
			systemUser.setMobileNo(userModel.getMobileNo());
		}
		if (StringUtils.isNotBlank(userModel.getEmailId())) {
			systemUser.setEmailId(userModel.getEmailId());
		}
		if (StringUtils.isNotBlank(userModel.getBranchGUID())) {
			systemUser.setBranchGUID(userModel.getBranchGUID());
		}
		if (userModel.getUserType() != null) {
			systemUser.setUserType(userModel.getUserType());
		}
		if (userModel.getEnabled() != null) {
			systemUser.setEnabled(userModel.getEnabled());
		}
		if (StringUtils.isNotBlank(userModel.getPassword())) {
			systemUser.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
		}
		if (StringUtils.isNotBlank(userModel.getCreatedBy())) {
			systemUser.setCreatedBy(userModel.getCreatedBy());
		}
		if (userModel.getCreatedDate() != null) {
			systemUser.setCreatedDate(userModel.getCreatedDate());
		}
		if (StringUtils.isNotBlank(userModel.getLastModifiedBy())) {
			systemUser.setLastModifiedBy(userModel.getLastModifiedBy());
		}
		if (userModel.getLastModifiedDate() != null) {
			systemUser.setLastModifiedDate(userModel.getLastModifiedDate());
		}
		genericRepository.saveOrUpdate(systemUser);
		return systemUser;
	}

	private SystemUser enrolNewUser(SystemUserModel userModel, UserType userType) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		SystemUser systemUser = new SystemUser();
		systemUser.setName(userModel.getName());
		systemUser.setUsername(userModel.getUsername());
		systemUser.setEmailId(userModel.getEmailId());
		systemUser.setMobileNo(userModel.getMobileNo());
		systemUser.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
		systemUser.setBranchGUID(userModel.getBranchGUID());
		systemUser.setGuid(userModel.getUserGUID());
		systemUser.setUserType(userType);
		systemUser.setCreatedBy(userModel.getCreatedBy());
		systemUser.setCreatedDate(userModel.getCreatedDate());
		systemUser.setLastModifiedBy(userModel.getLastModifiedBy());
		systemUser.setLastModifiedDate(userModel.getLastModifiedDate());
		genericRepository.save(systemUser);
		return systemUser;
	}

}
