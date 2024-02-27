/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.queue.EntityOperationModel;

/**
 * @author gaurav
 *
 */
@Component
public class ExchangePublisherUtil {

	@Autowired
	private EntityOperationExchangePublisher entityOperationPublisher;

	public void produceEntityOperation(Set<?> ids, EntityOperationType entityOperationType, Class<?> modelClass) {
		try {
			EntityOperationModel entityOperationModel = new EntityOperationModel();
			entityOperationModel.setEntityModelClass(modelClass);
			entityOperationModel.setEntityOperationType(entityOperationType);
			entityOperationModel.setTenantId(UserInfoUtil.getTenantId());
			entityOperationModel.setIds(ids);
			entityOperationPublisher.produceEntityOperation(entityOperationModel);
		} catch (Exception e) {
			ApplicationLogger.logError("An error occurred while publishing entity operation in state, Cause : ", e);
		}
	}
}
