/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essms.admin.business.HomeManagement;
import com.essms.admin.entities.BranchEvent;
import com.essms.admin.entities.SystemEvent;
import com.essms.admin.models.SystemEventModel;
import com.essms.admin.models.list.ListExecutiveDetail;
import com.essms.admin.models.response.HomePageResponse;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.core.utils.TableColumnUtil;

/**
 * @author gaurav
 *
 */
@Service
public class HomeManagementImpl implements HomeManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Override
	public HomePageResponse home() {
		HomePageResponse homePageResponse = new HomePageResponse();
		Map<String, Map<String, List<SystemEventModel>>> categoryEventMap = new LinkedHashMap<>();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("isDeleted", false);
		criterias.put("isActive", true);
		List<SystemEvent> systemEvents = genericRepository.findByCriteria(SystemEvent.class, criterias, "displayOrder",
				false);
		Map<String, Long> branchEventMap = null;
		SystemEventModel systemEventModel = null;
		List<SystemEventModel> systemEventModels = null;
		Map<String, List<SystemEventModel>> systemEventMap = null;
		for (SystemEvent systemEvent : systemEvents) {
			branchEventMap = new LinkedHashMap<>();
			for (BranchEvent branchEvent : systemEvent.getBranchEvents()) {
				branchEventMap.put(branchEvent.getBranch().getName(), branchEvent.getCount());
			}
			systemEventModel = new SystemEventModel();
			systemEventModel.setBusinessObjectId(systemEvent.getBusinessObjectId());
			systemEventModel.setRouteType(systemEvent.getRouteType());
			systemEventModel.setMenuAction(systemEvent.getMenuAction());
			systemEventModel.setDetailUrl(systemEvent.getDetailUrl());
			systemEventModel.setBranchEventMap(branchEventMap);
			systemEventModel.setTotalCount(systemEvent.getTotalCount());
			if (categoryEventMap.get(systemEvent.getMenuCategory().name()) != null) {
				if (categoryEventMap.get(systemEvent.getMenuCategory().name())
						.get(systemEvent.getSystemEventType().getLabel()) == null) {
					systemEventModels = new LinkedList<>();
					systemEventModels.add(systemEventModel);
					categoryEventMap.get(systemEvent.getMenuCategory().name())
							.put(systemEvent.getSystemEventType().getLabel(), systemEventModels);
				} else {
					categoryEventMap.get(systemEvent.getMenuCategory().name())
							.get(systemEvent.getSystemEventType().getLabel()).add(systemEventModel);
				}
			} else {
				systemEventMap = new LinkedHashMap<>();
				systemEventModels = new LinkedList<>();
				systemEventModels.add(systemEventModel);
				systemEventMap.put(systemEvent.getSystemEventType().getLabel(), systemEventModels);
				categoryEventMap.put(systemEvent.getMenuCategory().name(), systemEventMap);
			}
		}
		homePageResponse.setCategoryEventMap(categoryEventMap);
		homePageResponse.setExtraDetails(extraDetails());
		return homePageResponse;
	}

	private List<ListWithCountAndFormsResponse> extraDetails() {
		List<ListWithCountAndFormsResponse> extraDetails = new ArrayList<>();
		ListWithCountAndFormsResponse listWithCountAndFormsResponse = new ListWithCountAndFormsResponse();
		listWithCountAndFormsResponse.setDisplayableColumnsWithHeader(displayableColumnsWithHeader());
		listWithCountAndFormsResponse.setEntityList(setupList());
		extraDetails.add(listWithCountAndFormsResponse);
		return extraDetails;
	}

	private Map<String, String> displayableColumnsWithHeader() {
		Map<String, String> displayableColumnsWithHeader = new LinkedHashMap<>();
		displayableColumnsWithHeader.put("name", "Field Executive Name");
		displayableColumnsWithHeader.put("dateOfTravel", "Date of Travel");
		displayableColumnsWithHeader.put("map", "Map");
		displayableColumnsWithHeader.put("bill", "Bill");
		return displayableColumnsWithHeader;
	}

	private List<ListExecutiveDetail> setupList() {
		List<ListExecutiveDetail> listExecutiveDetails = new ArrayList<>();
		ListExecutiveDetail listExecutiveDetail = new ListExecutiveDetail();
		listExecutiveDetail.setName("Field Executive");
		listExecutiveDetail.setDateOfTravel("01/01/2018");
		listExecutiveDetail
				.setMap(TableColumnUtil.generateColumnProperty("Map", FormEditorType.Hyperlink, "map", "url"));
		listExecutiveDetail
				.setBill(TableColumnUtil.generateColumnProperty("N/A", FormEditorType.Hyperlink, "bill", "url"));
		listExecutiveDetails.add(listExecutiveDetail);
		return listExecutiveDetails;
	}

}
