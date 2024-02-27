/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.mbo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.essms.auth.entities.BusinessObject;
import com.essms.auth.enums.ProjectStage;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Component
public class ProjectTreeHelper {

	@Autowired
	private GenericRepository genericRepository;

	public ProjectItemTree getProjectItemTree(List<BusinessObject> businessObjects, String title,
			String adminUserGUID) {
		ProjectItemTree projectItemTree = new ProjectItemTree();
		ProjectItem menuItemNode = null;
		Map<String, ProjectItem> parentNodes = new HashMap<String, ProjectItem>();
		try {
			int count = 0;
			for (BusinessObject businessObject : businessObjects) {
				// Skip menu items greater than level 4
				if (businessObject == null || businessObject.getObjectLevel() > 4)
					continue;
				if (StringUtils.isBlank(businessObject.getGuid())) {
					businessObject.setGuid(UUID.randomUUID().toString());
					genericRepository.saveOrUpdate(businessObject);
				}
				if (count == 0) {
					businessObject.setDisplayTag(title);
					menuItemNode = convert(businessObject, adminUserGUID);
					projectItemTree.setRootElement(menuItemNode);
				} else {
					menuItemNode = convert(businessObject, adminUserGUID);
				}
				long menuId = businessObject.getId();
				parentNodes.put("" + menuId, menuItemNode);
				if (count != 0) {
					ProjectItem parentNode = parentNodes.get("" + businessObject.getParent().getId());
					if (parentNode != null)
						parentNode.addChild(menuItemNode);
				}
				count++;
			}
			parentNodes.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectItemTree;
	}

	private static ProjectItem convert(BusinessObject businessObject, String adminUserGUID) {
		ProjectItem projectItem = new ProjectItem();
		projectItem.setId(businessObject.getGuid());
		projectItem.setTitle(businessObject.getDisplayTag());
		projectItem.setDescription(businessObject.getDisplayTag());
		projectItem.setStage(getStage(businessObject.getObjectLevel()));
		projectItem.setIndex(businessObject.getDisplayOrder());
		projectItem.setParentId(businessObject.getParent() == null ? null : businessObject.getParent().getGuid());
		AccessControl accessControl = new AccessControl();
		accessControl.setProjectItemId(businessObject.getGuid());
		accessControl.setUserId(adminUserGUID);
		accessControl.setCanView(true);
		projectItem.getAccessControl().add(accessControl);
		return projectItem;
	}

	private static ProjectStage getStage(Integer level) {
		switch (level) {
		case 1:
			return ProjectStage.Project;
		case 2:
			return ProjectStage.Module;
		case 3:
			return ProjectStage.SubModule;
		case 4:
			return ProjectStage.Milestone;
		default:
			return null;
		}
	}
}
