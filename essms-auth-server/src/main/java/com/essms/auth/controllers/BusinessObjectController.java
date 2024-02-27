/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.essms.auth.business.BusinessObjectManagement;
import com.essms.auth.entities.BusinessObject;
import com.essms.auth.models.list.ListBusinessObject;
import com.essms.auth.models.menu.BusinessObjectTreeHelper;
import com.essms.auth.models.request.UpsertBusiessObjectRequest;
import com.essms.auth.models.response.MenuTreeResponse;
import com.essms.core.constants.HeaderConstant;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.PaginatedModel;
import com.essms.hibernate.core.models.SearchCriteria;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;
import com.essms.hibernate.core.models.response.ListWithCountResponse;
import com.essms.hibernate.core.utils.FormGeneratorUtil;
import com.essms.hibernate.core.utils.SearchCriteriaUtil;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/businessobject/")
public class BusinessObjectController extends
		CRUDController<BusinessObject, UpsertBusiessObjectRequest, UpsertBusiessObjectRequest, ListBusinessObject, SearchEntity> {

	@Autowired
	private BusinessObjectManagement businessObjectManagement;

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	@Value("${" + RolePropertyConstant.SUPER_ADMIN_ROLE + "}")
	private String superAdminRoleGUID;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		final Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("id", new FieldHeaderLabelAndEntityProperty("Id", "id"));
		headerLabelAndFieldPropertyMap.put("icon", new FieldHeaderLabelAndEntityProperty("Icon", "icon"));
		headerLabelAndFieldPropertyMap.put("routeType",
				new FieldHeaderLabelAndEntityProperty("Route Type", "routeType"));
		headerLabelAndFieldPropertyMap.put("objectName",
				new FieldHeaderLabelAndEntityProperty("Object Name", "objectName"));
		headerLabelAndFieldPropertyMap.put("displayTag",
				new FieldHeaderLabelAndEntityProperty("Display Tag", "displayTag"));
		headerLabelAndFieldPropertyMap.put("menuAction",
				new FieldHeaderLabelAndEntityProperty("Menu Action", "action"));
		headerLabelAndFieldPropertyMap.put("objectLevel",
				new FieldHeaderLabelAndEntityProperty("Object Level", "objectLevel"));
		headerLabelAndFieldPropertyMap.put("displayOrder",
				new FieldHeaderLabelAndEntityProperty("Display Order", "displayOrder"));
		headerLabelAndFieldPropertyMap.put("url", new FieldHeaderLabelAndEntityProperty("URL", "url"));
		headerLabelAndFieldPropertyMap.put("isHidden", new FieldHeaderLabelAndEntityProperty("Is Hidden", "isHidden"));
		headerLabelAndFieldPropertyMap.put("parentId", new FieldHeaderLabelAndEntityProperty("Parent Id", "parentId"));
		if (UserInfoUtil.getUserRoleGUIDs().contains(superAdminRoleGUID)) {
			headerLabelAndFieldPropertyMap.put("isSelectable",
					new FieldHeaderLabelAndEntityProperty("Selectable", "isSelectable"));
		}
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListBusinessObject> setupList(List<BusinessObject> entities) {
		final List<ListBusinessObject> listBusinessObjects = new ArrayList<>();
		ListBusinessObject listBusinessObject = null;
		for (final BusinessObject businessObject : entities) {
			if ((businessObject.getIsSelectable() != null && !businessObject.getIsSelectable())
					&& !UserInfoUtil.getUserRoleGUIDs().contains(superAdminRoleGUID)) {
				continue;
			}
			listBusinessObject = new ListBusinessObject();
			listBusinessObject.setId(businessObject.getId());
			listBusinessObject.setIcon(businessObject.getIcon());
			listBusinessObject.setRouteType(businessObject.getRouteType());
			listBusinessObject.setMenuAction(businessObject.getAction());
			listBusinessObject.setDisplayOrder(businessObject.getDisplayOrder());
			listBusinessObject.setDisplayTag(businessObject.getDisplayTag());
			listBusinessObject.setIsDeleted(businessObject.getIsDeleted());
			listBusinessObject.setIsHidden(businessObject.getIsHidden());
			listBusinessObject.setDisplayOnMobile(businessObject.getDisplayOnMobile());
			listBusinessObject.setObjectLevel(businessObject.getObjectLevel());
			listBusinessObject.setObjectName(businessObject.getObjectName());
			listBusinessObject.setParentId(businessObject.getParentId());
			listBusinessObject.setUrl(businessObject.getUrl());
			listBusinessObject.setIsSelectable(businessObject.getIsSelectable());
			listBusinessObjects.add(listBusinessObject);
		}
		return listBusinessObjects;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#list(java.lang.String,
	 * java.lang.Boolean, java.lang.Integer, java.lang.Integer, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public ListWithCountResponse list(String jsonQueryString, Boolean includeDeActive, Integer pageNumber,
			Integer pageSize, String orderBy, Boolean desc) {
		final ListWithCountResponse listWithCountResponse = new ListWithCountResponse();
		PaginatedModel paginatedModel = null;
		if (pageNumber != null && pageNumber > 0) {
			paginatedModel = new PaginatedModel();
			paginatedModel.setPageNumber(pageNumber);
			if (orderBy != null) {
				paginatedModel.setOrderBy(orderBy);
				paginatedModel.setDesc(desc);
			}
			paginatedModel.setPageSize(pageSize);
		}
		final SearchCriteria searchCriteria = SearchCriteriaUtil.generate(jsonQueryString, paginatedModel,
				getHeaderLabelAndFieldPropertyMap(), BusinessObject.class);
		searchCriteria.getCriterias().put("isDeleted", false);
		listWithCountResponse.setEntityList(
				setupList(genericRepository.findByNamedQuery("findAllActive", new HashMap<String, Object>())));
		listWithCountResponse.setRecordCount(searchCriteria.getRecordCount());
		return listWithCountResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controller.CRUDController#listWithForms(java.lang.
	 * String, java.lang.Boolean, java.lang.Integer, java.lang.Integer,
	 * java.lang.String, java.lang.Boolean)
	 */
	@Override
	public ListWithCountAndFormsResponse listWithForms(String jsonQueryString, Boolean includeDeActive,
			Integer pageNumber, Integer pageSize, String orderBy, Boolean desc)
			throws InstantiationException, ClassNotFoundException {
		final List<ListBusinessObject> entityList = setupList(
				genericRepository.findByNamedQuery("findAllActive", new HashMap<String, Object>()));
		return generateListResponseWithForms(entityList, 0l);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#setupCreate(com.ssms.
	 * hibernate.core.models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public BusinessObject setupCreate(UpsertBusiessObjectRequest upsertRequest, BusinessObject entity) {
		if (entity == null) {
			entity = new BusinessObject();
		}
		entity.setId(upsertRequest.getId());
		entity.setIcon(upsertRequest.getIcon());
		entity.setRouteType(upsertRequest.getRouteType());
		entity.setAction(upsertRequest.getMenuAction());
		entity.setDisplayOrder(upsertRequest.getDisplayOrder());
		entity.setDisplayTag(upsertRequest.getDisplayTag());
		entity.setIsHidden(upsertRequest.getIsHidden());
		entity.setObjectLevel(upsertRequest.getObjectLevel());
		entity.setObjectName(upsertRequest.getObjectName());
		entity.setDisplayOnMobile(upsertRequest.getDisplayOnMobile());
		if (upsertRequest.getParentId() != null && upsertRequest.getParentId() > 0) {
			final BusinessObject businessObject = new BusinessObject();
			businessObject.setId(upsertRequest.getParentId());
			entity.setParent(businessObject);
		} else {
			entity.setParent(null);
		}
		entity.setUrl(upsertRequest.getUrl());
		if (upsertRequest.getIsSelectable() != null) {
			entity.setIsSelectable(upsertRequest.getIsSelectable());
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controller.CRUDController#setupUpdate(com.ssms.
	 * hibernate.core.models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public BusinessObject setupUpdate(UpsertBusiessObjectRequest updateRequest, BusinessObject entity) {
		return setupCreate(updateRequest, entity);
	}

	@RequestMapping(value = "/menutree", method = RequestMethod.GET)
	public MenuTreeResponse getMenuTree() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		final MenuTreeResponse menuTreeResponse = new MenuTreeResponse();
		menuTreeResponse.setBusinessObjectModel(
				BusinessObjectTreeHelper.getMenuTree(businessObjectManagement.getAll()).getRootElement());
		menuTreeResponse.setFormList(formGeneratorUtil.generateFormList(UpsertBusiessObjectRequest.class, null,
				FormDisplayMode.StrictlyHorizontal));
		final Map<String, Object> configuration = new HashMap<>();
		configuration.put("titleKey", "displayTag");
		configuration.put("subtitleKey", "routeType");
		configuration.put("tooltipKey", "url");
		configuration.put("parentIdKey", "parentId");
		configuration.put("selectionAllowed", false);
		menuTreeResponse.setConfiguration(configuration);
		return menuTreeResponse;
	}

	@RequestMapping(value = "/byroleaccess", method = RequestMethod.GET)
	public List<BusinessObject> listByRoleAccess(@RequestParam(name = "roleGUIDs") String[] roleGUIDs) {
		if (RequestContextHolder.getRequestAttributes().getAttribute(HeaderConstant.MOBILE,
				RequestAttributes.SCOPE_REQUEST) != null) {
			return businessObjectManagement.getByRoleAccessForMobile(roleGUIDs);
		}
		return businessObjectManagement.getByRoleAccess(roleGUIDs);
	}

	@RequestMapping(value = "/byuseraccess", method = RequestMethod.GET)
	public List<BusinessObject> listByUserAccess() {
		if (RequestContextHolder.getRequestAttributes().getAttribute(HeaderConstant.MOBILE,
				RequestAttributes.SCOPE_REQUEST) != null) {
			return businessObjectManagement.getByUserAccessForMobile(UserInfoUtil.getUserGUID());
		}
		return businessObjectManagement.getByUserAccess(UserInfoUtil.getUserGUID());
	}

	@RequestMapping(value = "/byuseraccess/{userGUID}/{branchGUID}", method = RequestMethod.GET)
	public List<BusinessObject> listByUserAccessAndBranchGUID(@PathVariable("userGUID") String userGUID,
			@PathVariable("branchGUID") String branchGUID) {
		if (RequestContextHolder.getRequestAttributes().getAttribute(HeaderConstant.MOBILE,
				RequestAttributes.SCOPE_REQUEST) != null) {
			return businessObjectManagement.getByUserNBranchAccessForMobile(branchGUID, userGUID);
		}
		return businessObjectManagement.getByUserNBranchAccess(branchGUID, userGUID);
	}

	@RequestMapping(value = "/byuseraccess/{userGUID}", method = RequestMethod.GET)
	public List<BusinessObject> listByUserAccess(@PathVariable("userGUID") String userGUID) {
		if (RequestContextHolder.getRequestAttributes().getAttribute(HeaderConstant.MOBILE,
				RequestAttributes.SCOPE_REQUEST) != null) {
			return businessObjectManagement.getByUserAccessForMobile(userGUID);
		}
		return businessObjectManagement.getByUserAccess(userGUID);
	}

	@RequestMapping(value = "/byusernbranchaccess/{userGUID}/{branchGUID}", method = RequestMethod.GET)
	public List<BusinessObject> listByUserNBranchAccess(@PathVariable("userGUID") String userGUID,
			@PathVariable("branchGUID") String branchGUID) {
		if (RequestContextHolder.getRequestAttributes().getAttribute(HeaderConstant.MOBILE,
				RequestAttributes.SCOPE_REQUEST) != null) {
			return businessObjectManagement.getByUserNBranchAccessForMobile(branchGUID, userGUID);
		}
		return businessObjectManagement.getByUserNBranchAccess(branchGUID, userGUID);
	}

}
