/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.entities.SystemEvent;
import com.essms.admin.models.request.CreateSystemEventRequest;
import com.essms.admin.models.request.UpdateSystemEventRequest;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.SearchEntity;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/systemevent/")
public class SystemEventController extends
		CRUDController<SystemEvent, CreateSystemEventRequest, UpdateSystemEventRequest, SystemEvent, SearchEntity> {

	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("menuCategory",
				new FieldHeaderLabelAndEntityProperty("MenuCategory", "menuCategory"));
		headerLabelAndFieldPropertyMap.put("systemEventType",
				new FieldHeaderLabelAndEntityProperty("SystemEventType", "systemEventType"));
		headerLabelAndFieldPropertyMap.put("routeType",
				new FieldHeaderLabelAndEntityProperty("RouteType", "routeType"));
		headerLabelAndFieldPropertyMap.put("displayOrder",
				new FieldHeaderLabelAndEntityProperty("Display Order", "displayOrder"));
		headerLabelAndFieldPropertyMap.put("businessObjectId",
				new FieldHeaderLabelAndEntityProperty("Busine Object Id", "businessObjectId"));
		headerLabelAndFieldPropertyMap.put("menuAction",
				new FieldHeaderLabelAndEntityProperty("Menu Action", "menuAction"));
		headerLabelAndFieldPropertyMap.put("detailUrl",
				new FieldHeaderLabelAndEntityProperty("Detail Url", "detailUrl"));
		return headerLabelAndFieldPropertyMap;
	}

	@Override
	public List<SystemEvent> setupList(List<SystemEvent> entities) {
		return entities;
	}

	@Override
	public SystemEvent setupCreate(CreateSystemEventRequest createRequest, SystemEvent entity) {
		entity.setMenuCategory(createRequest.getMenuCategory());
		entity.setDisplayOrder(createRequest.getDisplayOrder());
		entity.setRouteType(createRequest.getRouteType());
		entity.setSystemEventType(createRequest.getSystemEventType());
		entity.setBusinessObjectId(createRequest.getBusinessObjectId());
		entity.setMenuAction(createRequest.getMenuAction());
		entity.setDetailUrl(createRequest.getDetailUrl());
		return entity;
	}

	@Override
	public SystemEvent setupUpdate(UpdateSystemEventRequest updateRequest, SystemEvent entity) {
		entity.setMenuCategory(updateRequest.getMenuCategory());
		entity.setDisplayOrder(updateRequest.getDisplayOrder());
		entity.setRouteType(updateRequest.getRouteType());
		entity.setSystemEventType(updateRequest.getSystemEventType());
		entity.setBusinessObjectId(updateRequest.getBusinessObjectId());
		entity.setMenuAction(updateRequest.getMenuAction());
		entity.setDetailUrl(updateRequest.getDetailUrl());
		return entity;
	}

}
