package com.essms.auth.models.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.essms.auth.entities.BusinessObject;
import com.essms.hibernate.core.models.BaseModel;

/**
 * Helper class for menu related operations
 * 
 * @author gaurav
 * 
 */
public class MenuHelper {

	public static MenuTree getMenuTree(List<BusinessObject> businessObjects) {

		BaseModelNode rootElement = new BaseModelNode();
		MenuTree menuTree = new MenuTree();
		menuTree.setRootElement(rootElement);
		BaseModelNode menuItemNode = null;
		Map<String, BaseModelNode> parentNodes = new HashMap<String, BaseModelNode>();
		try {
			int count = 0;
			for (BusinessObject businessObject : businessObjects) {
				if (count == 0)
					menuItemNode = rootElement;
				else
					menuItemNode = new BaseModelNode();
				long menuId = businessObject.getId();
				menuItemNode.setData(businessObject);
				parentNodes.put("" + menuId, menuItemNode);
				if (count != 0) {
					BaseModelNode parentNode = parentNodes.get("" + businessObject.getParent().getId());
					if (parentNode != null)
						parentNode.addChild(menuItemNode);
				}
				count++;
			}
			parentNodes.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return menuTree;
	}

	/**
	 * Method to find all the menus at the specified level from the menu tree
	 * 
	 * @param menuTree
	 * @param level
	 * @return
	 */
	public static List<BusinessObject> getMenuForLevel(MenuTree menuTree, int level) {
		List<BusinessObject> menuItemList = new ArrayList<BusinessObject>();
		if (menuTree != null) {
			List<Node<BaseModel>> allMenus = menuTree.toList();
			BusinessObject menuItem = null;
			for (int i = 0; i < allMenus.size(); i++) {
				menuItem = (BusinessObject) allMenus.get(i).getData();
				if (menuItem != null && menuItem.getObjectLevel() == level)
					menuItemList.add(menuItem);
			}
		}
		Collections.sort(menuItemList);
		return menuItemList;
	}

	/**
	 * Method to find all the menus at the specified level from the menu tree
	 * 
	 * @param menuTree
	 * @param level
	 * @return
	 */
	public static List<BusinessObject> getMenuForParent(MenuTree menuTree, long parent) {
		List<BusinessObject> menuItemList = new ArrayList<BusinessObject>();
		List<Node<BaseModel>> allMenus = menuTree.toList();
		BusinessObject menuItem = null;
		for (int i = 1; i < allMenus.size(); i++) {
			menuItem = (BusinessObject) allMenus.get(i).getData();
			if (menuItem != null && menuItem.getParent().getId() == parent) {
				menuItemList.add(menuItem);
			}
		}
		Collections.sort(menuItemList);
		return menuItemList;
	}

	/**
	 * Method to get menuItem for the businessObject
	 * 
	 * @param menuTree
	 * @param level
	 * @return
	 */
	public static BusinessObject getMenuNode(MenuTree menuTree, Long menuObject) {
		List<Node<BaseModel>> allMenus = menuTree.toList();
		BusinessObject menuItem = null;
		for (int i = 0; i < allMenus.size(); i++) {
			menuItem = (BusinessObject) allMenus.get(i).getData();
			if (menuItem != null && menuItem.getId().longValue() == menuObject.longValue()) {
				return menuItem;
			}
		}
		return null;
	}

	/**
	 * Method to check if a particular menu item is seleted or not given the object
	 * id being accessed
	 * 
	 * @param menuTree
	 * @param objId
	 * @return
	 */
	public static boolean isSelected(MenuTree menuTree, int checkObjId, int selectedObjId) {
		List<Node<BaseModel>> allMenus = menuTree.toList();
		BusinessObject menuItem = null;
//		String hierarchyElement = "/" + checkObjId + "/";
		for (int i = 0; i < allMenus.size(); i++) {
			menuItem = (BusinessObject) allMenus.get(i).getData();
			if (menuItem != null && menuItem.getId() == selectedObjId) {
				break;
			}
		}
//		if (menuItem != null && menuItem.getObjectHierarchy().indexOf(hierarchyElement) != -1)
//			return true;
//		else
			return false;
	}
}
