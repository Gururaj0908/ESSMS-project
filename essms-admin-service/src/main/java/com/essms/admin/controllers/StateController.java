/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.entities.State;
import com.essms.admin.models.request.CreateStateRequest;
import com.essms.admin.models.request.UpdateStateRequest;
import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.admin.publishers.LocationExchangePublisher;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.queue.StateModel;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/state")
public class StateController
		extends CRUDController<State, CreateStateRequest, UpdateStateRequest, State, SearchEntity> {

	@Autowired
	private LocationExchangePublisher locationExchangePublisher;

	@Autowired
	private EntityOperationExchangePublisher entityOperationExchangePublisher;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controllers.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("code", new FieldHeaderLabelAndEntityProperty("Code", "code"));
		headerLabelAndFieldPropertyMap.put("tin", new FieldHeaderLabelAndEntityProperty("TIN", "tin"));
		headerLabelAndFieldPropertyMap.put("name", new FieldHeaderLabelAndEntityProperty("Name", "name"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<State> setupList(List<State> entities) {
		return entities;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.request.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public State setupCreate(CreateStateRequest createRequest, State entity) {
		return entity.getModifier().setCode(createRequest.getCode()).setTin(createRequest.getTin())
				.setName(createRequest.getName()).build();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.
	 * ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterCreate(State entity, CreateStateRequest createRequest) {
		// TODO Remove after bms code separation
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase("bms")) {
			try {
				locationExchangePublisher.produceState(entity);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing state, Cause : ", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupUpdate(com.ssms.core.
	 * models.request.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public State setupUpdate(UpdateStateRequest updateRequest, State entity) {
		return entity.getModifier().setCode(updateRequest.getCode()).setTin(updateRequest.getTin())
				.setName(updateRequest.getName()).build();
	}

	@Override
	@Transactional
	protected void validateCreateRequest(CreateStateRequest createRequest, State entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", createRequest.getName());
		if (!genericRepository.isUnique(State.class, properties)) {
			throw new ValidationException(
					FieldErrorUtil.generateFieldErrors("State", "name", "State with this name already exists"));
		}
	}

	@Override
	@Transactional
	protected void validateUpdateRequest(UpdateStateRequest updateRequest, State entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", updateRequest.getName());
		if (!genericRepository.isUnique(State.class, entity.getId(), properties)) {
			throw new ValidationException(
					FieldErrorUtil.generateFieldErrors("State", "name", "State with this name already exists"));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterUpdate(com.ssms.
	 * hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterUpdate(State entity, UpdateStateRequest updateRequest) {
		afterCreate(entity, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterActivateDeactivate(com
	 * .ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterActivateDeactivate(State entity) {
		afterCreate(entity, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#afterHardDelete(java.util.
	 * Set)
	 */
	@Override
	protected void afterHardDelete(Set<Long> ids) {
		// TODO Remove after bms code separation
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase("bms")) {
			try {
				entityOperationExchangePublisher.produceEntityOperation(ids, EntityOperationType.Deleted,
						StateModel.class);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing state, Cause : ", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterRestore(java.util.Set)
	 */
	@Override
	protected void afterRestore(Set<Long> ids) {
		Map<String, Object> criterias = new HashMap<>();
		if (ids != null && ids.size() > 0) {
			if (ids.size() == 1) {
				criterias.put("id", ids.toArray()[0]);
			} else {
				criterias.put("id", StringUtils.join(ids, ","));
			}
			for (State entity : genericRepository.findByCriteria(State.class, criterias)) {
				afterCreate(entity, null);
			}
		}
	}

}
