/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.essms.auth.entities.BusinessObject;

/**
 * @author gaurav
 *
 */
public class BusinessObjectTreeHelper {

	public static BusinessObjectTree getMenuTree(List<BusinessObject> businessObjects) {
		BusinessObjectTree projectItemTree = new BusinessObjectTree();
		BusinessObjectModel menuItemNode = null;
		Map<String, BusinessObjectModel> parentNodes = new HashMap<>();
		try {
			int count = 0;
			for (BusinessObject businessObject : businessObjects) {
				if (count == 0) {
					menuItemNode = convert(businessObject, null);
					projectItemTree.setRootElement(menuItemNode);
				} else {
					menuItemNode = convert(businessObject, null);
				}
				long menuId = businessObject.getId();
				parentNodes.put("" + menuId, menuItemNode);
				if (count != 0) {
					BusinessObjectModel parentNode = parentNodes.get("" + businessObject.getParent().getId());
					if (parentNode != null) {
						parentNode.addChild(menuItemNode);
					}
				}
				count++;
			}
			parentNodes.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectItemTree;
	}

	public static BusinessObjectTree getMenuTree(List<BusinessObject> businessObjects, List<Long> selectedIds) {
		BusinessObjectTree projectItemTree = new BusinessObjectTree();
		BusinessObjectModel menuItemNode = null;
		Map<String, BusinessObjectModel> parentNodes = new HashMap<>();
		try {
			int count = 0;
			for (BusinessObject businessObject : businessObjects) {
				if (count == 0) {
					menuItemNode = convert(businessObject, selectedIds);
					projectItemTree.setRootElement(menuItemNode);
				} else {
					menuItemNode = convert(businessObject, selectedIds);
				}
				long menuId = businessObject.getId();
				parentNodes.put("" + menuId, menuItemNode);
				if (count != 0) {
					BusinessObjectModel parentNode = parentNodes.get("" + businessObject.getParent().getId());
					if (parentNode != null) {
						parentNode.addChild(menuItemNode);
					}
				}
				count++;
			}
			parentNodes.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectItemTree;
	}

	private static BusinessObjectModel convert(BusinessObject businessObject, List<Long> selectedIds) {
		BusinessObjectModel businessObjectModel = new BusinessObjectModel();
		businessObjectModel.setId(businessObject.getId());
		businessObjectModel.setAction(businessObject.getAction());
		businessObjectModel.setDisplayOrder(businessObject.getDisplayOrder());
		businessObjectModel.setDisplayTag(businessObject.getDisplayTag());
		businessObjectModel.setIcon(businessObject.getIcon());
		businessObjectModel.setIsHidden(businessObject.getIsHidden());
		businessObjectModel.setObjectLevel(businessObject.getObjectLevel());
		businessObjectModel.setObjectName(businessObject.getObjectName());
		businessObjectModel.setRouteType(businessObject.getRouteType());
		businessObjectModel.setUrl(businessObject.getUrl());
		businessObjectModel.setSelected(false);
		if (selectedIds != null && selectedIds.size() > 0) {
			if (selectedIds.contains(businessObject.getId())) {
				businessObjectModel.setSelected(true);
			}
		}
		businessObjectModel.setParentId(businessObject.getParent() == null ? null : businessObject.getParent().getId());
		return businessObjectModel;
	}
}
