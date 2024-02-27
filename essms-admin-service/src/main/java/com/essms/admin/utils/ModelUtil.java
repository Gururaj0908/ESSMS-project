/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.utils;

import com.essms.admin.entities.Address;
import com.essms.admin.entities.ContactDetail;
import com.essms.admin.models.AddressModel;
import com.essms.admin.models.ContactDetailModel;

/**
 * @author gaurav
 *
 */
public class ModelUtil {

	/**
	 *
	 * @param address
	 * @return
	 */
	public static AddressModel generateAddressModel(Address address) {
		AddressModel addressModel = new AddressModel();
		addressModel = new AddressModel();
		addressModel.setId(address.getId());
		addressModel.setAddressLine1(address.getAddressLine1());
		addressModel.setAddressLine2(address.getAddressLine2());
		addressModel.setFaxNo(address.getFaxNo());
		addressModel.setNearestLandMark(address.getNearestLandMark());
		addressModel.setPhoneNo(address.getPhoneNo());
		addressModel.setPinCode(address.getPinCode());
		addressModel.setLatitude(address.getLatitude());
		addressModel.setLongitude(address.getLongitude());
		if (address.getArea() != null) {
			addressModel.setAreaId(address.getArea().getId());
			addressModel.setArea(address.getArea().getName());
			addressModel.setCityId(address.getArea().getCity().getId());
			addressModel.setCity(address.getArea().getCity().getName());
			addressModel.setStateId(address.getArea().getCity().getState().getId());
			addressModel.setState(address.getArea().getCity().getState().getName());
		} else if (address.getCity() != null) {
			addressModel.setCityId(address.getCity().getId());
			addressModel.setCity(address.getCity().getName());
			addressModel.setStateId(address.getCity().getState().getId());
			addressModel.setState(address.getCity().getState().getName());
		}
		return addressModel;
	}

	public static ContactDetailModel generateContactDetailModel(ContactDetail contactDetail) {
		ContactDetailModel contactDetailModel = new ContactDetailModel();
		contactDetailModel.setId(contactDetail.getId());
		contactDetailModel.setContactPerson(contactDetail.getContactPerson());
		contactDetailModel.setContactMobile(contactDetail.getContactMobile());
		contactDetailModel.setContactEmailId(contactDetail.getContactEmailId());
		contactDetailModel.setDesignation(contactDetail.getDesignation());
		return contactDetailModel;
	}
}
