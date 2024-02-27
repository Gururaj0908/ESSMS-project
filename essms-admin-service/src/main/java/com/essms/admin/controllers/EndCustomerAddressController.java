/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.constants.APIConstant;
import com.essms.admin.entities.Address;
import com.essms.admin.entities.EndCustomer;
import com.essms.admin.entities.EndCustomerAddress;
import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.list.ListEndCustomerAddress;
import com.essms.admin.models.request.UpsertEndCustomerAddress;
import com.essms.admin.repositories.EndCustomerAddressRepository;
import com.essms.admin.utils.EntityUtil;
import com.essms.admin.utils.ModelUtil;
import com.essms.core.enums.AddressType;
import com.essms.core.exception.ValidationException;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.enums.MethodType;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.FormOption;
import com.essms.hibernate.core.models.SearchEntity;
import com.essms.hibernate.core.utils.FormGeneratorUtil;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/endcustomer/address")
public class EndCustomerAddressController extends
		CRUDController<EndCustomerAddress, UpsertEndCustomerAddress, UpsertEndCustomerAddress, ListEndCustomerAddress, SearchEntity> {

	@Autowired
	private EndCustomerAddressRepository endCustomerAddressRepository;

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.hibernate.core.controller.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		final Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("id", new FieldHeaderLabelAndEntityProperty("Id", "id"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<ListEndCustomerAddress> setupList(List<EndCustomerAddress> entities) {
		final List<ListEndCustomerAddress> listEndCustomerAddresses = new ArrayList<>();
		ListEndCustomerAddress listEndCustomerAddress = null;
		for (final EndCustomerAddress endCustomerAddress : entities) {
			listEndCustomerAddress = new ListEndCustomerAddress();
			listEndCustomerAddress.setAddressType(endCustomerAddress.getAddressType());
			listEndCustomerAddress.setIsDefault(endCustomerAddress.getIsDefault());
			listEndCustomerAddress.setAddressModel(ModelUtil.generateAddressModel(endCustomerAddress.getAddress()));
		}
		return listEndCustomerAddresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupCreate(com.essms.
	 * hibernate.core.models.CreateEntity,
	 * com.essms.hibernate.core.models.BaseModel)
	 */
	@Override
	public EndCustomerAddress setupCreate(UpsertEndCustomerAddress createRequest, EndCustomerAddress entity) {
		entity.setAddress(EntityUtil.patchAddress(new Address(), createRequest.getAddressModel(), genericRepository));
		entity.setAddressType(createRequest.getAddressType());
		entity.setEndCustomer(genericRepository.getByGUID(EndCustomer.class, createRequest.getUserGUID()));
		entity.setIsDefault(createRequest.getIsDefault());
		return entity;
	}

	@Override
	protected void validateCreateRequest(UpsertEndCustomerAddress createRequest, EndCustomerAddress entity)
			throws ValidationException {
		if (createRequest.getIsDefault() != null && createRequest.getIsDefault()) {
			endCustomerAddressRepository.updateDefault(entity.getEndCustomer().getGuid(), entity.getId());
		}
	}

	@Override
	protected void validateUpdateRequest(UpsertEndCustomerAddress updateRequest, EndCustomerAddress entity)
			throws ValidationException {
		if (updateRequest.getIsDefault() != null && updateRequest.getIsDefault()) {
			endCustomerAddressRepository.updateDefault(entity.getEndCustomer().getGuid(), entity.getId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupUpdate(com.essms.
	 * hibernate.core.models.UpdateEntity,
	 * com.essms.hibernate.core.models.BaseModel)
	 */
	@Override
	public EndCustomerAddress setupUpdate(UpsertEndCustomerAddress updateRequest, EndCustomerAddress entity) {
		entity.setAddress(
				EntityUtil.patchAddress(entity.getAddress(), updateRequest.getAddressModel(), genericRepository));
		entity.setAddressType(updateRequest.getAddressType());
		entity.setEndCustomer(genericRepository.getByGUID(EndCustomer.class, updateRequest.getUserGUID()));
		entity.setIsDefault(updateRequest.getIsDefault());
		return entity;
	}

	@RequestMapping(value = "/all/{regNo}", method = RequestMethod.GET)
	public List<FormOption> byRegNo(@PathVariable("regNo") String regNo,
			@RequestParam(value = "defaultBilling", required = false) Boolean defaultBilling) {
		final List<FormOption> formOptions = new ArrayList<>();
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("endCustomer.regNo", regNo);
		final List<EndCustomerAddress> endCustomerAddresses = genericRepository.findByCriteria(EndCustomerAddress.class,
				criterias);
		FormOption formOption = null;
		for (final EndCustomerAddress endCustomerAddress : endCustomerAddresses) {
			formOption = new FormOption();
			formOption.setLabel(endCustomerAddress.getAddress().toString());
			formOption.setValue(endCustomerAddress.getAddress().getGuid());
			if (defaultBilling != null && endCustomerAddress.getAddressType() == AddressType.BILLING) {
				formOption.setSelected(true);
			}
			formOptions.add(formOption);
		}
		return formOptions;
	}

	@RequestMapping(value = "/form/{regNo}", method = RequestMethod.GET)
	public FormList form(@PathVariable("regNo") String regNo) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		final FormList formList = formGeneratorUtil.generateFormList(EndCustomerAddressModel.class, null,
				FormDisplayMode.StrictlyVertical);
		formList.setMethod(MethodType.POST.getLabel());
		formList.setUrl(APIConstant.SUBMIT_ENDCUSTOMER_ADDRESS + "/" + regNo);
		return formList;
	}

	@Transactional
	@RequestMapping(value = "/add/{regNo}", method = RequestMethod.POST)
	public FormOption add(@PathVariable("regNo") String regNo,
			@RequestBody EndCustomerAddressModel endCustomerAddressModel)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		final EndCustomerAddress endCustomerAddress = new EndCustomerAddress();
		endCustomerAddress.setAddress(
				EntityUtil.patchAddress(new Address(), endCustomerAddressModel.getAddressModel(), genericRepository));
		endCustomerAddress.setAddressType(endCustomerAddressModel.getAddressType());
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("regNo", regNo);
		endCustomerAddress.setEndCustomer(genericRepository.findByCriteria(EndCustomer.class, criterias).get(0));
		endCustomerAddress.setIsDefault(endCustomerAddressModel.getIsDefault());
		endCustomerAddress.setGuid(UUID.randomUUID().toString());
		genericRepository.saveOrUpdate(endCustomerAddress);
		final FormOption formOption = new FormOption();
		formOption.setLabel(endCustomerAddress.getAddress().toString());
		formOption.setValue(endCustomerAddress.getAddress().getGuid());
		formOption.setSelected(true);
		return formOption;
	}

}
