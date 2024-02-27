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

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.auth.entities.SystemUser;
import com.essms.auth.entities.UserPermission;
import com.essms.auth.entities.UserRole;
import com.essms.core.enums.UserType;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.models.FormOption;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/option")
public class OptionsController {

	@Autowired
	private GenericRepository genericRepository;

	@Value("${" + RolePropertyConstant.TECHNICIAN_ROLE + "}")
	String technicianRoleGuid;

	@Value("${" + RolePropertyConstant.INVESTOR_ROLE + "}")
	String investorRoleGuid;

	@Value("${" + RolePropertyConstant.INTERNAL_COURIER_ROLE + "}")
	String internalCourierRoleGuid;

	@RequestMapping(value = "/userbyroles", method = RequestMethod.GET)
	public List<FormOption> userByRoles(@RequestParam("roleGUIDs") String[] roleGUIDs) {
		final List<FormOption> options = new ArrayList<>();
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedRoleGUIDs", StringUtils.join(roleGUIDs, ","));
		final List<UserRole> userRoles = genericRepository.findByNamedQuery("findByRoleGUIDs", namedParamers);
		FormOption formOption = null;
		for (final UserRole userRole : userRoles) {
			formOption = new FormOption();
			formOption.setLabel(userRole.getSystemUser().getName());
			formOption.setValue(userRole.getSystemUser().getGuid());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/technician", method = RequestMethod.GET)
	public List<FormOption> technician() {
		final List<FormOption> options = new ArrayList<>();
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedRoleGUIDs", technicianRoleGuid);
		final List<UserRole> userRoles = genericRepository.findByNamedQuery("findByRoleGUIDs", namedParamers);
		FormOption formOption = null;
		for (final UserRole userRole : userRoles) {
			formOption = new FormOption();
			formOption.setLabel(userRole.getSystemUser().getName());
			formOption.setValue(userRole.getSystemUser().getGuid());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/investor", method = RequestMethod.GET)
	public List<FormOption> investor() {
		final List<FormOption> options = new ArrayList<>();
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedRoleGUIDs", investorRoleGuid);
		final List<UserRole> userRoles = genericRepository.findByNamedQuery("findByRoleGUIDs", namedParamers);
		FormOption formOption = null;
		for (final UserRole userRole : userRoles) {
			formOption = new FormOption();
			formOption.setLabel(userRole.getSystemUser().getName());
			formOption.setValue(userRole.getSystemUser().getGuid());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/courier/internal", method = RequestMethod.GET)
	public List<FormOption> courier() {
		final List<FormOption> options = new ArrayList<>();
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedRoleGUIDs", internalCourierRoleGuid);
		final List<UserRole> userRoles = genericRepository.findByNamedQuery("findByRoleGUIDs", namedParamers);
		FormOption formOption = null;
		for (final UserRole userRole : userRoles) {
			formOption = new FormOption();
			formOption.setLabel(userRole.getSystemUser().getName());
			formOption.setValue(userRole.getSystemUser().getGuid());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/courier/internal/withguid", method = RequestMethod.GET)
	public List<FormOption> courierWithGUID() {
		final List<FormOption> options = new ArrayList<>();
		final Map<String, Object> namedParamers = new HashMap<>();
		namedParamers.put("commaSeparatedRoleGUIDs", internalCourierRoleGuid);
		final List<UserRole> userRoles = genericRepository.findByNamedQuery("findByRoleGUIDs", namedParamers);
		FormOption formOption = null;
		for (final UserRole userRole : userRoles) {
			formOption = new FormOption();
			formOption.setLabel(userRole.getSystemUser().getName());
			formOption.setValue(userRole.getSystemUser().getGuid() + "::" + userRole.getSystemUser().getName());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/backofficeusers", method = RequestMethod.GET)
	public List<FormOption> admin() {
		final List<FormOption> options = new ArrayList<>();
		final Map<String, Object> critereas = new HashMap<>();
		critereas.put("userType", UserType.BACKOFFICE);
		final List<SystemUser> users = genericRepository.findByCriteria(SystemUser.class, critereas);
		FormOption formOption = null;
		for (final SystemUser user : users) {
			formOption = new FormOption();
			formOption.setLabel(user.getName());
			formOption.setValue(user.getGuid());
			options.add(formOption);
		}
		return options;
	}

	// TODO Remove this API
	@RequestMapping(value = "/permission", method = RequestMethod.GET)
	@Transactional
	public void permission() {
		List<SystemUser> systemUsers = genericRepository.findAll(SystemUser.class);
		Map<String, Object> criterias = new HashMap<>();
		systemUsers.forEach(e -> {
			criterias.put("userGUID", e.getGuid());
			List<UserPermission> userPermissions = genericRepository.findByCriteria(UserPermission.class, criterias);
			userPermissions.forEach(up -> {
				up.setBranchGUID(e.getBranchGUID());
				genericRepository.saveOrUpdate(up);
			});
		});

	}

}
