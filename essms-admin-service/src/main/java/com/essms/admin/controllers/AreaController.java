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

import com.essms.admin.entities.Area;
import com.essms.admin.entities.City;
import com.essms.admin.models.list.ListArea;
import com.essms.admin.models.request.CreateAreaRequest;
import com.essms.admin.models.request.UpdateAreaRequest;
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
import com.essms.hibernate.core.models.queue.AreaModel;
import com.essms.hibernate.core.models.response.ListWithCountResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/area")
public class AreaController extends CRUDController<Area, CreateAreaRequest, UpdateAreaRequest, ListArea, SearchEntity> {

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
		headerLabelAndFieldPropertyMap.put("city", new FieldHeaderLabelAndEntityProperty("City", "city.name"));
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
	public List<ListArea> setupList(List<Area> entities) {
		ListArea listArea = null;
		List<ListArea> areas = new ArrayList<>();
		for (Area area : entities) {
			listArea = new ListArea();
			listArea.setId(area.getId());
			listArea.setName(area.getName());
			listArea.setCity(area.getCity().getName());
			listArea.setCityId(area.getCity().getId());
			listArea.setState(area.getCity().getState().getName());
			listArea.setStateId(area.getCity().getState().getId());
			listArea.setIsActive(area.getIsActive());
			areas.add(listArea);
		}
		return areas;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.core.
	 * models.request.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Area setupCreate(CreateAreaRequest createRequest, Area area) {
		area.setName(createRequest.getName());
		City city = genericRepository.getById(City.class, createRequest.getCityId());
		area.setCity(city);
		return area;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterCreate(com.
	 * ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterCreate(Area entity, CreateAreaRequest createRequest) {
		// TODO Remove after bms code separation
		if (!UserInfoUtil.getTenantId().equalsIgnoreCase("bms")) {
			try {
				locationExchangePublisher.produceArea(entity);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing area, Cause : ", e);
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
	public Area setupUpdate(UpdateAreaRequest updateRequest, Area area) {
		if (StringUtils.isNotBlank(updateRequest.getName())) {
			area.setName(updateRequest.getName());
		}
		if (updateRequest.getCityId() != null && updateRequest.getCityId() > 0) {
			City city = genericRepository.getById(City.class, updateRequest.getCityId());
			area.setCity(city);
		}
		return area;
	}

	@Override
	protected void validateCreateRequest(CreateAreaRequest createRequest, Area entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", createRequest.getName());
		properties.put("city.id", createRequest.getCityId());
		if (!genericRepository.isUnique(Area.class, properties)) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("Area", "name",
					"Area with this name already exists within same city"));
		}
	}

	@Override
	protected void validateUpdateRequest(UpdateAreaRequest updateRequest, Area entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", updateRequest.getName());
		properties.put("city.id", updateRequest.getCityId());
		if (!genericRepository.isUnique(Area.class, entity.getId(), properties)) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("Area", "name",
					"Area with this name already exists within same city"));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#afterUpdate(com.ssms.
	 * hibernate.core.models.BaseModel)
	 */
	@Override
	protected void afterUpdate(Area entity, UpdateAreaRequest updateRequest) {
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
	protected void afterActivateDeactivate(Area entity) {
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
						AreaModel.class);
			} catch (AmqpException e) {
				ApplicationLogger.logError("An error occurred while publishing area, Cause : ", e);
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
				for (Area entity : genericRepository.findByCriteria(Area.class, criterias)) {
					afterCreate(entity, null);
				}
			}
		}
	}

	@RequestMapping(value = "/bycity/{cityId}", method = RequestMethod.GET)
	public ListWithCountResponse byState(@PathVariable("cityId") Long cityId) {
		ListWithCountResponse listWithCountResponse = new ListWithCountResponse();
		SearchCriteria searchCriteria = new SearchCriteria();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("city.id", cityId);
		criterias.put("isDeleted", false);
		criterias.put("isActive", true);
		searchCriteria.setCriterias(criterias);
		listWithCountResponse.setEntityList(setupList(genericRepository.search(getEntityClass(), searchCriteria)));
		listWithCountResponse.setRecordCount(searchCriteria.getRecordCount());
		return listWithCountResponse;
	}

}
