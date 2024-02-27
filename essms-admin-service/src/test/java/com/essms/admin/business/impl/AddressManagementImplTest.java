/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.essms.admin.business.AddressManagement;
import com.essms.admin.entities.Address;
import com.essms.admin.entities.Area;
import com.essms.admin.entities.City;
import com.essms.admin.entities.State;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressManagementImplTest {

	@Mock
	private GenericRepository genericRepository;

	@InjectMocks
	private final AddressManagement addressManagement = new AddressManagementImpl();

	@Test
	public void detailWithGUIDReturnsListOfDetailModels() {

		Address address = new Address();
		address.setId(1l);
		address.setAddressLine1("address1");
		address.setAddressLine2("address2");
		address.setArea(null);
		address.setCity(null);
		address.setPinCode("123");
		address.setPhoneNo("123");

		List<DetailModel> detailModels = new LinkedList<>();
		detailModels.add(new DetailModel("Address Line 1", address.getAddressLine1()));
		detailModels.add(new DetailModel("Address Line 2", address.getAddressLine2()));
		detailModels.add(new DetailModel("Nearest Landmark", address.getNearestLandMark()));
		detailModels.add(new DetailModel("Area", address.getArea() == null ? "N/A" : address.getArea().getName()));
		detailModels.add(new DetailModel("City", address.getCity() == null ? "N/A" : address.getCity().getName()));
		detailModels.add(
				new DetailModel("State", address.getCity() == null ? "N/A" : address.getCity().getState().getName()));
		detailModels.add(new DetailModel("Pin Code", address.getPinCode()));
		detailModels.add(new DetailModel("Phone No", address.getPhoneNo()));

		Mockito.when(genericRepository.getByGUID(Mockito.any(), Mockito.anyString())).thenReturn(address);
		List<DetailModel> models = addressManagement.detail("abc");
		Assert.assertEquals("Not equal", "address1", models.get(0).getValue());
	}

	@Test
	public void detailWithGUIDHasCityReturnsListOfDetailModels() {

		Address address = new Address();
		address.setId(1l);
		address.setAddressLine1("address1");
		address.setAddressLine2("address2");
		address.setArea(null);
		State state = State.initializeBuilder().setId(1l).setCode("KA").setTin("29").setName("Karnataka").build();
		City city = new City();
		city.setState(state);
		city.setId(1l);
		city.setName("Bangalore");
		address.setCity(city);
		address.setPinCode("123");
		address.setPhoneNo("123");

		List<DetailModel> detailModels = new LinkedList<>();
		detailModels.add(new DetailModel("Address Line 1", address.getAddressLine1()));
		detailModels.add(new DetailModel("Address Line 2", address.getAddressLine2()));
		detailModels.add(new DetailModel("Nearest Landmark", address.getNearestLandMark()));
		detailModels.add(new DetailModel("Area", address.getArea() == null ? "N/A" : address.getArea().getName()));
		detailModels.add(new DetailModel("City", address.getCity() == null ? "N/A" : address.getCity().getName()));
		detailModels.add(
				new DetailModel("State", address.getCity() == null ? "N/A" : address.getCity().getState().getName()));
		detailModels.add(new DetailModel("Pin Code", address.getPinCode()));
		detailModels.add(new DetailModel("Phone No", address.getPhoneNo()));

		Mockito.when(genericRepository.getByGUID(Mockito.any(), Mockito.anyString())).thenReturn(address);
		List<DetailModel> models = addressManagement.detail("abc");
		Assert.assertEquals("Not equal", "address1", models.get(0).getValue());
	}

	@Test
	public void detailWithGUIDHasAreaReturnsListOfDetailModels() {

		Address address = new Address();
		address.setId(1l);
		address.setAddressLine1("address1");
		address.setAddressLine2("address2");
		address.setArea(null);
		State state = State.initializeBuilder().setId(1l).setCode("KA").setTin("29").setName("Karnataka").build();
		City city = new City();
		city.setState(state);
		city.setId(1l);
		city.setName("Bangalore");
		address.setCity(city);
		Area area = new Area();
		area.setId(1l);
		area.setName("Kas");
		address.setArea(area);
		address.setPinCode("123");
		address.setPhoneNo("123");

		List<DetailModel> detailModels = new LinkedList<>();
		detailModels.add(new DetailModel("Address Line 1", address.getAddressLine1()));
		detailModels.add(new DetailModel("Address Line 2", address.getAddressLine2()));
		detailModels.add(new DetailModel("Nearest Landmark", address.getNearestLandMark()));
		detailModels.add(new DetailModel("Area", address.getArea() == null ? "N/A" : address.getArea().getName()));
		detailModels.add(new DetailModel("City", address.getCity() == null ? "N/A" : address.getCity().getName()));
		detailModels.add(
				new DetailModel("State", address.getCity() == null ? "N/A" : address.getCity().getState().getName()));
		detailModels.add(new DetailModel("Pin Code", address.getPinCode()));
		detailModels.add(new DetailModel("Phone No", address.getPhoneNo()));

		Mockito.when(genericRepository.getByGUID(Mockito.any(), Mockito.anyString())).thenReturn(address);
		List<DetailModel> models = addressManagement.detail("abc");
		Assert.assertEquals("Not equal", "address1", models.get(0).getValue());
	}

}
