/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.entities.Role;
import com.essms.auth.models.list.ListRole;
import com.essms.auth.models.request.CreateRoleRequest;
import com.essms.auth.models.request.UpdateRoleRequest;
import com.essms.core.exception.ValidationException;
import com.essms.core.util.FieldErrorUtil;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.utils.TableColumnUtil;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/role")
public class RoleController extends CRUDController<Role, CreateRoleRequest, UpdateRoleRequest, ListRole, SearchEntity> {

	@Value("${" + RolePropertyConstant.SUPER_ADMIN_ROLE + "}")
	private String superAdminRoleGUID;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controllers.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new HashMap<>();
		headerLabelAndFieldPropertyMap.put("name", new FieldHeaderLabelAndEntityProperty("Name", "name"));
		headerLabelAndFieldPropertyMap.put("permission",
				new FieldHeaderLabelAndEntityProperty("Permission", "permission"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.hibernate.core.controllers.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListRole> setupList(List<Role> entities) {
		List<ListRole> listRoles = new ArrayList<>();
		ListRole listRole = null;
		for (Role entity : entities) {
			if (!entity.getIsSelectable() && !UserInfoUtil.getUserRoleGUIDs().contains(superAdminRoleGUID)) {
				continue;
			}
			listRole = new ListRole();
			listRole.setId(entity.getId());
			listRole.setGuid(entity.getGuid());
			listRole.setName(entity.getName());
			listRole.setIsActive(entity.getIsActive());
			listRole.setIsSelectable(entity.getIsSelectable());
			listRole.setIsSystemDefined(entity.getCreatedBy().equalsIgnoreCase("SYSTEMDEFINED")
					|| entity.getLastModifiedBy().equalsIgnoreCase("SYSTEMDEFINED") ? true : false);
			listRole.setCreatedBy(entity.getCreatedBy());
			listRole.setLastModifiedBy(entity.getLastModifiedBy());
			if ((entity.getCreatedBy().equalsIgnoreCase("SYSTEMDEFINED")
					|| entity.getLastModifiedBy().equalsIgnoreCase("SYSTEMDEFINED"))
					&& !UserInfoUtil.getUserRoleGUIDs().contains(superAdminRoleGUID)) {
				listRole.setPermission(TableColumnUtil.generateColumnProperty("View Permission", FormEditorType.Anchor,
						"permission", "/#/admin/tree/20714?roleGUID=" + listRole.getGuid() + "&viewonly=true"));
			} else {
				listRole.setPermission(TableColumnUtil.generateColumnProperty("Manage Permission",
						FormEditorType.Anchor, "permission", "/#/admin/tree/20714?roleGUID=" + listRole.getGuid()));
			}
			listRoles.add(listRole);
		}
		return listRoles;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controllers.CRUDController#setupCreate(com.ssms.
	 * hibernate.core.models.CreateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Role setupCreate(CreateRoleRequest createRequest, Role entity) {
		entity.setName(createRequest.getName());
		if (createRequest.getIsSelectable() != null) {
			entity.setIsSelectable(createRequest.getIsSelectable());
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.hibernate.core.controllers.CRUDController#setupUpdate(com.ssms.
	 * hibernate.core.models.UpdateEntity, com.ssms.hibernate.core.models.BaseModel)
	 */
	@Override
	public Role setupUpdate(UpdateRoleRequest updateRequest, Role entity) {
		entity.setName(updateRequest.getName());
		if (updateRequest.getIsSelectable() != null) {
			entity.setIsSelectable(updateRequest.getIsSelectable());
		}
		return entity;
	}

	@Override
	protected void validateCreateRequest(CreateRoleRequest createRequest, Role entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", createRequest.getName());
		if (!genericRepository.isUnique(Role.class, properties)) {
			throw new ValidationException(
					FieldErrorUtil.generateFieldErrors("Role", "name", "Role with this name already exists"));
		}
	}

	@Override
	protected void validateUpdateRequest(UpdateRoleRequest updateRequest, Role entity) throws ValidationException {
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", updateRequest.getName());
		if (!genericRepository.isUnique(Role.class, entity.getId(), properties)) {
			throw new ValidationException(
					FieldErrorUtil.generateFieldErrors("Role", "name", "Role with this name already exists"));
		}
	}

	@RequestMapping(value = "/withpermission", method = RequestMethod.GET)
	public List<Role> roleWithPermission() {
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("isDeleted", Boolean.FALSE);
		criterias.put("isActive", Boolean.TRUE);
		if (!UserInfoUtil.getUserRoleGUIDs().contains(superAdminRoleGUID)) {
			criterias.put("isSelectable", Boolean.TRUE);
		}
		return genericRepository.findByCriteria(Role.class, criterias);
	}

}
