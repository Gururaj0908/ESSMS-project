/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.subscribers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.essms.admin.business.EndCustomerManagement;
import com.essms.admin.entities.EndCustomer;
import com.essms.admin.models.AddressModel;
import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.EndCustomerModel;
import com.essms.admin.models.EndCustomerUserModel;
import com.essms.admin.models.request.CreateEndCustomerRequest;
import com.essms.admin.publishers.UserExchangePublisher;
import com.essms.core.enums.UserType;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.queue.EndCustomerRegistration;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.tenant.context.TenantContext;

/**
 * @author gaurav
 *
 */
@Component
public class EndCustomerQueueSubscriber {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private EndCustomerManagement endCustomerManagement;

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	@Value("${admin.rabbitmq.exchange.endcustomer}")
	private String endCustomerExchange;

	@Value("${admin.rabbitmq.dlx.queue.endcustomer}")
	private String endCustomerDLXQueue;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@RabbitListener(queues = "${admin.rabbitmq.queue.endcustomer}")
	public void recievedMessage(EndCustomerRegistration endCustomerRegistration) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		TenantContext.setCurrentTenant(endCustomerRegistration.getTenantId());
		EndCustomer entity = new EndCustomer();
		CreateEndCustomerRequest createRequest = new CreateEndCustomerRequest();
		EndCustomerModel endCustomerModel = new EndCustomerModel();
		EndCustomerUserModel endCustomerUserModel = new EndCustomerUserModel();
		BeanUtils.copyProperties(endCustomerRegistration.getEndCustomerModel(), endCustomerModel);
		BeanUtils.copyProperties(endCustomerRegistration.getRegisterUserModel(), endCustomerUserModel);
		List<EndCustomerAddressModel> endCustomerAddressModels = new ArrayList<>();
		for (com.essms.hibernate.core.models.queue.EndCustomerAddressModel source : endCustomerRegistration
				.getEndCustomerAddressModels()) {
			EndCustomerAddressModel target = new EndCustomerAddressModel();
			AddressModel addressModel = new AddressModel();
			BeanUtils.copyProperties(source, target);
			BeanUtils.copyProperties(source.getAddressModel(), addressModel);
			target.setAddressModel(addressModel);
			endCustomerAddressModels.add(target);
		}
		createRequest.setEndCustomerUserModel(endCustomerUserModel);
		createRequest.setEndCustomerModel(endCustomerModel);
		createRequest.setEndCustomerAddressModels(endCustomerAddressModels);
		endCustomerManagement.createEndCustomer(createRequest, entity, "QUEUE");
		entity.setCustomerType(endCustomerRegistration.getEndCustomerModel().getCustomerType());
		entity.setGuid(endCustomerModel.getGuid());
		entity.setRegNo(endCustomerModel.getRegNo());
		genericRepository.saveOrUpdate(entity);
		RegisterUserModel registerUserModel = new RegisterUserModel();
		BeanUtils.copyProperties(endCustomerRegistration.getRegisterUserModel(), registerUserModel);
		userExchangePublisher.produceUser(registerUserModel, entity.getBranch().getGuid(), entity.getCreatedBy(),
				entity.getLastModifiedBy(), entity.getCreatedDate(), entity.getLastModifiedDate(), true,
				entity.getGuid(), UserType.CUSTOMER, endCustomerRegistration.getTenantId(), false);
		TenantContext.clear();
	}

//	private static final String X_RETRIES_HEADER = "x-retries";
//
//	// TODO Verify retry ...
//	@RabbitListener(queues = "${admin.rabbitmq.dlx.queue.endcustomer}")
//	public void rePublish(Message failedMessage) {
//		Map<String, Object> headers = failedMessage.getMessageProperties().getHeaders();
//		Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);
//		if (retriesHeader == null) {
//			retriesHeader = Integer.valueOf(0);
//		}
//		if (retriesHeader < 3) {
//			headers.put(X_RETRIES_HEADER, retriesHeader + 1);
//			headers.put("x-delay", 50000 * retriesHeader);
//			amqpTemplate.convertAndSend(endCustomerExchange, "", failedMessage);
//		} else {
//			amqpTemplate.send(endCustomerDLXQueue, failedMessage);
//		}
//	}

}
