/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.entities.Area;
import com.essms.admin.entities.Branch;
import com.essms.admin.entities.City;
import com.essms.admin.entities.EndCustomerAddress;
import com.essms.admin.entities.Investor;
import com.essms.admin.entities.State;
import com.essms.admin.entities.Vendor;
import com.essms.admin.entities.VendorAddress;
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

	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public List<FormOption> state() {
		List<FormOption> options = new ArrayList<>();
		List<State> states = genericRepository.findAllActiveAndUndeleted(State.class);
		FormOption formOption = null;
		for (State state : states) {
			formOption = new FormOption();
			formOption.setLabel(state.getName());
			formOption.setValue(state.getId());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/vendor/withguid", method = RequestMethod.GET)
	public List<FormOption> vendorWithGUID() {
		List<FormOption> options = new ArrayList<>();
		List<Vendor> vendors = genericRepository.findAllActiveAndUndeleted(Vendor.class);
		FormOption formOption = null;
		for (Vendor vendor : vendors) {
			formOption = new FormOption();
			formOption.setLabel(vendor.getName());
			formOption.setValue(vendor.getGuid());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/vendor/withguidNname", method = RequestMethod.GET)
	public List<FormOption> vendorWithGUIDandName() {
		List<FormOption> options = new ArrayList<>();
		List<Vendor> vendors = genericRepository.findAllActiveAndUndeleted(Vendor.class);
		FormOption formOption = null;
		for (Vendor vendor : vendors) {
			formOption = new FormOption();
			formOption.setLabel(vendor.getName());
			formOption.setValue(vendor.getGuid() + "::" + vendor.getName());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/citybystate", method = RequestMethod.GET)
	public List<FormOption> cityByState(@RequestParam(value = "stateId", required = false) Long stateId) {
		List<FormOption> options = new ArrayList<>();
		if (stateId == null || stateId <= 0) {
			return options;
		}
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("state.id", stateId);
		criterias.put("isDeleted", false);
		criterias.put("isActive", true);
		List<City> cities = genericRepository.findByCriteria(City.class, criterias);
		FormOption formOption = null;
		for (City city : cities) {
			formOption = new FormOption();
			formOption.setLabel(city.getName());
			formOption.setValue(city.getId());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/areabycity", method = RequestMethod.GET)
	public List<FormOption> areaByCity(@RequestParam(value = "cityId", required = false) Long cityId) {
		List<FormOption> options = new ArrayList<>();
		if (cityId == null || cityId <= 0) {
			return options;
		}
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("city.id", cityId);
		criterias.put("isDeleted", false);
		criterias.put("isActive", true);
		List<Area> areas = genericRepository.findByCriteria(Area.class, criterias);
		FormOption formOption = null;
		for (Area area : areas) {
			formOption = new FormOption();
			formOption.setLabel(area.getName());
			formOption.setValue(area.getId());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/branch", method = RequestMethod.GET)
	public List<FormOption> branch() {
		List<FormOption> options = new ArrayList<>();
		List<Branch> branches = genericRepository.findAllActiveAndUndeleted(Branch.class);
		FormOption formOption = null;
		for (Branch branch : branches) {
			formOption = new FormOption();
			formOption.setLabel(branch.getName());
			formOption.setValue(branch.getId());
			options.add(formOption);
		}
		return options;
	}

	@RequestMapping(value = "/endcustomer/address/pickup/{regNo}", method = RequestMethod.GET)
	public List<FormOption> addressPickup(@PathVariable("regNo") String regNo) {
		List<FormOption> formOptions = new ArrayList<>();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("endCustomer.regNo", regNo);
		List<EndCustomerAddress> endCustomerAddresses = genericRepository.findByCriteria(EndCustomerAddress.class,
				criterias);
		FormOption formOption = new FormOption();
		formOption.setLabel("Same As Billing");
		formOption.setValue(-1);
		formOptions.add(formOption);
		for (EndCustomerAddress endCustomerAddress : endCustomerAddresses) {
			formOption = new FormOption();
			formOption.setLabel(endCustomerAddress.getAddress().toString());
			formOption.setValue(endCustomerAddress.getAddress().getGuid());
			formOptions.add(formOption);
		}
		return formOptions;
	}

	@RequestMapping(value = "/endcustomer/address/delivery/{regNo}", method = RequestMethod.GET)
	public List<FormOption> addressDelivery(@PathVariable("regNo") String regNo) {
		List<FormOption> formOptions = new ArrayList<>();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("endCustomer.regNo", regNo);
		List<EndCustomerAddress> endCustomerAddresses = genericRepository.findByCriteria(EndCustomerAddress.class,
				criterias);
		FormOption formOption = new FormOption();
		formOption.setLabel("Same As Billing");
		formOption.setValue(-1);
		formOptions.add(formOption);
		for (EndCustomerAddress endCustomerAddress : endCustomerAddresses) {
			formOption = new FormOption();
			formOption.setLabel(endCustomerAddress.getAddress().toString());
			formOption.setValue(endCustomerAddress.getAddress().getGuid());
			formOptions.add(formOption);
		}
		return formOptions;
	}

	@RequestMapping(value = "/endcustomer/address/drop/{regNo}", method = RequestMethod.GET)
	public List<FormOption> addressDrop(@PathVariable("regNo") String regNo) {
		List<FormOption> formOptions = new ArrayList<>();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("endCustomer.regNo", regNo);
		List<EndCustomerAddress> endCustomerAddresses = genericRepository.findByCriteria(EndCustomerAddress.class,
				criterias);
		FormOption formOption = new FormOption();
		formOption.setLabel("Same As Pickup");
		formOption.setValue(-1);
		formOptions.add(formOption);
		for (EndCustomerAddress endCustomerAddress : endCustomerAddresses) {
			formOption = new FormOption();
			formOption.setLabel(endCustomerAddress.getAddress().toString());
			formOption.setValue(endCustomerAddress.getAddress().getGuid());
			formOptions.add(formOption);
		}
		return formOptions;
	}

	@RequestMapping(value = "/vendor/address/{guid}", method = RequestMethod.GET)
	public List<FormOption> addressVendor(@PathVariable("guid") String guid) {
		List<FormOption> formOptions = new ArrayList<>();
		Vendor vendor = genericRepository.getByGUID(Vendor.class, guid);
		FormOption formOption = null;
		for (VendorAddress vendorAddress : vendor.getVendorAddresses()) {
			formOption = new FormOption();
			formOption.setLabel(vendorAddress.getAddress().toString());
			formOption.setValue(vendorAddress.getAddress().getGuid());
			formOptions.add(formOption);
		}
		return formOptions;
	}

	@RequestMapping(value = "/investor/withguid", method = RequestMethod.GET)
	public List<FormOption> investorWithGUID() {
		List<FormOption> options = new ArrayList<>();
		List<Investor> investors = genericRepository.findAllActiveAndUndeleted(Investor.class);
		FormOption formOption = null;
		for (Investor investor : investors) {
			formOption = new FormOption();
			formOption.setLabel(investor.getName());
			formOption.setValue(investor.getGuid() + "::" + investor.getName());
			options.add(formOption);
		}
		return options;
	}

}
