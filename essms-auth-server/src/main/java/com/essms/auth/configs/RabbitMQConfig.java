/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaurav
 *
 */
@RefreshScope
@Configuration
public class RabbitMQConfig {

	@Value("${auth.rabbitmq.exchange.user}")
	private String userExchange;

	@Value("${auth.rabbitmq.queue.user}")
	private String userQueue;

	@Value("${auth.rabbitmq.dlx.queue.user}")
	private String userDLXQueue;

	@Value("${operation.rabbitmq.exchange.entity}")
	private String entityOperationExchange;

	@Value("${operation.rabbitmq.queue.entity}")
	private String entityOperationQueue;

	@Value("${operation.rabbitmq.dlx.queue.entity}")
	private String entityOperationDLXQueue;

	@Value("${location.rabbitmq.exchange.branch}")
	private String branchExchange;

	@Value("${location.rabbitmq.queue.branch}")
	private String branchQueue;

	@Value("${location.rabbitmq.dlx.queue.branch}")
	private String branchDLXQueue;

	@Bean
	public Queue userQueue() {
		return QueueBuilder.durable(userQueue).withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", userDLXQueue).build();
	}

	@Bean
	Queue userDLXQueue() {
		return QueueBuilder.durable(userDLXQueue).build();
	}

	@Bean
	public Queue entityOperationQueue() {
		return QueueBuilder.durable(entityOperationQueue).withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", entityOperationDLXQueue).build();
	}

	@Bean
	Queue entityOperationDLXQueue() {
		return QueueBuilder.durable(entityOperationDLXQueue).build();
	}

	@Bean
	public Queue branchQueue() {
		return QueueBuilder.durable(branchQueue).withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", branchDLXQueue).build();
	}

	@Bean
	Queue branchDLXQueue() {
		return QueueBuilder.durable(branchDLXQueue).build();
	}

	@Bean
	FanoutExchange userFanoutExchange() {
		return new FanoutExchange(userExchange, true, false);
	}

	@Bean
	FanoutExchange entityOperationFanoutExchange() {
		return new FanoutExchange(entityOperationExchange, true, false);
	}

	@Bean
	FanoutExchange branchFanoutExchange() {
		return new FanoutExchange(branchExchange, true, false);
	}

	@Bean
	Binding userExchangeBinding(FanoutExchange userFanoutExchange, Queue userQueue) {
		return BindingBuilder.bind(userQueue).to(userFanoutExchange());
	}

	@Bean
	Binding entityOperationExchangeBinding(FanoutExchange entityOperationFanoutExchange, Queue entityOperationQueue) {
		return BindingBuilder.bind(entityOperationQueue).to(entityOperationFanoutExchange());
	}

	@Bean
	Binding branchExchangeBinding(FanoutExchange branchFanoutExchange, Queue branchQueue) {
		return BindingBuilder.bind(branchQueue).to(branchFanoutExchange());
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitContainerFactory(
			ConnectionFactory rabbitConnectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setMessageConverter(jsonMessageConverter());
		factory.setConnectionFactory(rabbitConnectionFactory);
		return factory;
	}

}
