/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaurav
 *
 */
@Configuration
public class RabbitMQConfig {

	@Autowired
	private ConnectionFactory rabbitConnectionFactory;

	@Value("${location.rabbitmq.exchange.state}")
	private String stateExchange;

	@Value("${location.rabbitmq.exchange.city}")
	private String cityExchange;

	@Value("${location.rabbitmq.exchange.area}")
	private String areaExchange;

	@Value("${location.rabbitmq.exchange.branch}")
	private String branchExchange;

	@Value("${admin.rabbitmq.exchange.endcustomer}")
	private String endCustomerExchange;

	@Value("${admin.rabbitmq.queue.endcustomer}")
	private String endCustomerQueue;

	@Value("${admin.rabbitmq.dlx.queue.endcustomer}")
	private String endCustomerDLXQueue;

	@Value("${admin.rabbitmq.exchange.systemevent}")
	private String systemEventExchange;

	@Value("${admin.rabbitmq.queue.systemevent}")
	private String systemEventQueue;

	@Value("${admin.rabbitmq.dlx.queue.systemevent}")
	private String systemEventDLXQueue;

	@Value("${operation.rabbitmq.exchange.entity}")
	private String entityOperationExchange;

	@Bean
	FanoutExchange stateFanoutExchange() {
		return new FanoutExchange(stateExchange, true, false);
	}

	@Bean
	FanoutExchange cityFanoutExchange() {
		return new FanoutExchange(cityExchange, true, false);
	}

	@Bean
	FanoutExchange areaFanoutExchange() {
		return new FanoutExchange(areaExchange, true, false);
	}

	@Bean
	FanoutExchange branchFanoutExchange() {
		return new FanoutExchange(branchExchange, true, false);
	}

	@Bean
	public Queue endCustomerQueue() {
		return QueueBuilder.durable(endCustomerQueue).withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", endCustomerDLXQueue).build();
	}

	@Bean
	Queue endCustomerDLXQueue() {
		return QueueBuilder.durable(endCustomerDLXQueue).build();
	}

	@Bean
	FanoutExchange endCustomerFanoutExchange() {
		return new FanoutExchange(endCustomerExchange, true, false);
	}

	@Bean
	Binding endCustomerExchangeBinding(FanoutExchange endCustomerFanoutExchange, Queue endCustomerQueue) {
		return BindingBuilder.bind(endCustomerQueue).to(endCustomerFanoutExchange());
	}

	@Bean
	public Queue systemEventQueue() {
		return QueueBuilder.durable(systemEventQueue).withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", systemEventDLXQueue).build();
	}

	@Bean
	Queue systemEventDLXQueue() {
		return QueueBuilder.durable(systemEventDLXQueue).build();
	}

	@Bean
	FanoutExchange systemEventFanoutExchange() {
		return new FanoutExchange(systemEventExchange, true, false);
	}

	@Bean
	Binding systemEventExchangeBinding(FanoutExchange systemEventFanoutExchange, Queue systemEventQueue) {
		return BindingBuilder.bind(systemEventQueue).to(systemEventFanoutExchange());
	}

	@Bean
	FanoutExchange entityOperationFanoutExchange() {
		return new FanoutExchange(entityOperationExchange, true, false);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate stateRabbitTemplate() {
		RabbitTemplate r = new RabbitTemplate(rabbitConnectionFactory);
		r.setMessageConverter(jsonMessageConverter());
		r.setConnectionFactory(rabbitConnectionFactory);
		return r;
	}

}
