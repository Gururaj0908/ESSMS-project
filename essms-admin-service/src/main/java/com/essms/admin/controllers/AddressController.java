/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.business.AddressManagement;
import com.essms.admin.entities.Address;
import com.essms.admin.models.AddressModel;
import com.essms.admin.utils.ModelUtil;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/address/public")
public class AddressController {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private AddressManagement addressManagement;

	@RequestMapping("/fetch/{addressGUID}")
	public AddressModel getByGUID(@PathVariable("addressGUID") String addressGUID) {
		return ModelUtil.generateAddressModel(genericRepository.getByGUID(Address.class, addressGUID.toLowerCase()));
	}

	@RequestMapping(value = "/detail/{addressGUID}", method = RequestMethod.GET)
	public List<DetailModel> detail(@PathVariable("addressGUID") String addressGUID) {
		return addressManagement.detail(addressGUID);
	}
}
