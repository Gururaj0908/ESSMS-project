/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.entities.TenantConfiguration;
import com.essms.admin.models.request.CreateTenantConfigurationRequest;
import com.essms.admin.models.request.UpdateTenantConfigurationRequest;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;
import com.essms.hibernate.core.utils.SearchFormGeneratorUtil;

/**
 * @author gaurav
 *
 */

@RestController
@RequestMapping("/tenant/configuration")
public class TenantConfigurationController extends
		CRUDController<TenantConfiguration, CreateTenantConfigurationRequest, UpdateTenantConfigurationRequest, TenantConfiguration, SearchEntity> {

	@Autowired
	private SearchFormGeneratorUtil searchFormGeneratorUtil;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.hibernate.core.controller.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new HashMap<>();
		headerLabelAndFieldPropertyMap.put("tenantId", new FieldHeaderLabelAndEntityProperty("Tenant Id", "tenantId"));
		headerLabelAndFieldPropertyMap.put("mboProjectId",
				new FieldHeaderLabelAndEntityProperty("MBO Project Id", "mboProjectId"));
		headerLabelAndFieldPropertyMap.put("uiBaseUrl",
				new FieldHeaderLabelAndEntityProperty("UI Base URL", "uiBaseUrl"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.hibernate.core.controller.CRUDController#
	 * generateListResponseWithForms(java.util.List, java.lang.Long)
	 */
	@Override
	public ListWithCountAndFormsResponse generateListResponseWithForms(List<TenantConfiguration> entityList,
			Long recordCount) throws InstantiationException, ClassNotFoundException {
		ListWithCountAndFormsResponse listWithCountAndFormsResponse = new ListWithCountAndFormsResponse();
		listWithCountAndFormsResponse.setDisplayableColumnsWithHeader(getDisplayableColumnsWithHeaderMap());
		listWithCountAndFormsResponse.setEntityList(entityList);
		listWithCountAndFormsResponse.setRecordCount(recordCount);
		listWithCountAndFormsResponse.setTableButtons(tableButtons());
		listWithCountAndFormsResponse.setHideActiveToggle(true);
		try {
			listWithCountAndFormsResponse.setFormList(generateEditorFormList(CreateTenantConfigurationRequest.class));
			FormList searchFormList = searchFormGeneratorUtil.generateFormList(SearchEntity.class, null,
					FormDisplayMode.DistributeEvenly);
			searchFormList.setUrl("/" + UserInfoUtil.getServiceName() + "/tenant/configuration/list");
			listWithCountAndFormsResponse.setSearchFormList(searchFormList);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | NoSuchFieldException e) {
			ApplicationLogger.logError("An error occurred while trying to list entity, Cause : " + e.getMessage(), e);
		}
		return listWithCountAndFormsResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<TenantConfiguration> setupList(List<TenantConfiguration> entities) {
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupCreate(com.essms.
	 * hibernate.core.models.CreateEntity,
	 * com.essms.hibernate.core.models.BaseModel)
	 */
	@Override
	public TenantConfiguration setupCreate(CreateTenantConfigurationRequest createRequest, TenantConfiguration entity) {
		entity.setMboProjectId(createRequest.getMboProjectId());
		entity.setTenantId(createRequest.getTenantId());
		entity.setUiBaseUrl(createRequest.getUiBaseUrl());
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupUpdate(com.essms.
	 * hibernate.core.models.UpdateEntity,
	 * com.essms.hibernate.core.models.BaseModel)
	 */
	@Override
	public TenantConfiguration setupUpdate(UpdateTenantConfigurationRequest updateRequest, TenantConfiguration entity) {
		entity.setMboProjectId(updateRequest.getMboProjectId());
		entity.setTenantId(updateRequest.getTenantId());
		entity.setUiBaseUrl(updateRequest.getUiBaseUrl());
		return entity;
	}

}
