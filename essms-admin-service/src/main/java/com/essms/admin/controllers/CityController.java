/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.entities.City;
import com.essms.admin.entities.State;
import com.essms.admin.models.list.ListCity;
import com.essms.admin.models.request.CreateCityRequest;
import com.essms.admin.models.request.UpdateCityRequest;
import com.essms.admin.publishers.EntityOperationExchangePublisher;
import com.essms.admin.publishers.LocationExchangePublisher;
import com.essms.core.exception.ValidationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.SearchCriteria;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.queue.CityModel;
import com.essms.hibernate.core.models.response.ListWithCountResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/city")
public class CityController extends CRUDController<City, CreateCityRequest, UpdateCityRequest, ListCity, SearchEntity> {

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
		headerLabelAndFieldPropertyMap.put("name", new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("state", new FieldHeaderLabelAndEntityProperty("State", "state.name"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListCity> setupList(List<City> entities) {
		ListCity listCity = null;
		List<ListCity> cities = new ArrayList<>();
		for (City city : entities) {
			listCity = new ListCity();
			listCity.setId(city.getId());
			listCity.setName(city.getName());
			listCity.setStateId(city.getState().getId());
			listCity.setState(city.getState().getName());
			listCity.setIsActive(city.getIsActive());
			cities.add(listCity);
		}
		return cities;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.request.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public City setupCreate(CreateCityRequest createRequest, City city) {
		city.setName(createRequest.getName());
		State state = genericRepository.getById(State.class, createRequest.getStateId());
		city.setState(state);
		return city;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.ssms.
	 * hibernate.core.models.BaseModel, com.ssms.hibernate.core.models.CreateEntity)
	 */
	@Override
	protected void afterCreate(City entity, CreateCityRequest createRequest) {
		// TODO Remove after bms code separation
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase("bms")) {
			try {
				locationExchangePublisher.produceCity(entity);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing city, Cause : ", e);
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
	public City setupUpdate(UpdateCityRequest updateRequest, City city) {
		if (StringUtils.isNotBlank(updateRequest.getName())) {
			city.setName(updateRequest.getName());
		}
		if (updateRequest.getStateId() != null && updateRequest.getStateId() > 0) {
			State state = genericRepository.getById(State.class, updateRequest.getStateId());
			city.setState(state);
		}
		return city;
	}

	@Override
	protected void validateCreateRequest(CreateCityRequest createRequest, City entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", createRequest.getName());
		properties.put("state.id", createRequest.getStateId());
		if (!genericRepository.isUnique(City.class, properties)) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("City", "name",
					"City with this name already exists within same state"));
		}
	}

	@Override
	protected void validateUpdateRequest(UpdateCityRequest updateRequest, City entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", updateRequest.getName());
		properties.put("state.id", updateRequest.getStateId());
		if (!genericRepository.isUnique(City.class, entity.getId(), properties)) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("City", "name",
					"City with this name already exists within same state"));
		}
	}

	@RequestMapping(value = "/bystate/{stateId}", method = RequestMethod.GET)
	public ListWithCountResponse byState(@PathVariable("stateId") Long stateId) {
		ListWithCountResponse listWithCountResponse = new ListWithCountResponse();
		SearchCriteria searchCriteria = new SearchCriteria();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("state.id", stateId);
		criterias.put("isDeleted", false);
		criterias.put("isActive", true);
		searchCriteria.setCriterias(criterias);
		listWithCountResponse.setEntityList(setupList(genericRepository.search(getEntityClass(), searchCriteria)));
		listWithCountResponse.setRecordCount(searchCriteria.getRecordCount());
		return listWithCountResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterUpdate(com.ssms.
	 * hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterUpdate(City entity, UpdateCityRequest updateRequest) {
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
	protected void afterActivateDeactivate(City entity) {
		afterCreate(entity, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#afterDelete(java.util.Set)
	 */
	@Override
	protected void afterHardDelete(Set<Long> ids) {
		// TODO Remove after bms code separation
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase("bms")) {
			try {
				entityOperationExchangePublisher.produceEntityOperation(ids, EntityOperationType.Deleted,
						CityModel.class);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing city, Cause : ", e);
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
		// TODO Remove after bms code separation
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase("bms")) {
			Map<String, Object> criterias = new HashMap<>();
			if (ids != null && ids.size() > 0) {
				if (ids.size() == 1) {
					criterias.put("id", ids.toArray()[0]);
				} else {
					criterias.put("id", StringUtils.join(ids, ","));
				}
				for (City entity : genericRepository.findByCriteria(City.class, criterias)) {
					afterCreate(entity, null);
				}
			}
		}
	}

}
