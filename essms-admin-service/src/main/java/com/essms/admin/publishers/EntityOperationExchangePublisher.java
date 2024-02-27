/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.publishers;

import java.util.Set;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.queue.EntityOperationModel;

/**
 * @author gaurav
 *
 */
@Component
public class EntityOperationExchangePublisher {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${operation.rabbitmq.exchange.entity}")
	private String entityOperationExchange;

	public void produceEntityOperation(Set<?> ids, EntityOperationType entityOperationType, Class<?> modelClass)
			throws AmqpException {
		EntityOperationModel entityOperationModel = new EntityOperationModel();
		entityOperationModel.setEntityModelClass(modelClass);
		entityOperationModel.setEntityOperationType(entityOperationType);
		entityOperationModel.setTenantId(UserInfoUtil.getTenantId());
		entityOperationModel.setIds(ids);
		amqpTemplate.convertAndSend(entityOperationExchange, "", entityOperationModel);
	}
	public void produceEntityOperation(EntityOperationModel entityOperationModel) {
		amqpTemplate.convertAndSend(entityOperationExchange, "", entityOperationModel);
	}
}
