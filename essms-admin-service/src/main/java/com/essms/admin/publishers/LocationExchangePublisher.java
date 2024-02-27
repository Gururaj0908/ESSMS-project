/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.publishers;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.essms.admin.entities.Area;
import com.essms.admin.entities.Branch;
import com.essms.admin.entities.City;
import com.essms.admin.entities.State;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.models.queue.AreaModel;
import com.essms.hibernate.core.models.queue.BranchModel;
import com.essms.hibernate.core.models.queue.CityModel;
import com.essms.hibernate.core.models.queue.StateModel;

/**
 * Fanout Exchange publisher for location related entity changes
 *
 * @author gaurav
 *
 */
@Component
public class LocationExchangePublisher {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${location.rabbitmq.exchange.state}")
	private String stateExchange;

	@Value("${location.rabbitmq.exchange.city}")
	private String cityExchange;

	@Value("${location.rabbitmq.exchange.area}")
	private String areaExchange;

	@Value("${location.rabbitmq.exchange.branch}")
	private String branchExchange;

	public void produceState(State entity) throws AmqpException {
		amqpTemplate.convertAndSend(stateExchange, "", generateQueueStateModel(entity));
	}

	private StateModel generateQueueStateModel(State entity) {
		StateModel stateModel = new StateModel();
		stateModel.setId(entity.getId());
		stateModel.setCreatedBy(entity.getCreatedBy());
		stateModel.setCreatedDate(entity.getCreatedDate());
		stateModel.setGuid(entity.getGuid());
		stateModel.setIsActive(entity.getIsActive());
		stateModel.setIsDeleted(entity.getIsDeleted());
		stateModel.setLastModifiedBy(entity.getLastModifiedBy());
		stateModel.setLastModifiedDate(entity.getLastModifiedDate());
		stateModel.setCode(entity.getCode());
		stateModel.setTin(entity.getTin());
		stateModel.setName(entity.getName());
		stateModel.setTenantId(UserInfoUtil.getTenantId());
		return stateModel;
	}

	public void produceCity(City entity) throws AmqpException {
		amqpTemplate.convertAndSend(cityExchange, "", generateQueueCityModel(entity));
	}

	private CityModel generateQueueCityModel(City entity) {
		CityModel cityModel = new CityModel();
		cityModel.setId(entity.getId());
		cityModel.setStateId(entity.getState().getId());
		cityModel.setCreatedBy(entity.getCreatedBy());
		cityModel.setCreatedDate(entity.getCreatedDate());
		cityModel.setGuid(entity.getGuid());
		cityModel.setIsActive(entity.getIsActive());
		cityModel.setIsDeleted(entity.getIsDeleted());
		cityModel.setLastModifiedBy(entity.getLastModifiedBy());
		cityModel.setLastModifiedDate(entity.getLastModifiedDate());
		cityModel.setName(entity.getName());
		cityModel.setTenantId(UserInfoUtil.getTenantId());
		return cityModel;
	}

	public void produceArea(Area entity) throws AmqpException {
		amqpTemplate.convertAndSend(areaExchange, "", generateQueueAreaModel(entity));
	}

	private AreaModel generateQueueAreaModel(Area entity) {
		AreaModel areaModel = new AreaModel();
		areaModel.setId(entity.getId());
		areaModel.setCityId(entity.getCity().getId());
		areaModel.setCreatedBy(entity.getCreatedBy());
		areaModel.setCreatedDate(entity.getCreatedDate());
		areaModel.setGuid(entity.getGuid());
		areaModel.setIsActive(entity.getIsActive());
		areaModel.setIsDeleted(entity.getIsDeleted());
		areaModel.setLastModifiedBy(entity.getLastModifiedBy());
		areaModel.setLastModifiedDate(entity.getLastModifiedDate());
		areaModel.setName(entity.getName());
		areaModel.setTenantId(UserInfoUtil.getTenantId());
		return areaModel;
	}

	public void produceBranch(Branch entity) throws AmqpException {
		amqpTemplate.convertAndSend(branchExchange, "", generateQueueBranchModel(entity));
	}

	private BranchModel generateQueueBranchModel(Branch entity) {
		BranchModel branchModel = new BranchModel();
		branchModel.setId(entity.getId());
		branchModel.setCode(entity.getCode());
		branchModel.setName(entity.getName());
		branchModel.setEmailId(entity.getEmailId());
		branchModel.setGstinNumber(entity.getGstinNumber());
		branchModel.setIsServiceCenter(entity.getIsServiceCenter());
		branchModel.setOfficeType(entity.getOfficeType());
		branchModel.setCreatedBy(entity.getCreatedBy());
		branchModel.setCreatedDate(entity.getCreatedDate());
		branchModel.setGuid(entity.getGuid());
		branchModel.setIsActive(entity.getIsActive());
		branchModel.setIsDeleted(entity.getIsDeleted());
		branchModel.setLastModifiedBy(entity.getLastModifiedBy());
		branchModel.setLastModifiedDate(entity.getLastModifiedDate());
		branchModel.setTenantId(UserInfoUtil.getTenantId());
		return branchModel;
	}
}
