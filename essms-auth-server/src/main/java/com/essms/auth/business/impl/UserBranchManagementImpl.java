/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essms.auth.business.UserBranchManagement;
import com.essms.auth.entities.Branch;
import com.essms.auth.entities.SystemUser;
import com.essms.auth.entities.UserBranch;
import com.essms.auth.models.request.UpsertUserBranchRequest;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.FormOption;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.core.utils.FormGeneratorUtil;

/**
 * @author gaurav
 *
 */
@Service
public class UserBranchManagementImpl implements UserBranchManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	@Override
	public FormList permissionForm(String userGUID, String branchGUID)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {

		UpsertUserBranchRequest upsertUserBranchRequest = new UpsertUserBranchRequest();
		List<String> userBranches = new ArrayList<>();

		upsertUserBranchRequest.setUserGUID(userGUID);
		upsertUserBranchRequest.setUserBranches(userBranches);
		FormList formList = formGeneratorUtil.generateFormList(UpsertUserBranchRequest.class, upsertUserBranchRequest,
				FormDisplayMode.StrictlyVertical);
		formList.getForms().get(1).setOptions(generateFormOptions(userGUID, branchGUID));
		formList.setUrl("/essms-auth/userbranch/permission/upsert");
		return formList;
	}

	@Override
	@Transactional
	public void upsert(UpsertUserBranchRequest upsertUserBranchRequest) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("systemUser.guid", upsertUserBranchRequest.getUserGUID());
		List<UserBranch> userBranchess = genericRepository.findByCriteria(UserBranch.class, criterias);
		genericRepository.deleteAll(userBranchess);
		UserBranch userBranch = null;
		SystemUser systemUser = genericRepository.getByGUID(SystemUser.class, upsertUserBranchRequest.getUserGUID());
		for (String branchGUID : upsertUserBranchRequest.getUserBranches()) {
			userBranch = new UserBranch();
			userBranch.setSystemUser(systemUser);
			userBranch.setBranch(genericRepository.getByGUID(Branch.class, branchGUID));
			genericRepository.save(userBranch);
		}
	}

	@Override
	public List<FormOption> listing(String branchGUID, String userGUID) {
		return generateFormOptions(userGUID, branchGUID);
	}

	private List<FormOption> generateFormOptions(String userGUID, String branchGUID) {
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("systemUser.guid", userGUID);
		List<UserBranch> userBranchess = genericRepository.findByCriteria(UserBranch.class, criterias);
		criterias.clear();
		criterias.put("guid", "!=" + branchGUID);
		List<Branch> branches = genericRepository.findByCriteria(Branch.class, criterias);
		List<FormOption> formOptions = new ArrayList<>();
		branches.forEach(e -> {
			FormOption formOption = new FormOption();
			formOption.setLabel(e.getName());
			formOption.setValue(e.getGuid());
			if (userBranchess.stream().anyMatch(ub -> e.getGuid().equals(ub.getBranch().getGuid()))) {
				formOption.setSelected(true);
			}
			formOptions.add(formOption);
		});
		return formOptions;
	}

}
