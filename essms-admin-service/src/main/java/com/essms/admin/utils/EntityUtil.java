/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.utils;

import java.util.UUID;

import com.essms.admin.entities.Address;
import com.essms.admin.entities.Area;
import com.essms.admin.entities.City;
import com.essms.admin.models.AddressModel;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
public class EntityUtil {

	/**
	 * Patches data to Address entity from Address Model
	 *
	 * @param address
	 * @return
	 */
	public static Address patchAddress(Address address, AddressModel addressModel,
			GenericRepository genericRepository) {
		if (address.getId() == null) {
			address.setGuid(UUID.randomUUID().toString());
		}
		address.setAddressLine1(addressModel.getAddressLine1());
		address.setAddressLine2(addressModel.getAddressLine2());
		address.setFaxNo(addressModel.getFaxNo());
		address.setNearestLandMark(addressModel.getNearestLandMark());
		address.setPhoneNo(addressModel.getPhoneNo());
		address.setPinCode(addressModel.getPinCode());
		address.setLatitude(addressModel.getLatitude());
		address.setLongitude(addressModel.getLongitude());
		if (addressModel.getAreaId() != null && addressModel.getAreaId() > 0) {
			address.setArea(genericRepository.getById(Area.class, addressModel.getAreaId()));
		}
		if (addressModel.getCityId() != null && addressModel.getCityId() > 0) {
			address.setCity(genericRepository.getById(City.class, addressModel.getCityId()));
		}
		return address;
	}

}
