/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essms.admin.business.SystemConfigManagement;
import com.essms.admin.constants.APIConstant;
import com.essms.admin.entities.Area;
import com.essms.admin.entities.Branch;
import com.essms.admin.entities.City;
import com.essms.admin.entities.State;
import com.essms.admin.models.request.SyncDataRequest;
import com.essms.admin.publishers.LocationExchangePublisher;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.enums.MethodType;
import com.essms.hibernate.core.models.Button;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.SubForm;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.core.utils.FormGeneratorUtil;

/**
 * @author gaurav
 *
 */
@Service
public class SystemConfigManagementImpl implements SystemConfigManagement {

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private LocationExchangePublisher locationExchangePublisher;

	@Override
	public FormList form() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		FormList formList = null;
		List<FormList> formLists = new ArrayList<>();

		formList = formGeneratorUtil.generateFormList(SyncDataRequest.class, null, FormDisplayMode.StrictlyHorizontal);
		List<Button> formButtons = new ArrayList<>();
		Button button = new Button("Submit", APIConstant.SUBMIT_SYNC_DATA_FORM, false, MethodType.POST.toString(),
				false);
		formButtons.add(button);
		formList.setFormButtons(formButtons);
		formLists.add(formList);

		formList = formGeneratorUtil.generateFormList(SyncDataRequest.class, null, FormDisplayMode.StrictlyHorizontal);
		formButtons = new ArrayList<>();
		button = new Button("Submit", APIConstant.SUBMIT_SYNC_DATA_FORM, false, MethodType.POST.toString(), false);
		formButtons.add(button);
		formList.setFormButtons(formButtons);
		formLists.add(formList);

		formList = new FormList();
		List<SubForm> subForms = new ArrayList<>();
		SubForm subForm = new SubForm();
		subForm.setFormLists(formLists);
		subForms.add(subForm);
		formList.setSubForms(subForms);
		return formList;
	}

	@Override
	public void syncData(SyncDataRequest syncDataRequest) {
		if (syncDataRequest.isSyncLocation()) {
			syncLocation();
		}
	}

	private void syncLocation() {
		for (State state : genericRepository.findAll(State.class)) {
			locationExchangePublisher.produceState(state);
		}
		for (City city : genericRepository.findAll(City.class)) {
			locationExchangePublisher.produceCity(city);
		}
		for (Area area : genericRepository.findAll(Area.class)) {
			locationExchangePublisher.produceArea(area);
		}
		for (Branch branch : genericRepository.findAll(Branch.class)) {
			locationExchangePublisher.produceBranch(branch);
		}
	}

}
