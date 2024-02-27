/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.publishers;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

	public void produceUser(SystemUserModel systemUserModel) throws AmqpException {
		amqpTemplate.convertAndSend(userExchange, "", systemUserModel);
	}

}
