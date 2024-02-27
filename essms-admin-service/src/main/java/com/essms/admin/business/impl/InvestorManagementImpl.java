/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essms.admin.business.InvestorManagement;
import com.essms.admin.entities.Investor;
import com.essms.hibernate.core.enums.ContentType;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author ekamra.nayak
 *
 */
@Service
public class InvestorManagementImpl implements InvestorManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Override
	public List<DetailModel> getInvestorDetail(String investorGUID) {
		final List<DetailModel> detailModels = new LinkedList<>();
		final Investor investor = genericRepository.getByGUID(Investor.class, investorGUID);
		if (investor == null) {
			detailModels.add(new DetailModel("This Investor does not Exist", ""));
			return detailModels;
		}
		detailModels.add(new DetailModel("User Name", investor.getUsername()));
		detailModels.add(new DetailModel("Email Id", investor.getEmailId()));
		detailModels.add(new DetailModel("Name", investor.getName()));
		detailModels.add(new DetailModel("Mobile No", investor.getMobileNo()));
		detailModels.add(new DetailModel("Oultet Address", null, null, ContentType.Separator));
		detailModels.add(new DetailModel("Address Line 1", investor.getAddress().getAddressLine1()));
		detailModels.add(new DetailModel("Address Line 2", investor.getAddress().getAddressLine2()));
		detailModels.add(new DetailModel("Nearest Landmark", investor.getAddress().getNearestLandMark()));
		detailModels.add(new DetailModel("Area",
				investor.getAddress().getArea() == null ? "" : investor.getAddress().getArea().getName()));
		detailModels.add(new DetailModel("City",
				investor.getAddress().getCity() == null ? "" : investor.getAddress().getCity().getName()));
		detailModels.add(new DetailModel("State",
				investor.getAddress().getCity() == null ? "" : investor.getAddress().getCity().getState().getName()));
		detailModels.add(new DetailModel("Pin Code", investor.getAddress().getPinCode()));
		detailModels.add(new DetailModel("Phone No", investor.getAddress().getPhoneNo()));
		return detailModels;
	}
}
