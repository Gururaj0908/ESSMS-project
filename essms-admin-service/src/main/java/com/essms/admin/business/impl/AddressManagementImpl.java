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

import com.essms.admin.business.AddressManagement;
import com.essms.admin.entities.Address;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Service
public class AddressManagementImpl implements AddressManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Override
	public List<DetailModel> detail(String guid) {
		Address address = genericRepository.getByGUID(Address.class, guid);
		final List<DetailModel> detailModels = new LinkedList<>();
		detailModels.add(new DetailModel("Address Line 1", address.getAddressLine1()));
		detailModels.add(new DetailModel("Address Line 2", address.getAddressLine2()));
		detailModels.add(new DetailModel("Nearest Landmark", address.getNearestLandMark()));
		detailModels.add(new DetailModel("Area", address.getArea() == null ? "N/A" : address.getArea().getName()));
		detailModels.add(new DetailModel("City", address.getCity() == null ? "N/A" : address.getCity().getName()));
		detailModels.add(
				new DetailModel("State", address.getCity() == null ? "N/A" : address.getCity().getState().getName()));
		detailModels.add(new DetailModel("Pin Code", address.getPinCode()));
		detailModels.add(new DetailModel("Phone No", address.getPhoneNo()));
		return detailModels;
	}

}
