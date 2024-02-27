/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.publishers;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.essms.core.enums.UserType;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.queue.SystemUserModel;

/**
 * @author gaurav
 *
 */
@Component
public class UserExchangePublisher {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${auth.rabbitmq.exchange.user}")
	private String userExchange;

	public void produceUser(RegisterUserModel registerUserModel, String branchGUID, String createdBy, String updatedBy,
			Date createdDate, Date modifiedDate, Boolean isActive, String guid, UserType userType, String tenantId,
			Boolean syncToBMS) throws AmqpException {
		amqpTemplate.convertAndSend(userExchange, "", generateSystemUserModel(registerUserModel, branchGUID, createdBy,
				updatedBy, createdDate, modifiedDate, isActive, guid, userType, tenantId, syncToBMS));
	}

	private SystemUserModel generateSystemUserModel(RegisterUserModel registerUserModel, String branchGUID,
			String createdBy, String updatedBy, Date createdDate, Date modifiedDate, Boolean isActive, String guid,
			UserType userType, String tenantId, Boolean syncToBMS) {
		SystemUserModel systemUserModel = new SystemUserModel();
		if (registerUserModel != null) {
			if (StringUtils.isNotBlank(registerUserModel.getName())) {
				systemUserModel.setName(registerUserModel.getName());
			}
			if (StringUtils.isNotBlank(registerUserModel.getUsername())) {
				systemUserModel.setUsername(registerUserModel.getUsername());
			}
			if (StringUtils.isNotBlank(registerUserModel.getMobileNo())) {
				systemUserModel.setMobileNo(registerUserModel.getMobileNo());
			}
			if (StringUtils.isNotBlank(registerUserModel.getEmailId())) {
				systemUserModel.setEmailId(registerUserModel.getEmailId());
			}
			if (StringUtils.isNotBlank(registerUserModel.getPassword())) {
				systemUserModel.setPassword(registerUserModel.getPassword());
			}
		}
		if (StringUtils.isNotBlank(branchGUID)) {
			systemUserModel.setBranchGUID(branchGUID);
		}
		systemUserModel.setCreatedBy(createdBy);
		systemUserModel.setCreatedDate(createdDate);
		systemUserModel.setEnabled(isActive);
		systemUserModel.setLastModifiedBy(updatedBy);
		systemUserModel.setLastModifiedDate(modifiedDate);
		systemUserModel.setTenantId(tenantId);
		systemUserModel.setUserGUID(guid);
		systemUserModel.setUserType(userType);
		systemUserModel.setSyncToBMS(syncToBMS);
		return systemUserModel;
	}

	public void produceUser(SystemUserModel systemUserModel) {
		amqpTemplate.convertAndSend(userExchange, "", systemUserModel);
	}

}
