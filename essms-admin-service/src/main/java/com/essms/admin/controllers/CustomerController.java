/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.business.CustomerManagement;
import com.essms.admin.enums.CustomerPageRoute;
import com.essms.core.enums.CustomerType;
import com.essms.core.exception.ValidationException;
import com.essms.core.util.FieldErrorUtil;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.request.IdRequest;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;
import com.essms.hibernate.core.models.response.ListWithCountResponse;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerManagement customerManagement;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ListWithCountResponse allCustomers(
			@RequestParam(value = "jsonQueryString", required = false) String jsonQueryString,
			@RequestParam(value = "includeDeActive", required = false) Boolean includeDeActive,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "desc", required = false) Boolean desc) {
		return customerManagement.list(jsonQueryString, includeDeActive, pageNumber, pageSize, orderBy, desc);
	}

	@RequestMapping(value = "/listwithforms", method = RequestMethod.GET)
	public ListWithCountAndFormsResponse listWithForms(
			@RequestParam(value = "jsonQueryString", required = false) String jsonQueryString,
			@RequestParam(value = "includeDeActive", required = false) Boolean includeDeActive,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "desc", required = false) Boolean desc)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		if (pageNumber != null && pageNumber < 1) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("Customer", "pageNumber", "Must be >= 1"));
		}
		return customerManagement.listWithForms(jsonQueryString, includeDeActive, pageNumber, pageSize, orderBy, desc);
	}

	@RequestMapping(value = "/bookconsumption/listwithforms/{itemType}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ListWithCountAndFormsResponse bookConsumptionListWithForms(@PathVariable("itemType") String itemType,
			@RequestParam(value = "jsonQueryString", required = false) String jsonQueryString,
			@RequestParam(value = "includeDeActive", required = false) Boolean includeDeActive,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "desc", required = false) Boolean desc)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		if (pageNumber != null && pageNumber < 1) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("Customer", "pageNumber", "Must be >= 1"));
		}
		return customerManagement.bookConsumptionListWithForms(itemType, jsonQueryString, includeDeActive, pageNumber,
				pageSize, orderBy, desc);
	}

	@RequestMapping(value = "/quicksale/listwithforms/{itemType}", method = RequestMethod.POST)
	public ListWithCountAndFormsResponse quickSaleCustomers(@PathVariable("itemType") String itemType,
			@RequestBody List<IdRequest> idRequests,
			@RequestParam(value = "jsonQueryString", required = false) String jsonQueryString,
			@RequestParam(value = "includeDeActive", required = false) Boolean includeDeActive,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "desc", required = false) Boolean desc)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		if (pageNumber != null && pageNumber < 1) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("Customer", "pageNumber", "Must be >= 1"));
		}
		return customerManagement.quickSaleCustomers(itemType, idRequests, jsonQueryString, includeDeActive, pageNumber,
				pageSize, orderBy, desc);
	}

	@RequestMapping(value = "/detail/{customerRegNo}/{customerName}", method = RequestMethod.GET)
	public List<DetailModel> detail(@PathVariable("customerRegNo") String customerRegNo,
			@PathVariable(value = "customerRegNo", required = false) String customerName) {
		return customerManagement.detail(customerRegNo);
	}

	@RequestMapping(value = "/listinventory/bookconsumption/listwithforms/{itemType}", method = RequestMethod.POST)
	public ListWithCountAndFormsResponse bookConsumptionFromListInventory(@PathVariable("itemType") String itemType,
			@RequestBody List<IdRequest> idRequests,
			@RequestParam(value = "jsonQueryString", required = false) String jsonQueryString,
			@RequestParam(value = "includeDeActive", required = false) Boolean includeDeActive,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "desc", required = false) Boolean desc)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		if (pageNumber != null && pageNumber < 1) {
			throw new ValidationException(FieldErrorUtil.generateFieldErrors("Customer", "pageNumber", "Must be >= 1"));
		}
		return customerManagement.bookConsumptionFromListInventory(itemType, idRequests, jsonQueryString,
				includeDeActive, pageNumber, pageSize, orderBy, desc);
	}

	@GetMapping("/form")
	public FormList form(@RequestParam("customerGUID") String customerGUID,
			@RequestParam("customerType") CustomerType customerType,
			@RequestParam("customerPageRoute") CustomerPageRoute customerPageRoute)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		return customerManagement.form(customerGUID, customerType, customerPageRoute);
	}

}
