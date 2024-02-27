/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essms.admin.entities.EndCustomer;
import com.essms.admin.entities.EndCustomerCard;
import com.essms.admin.models.request.CreateCustomerCardRequest;
import com.essms.admin.models.request.UpdateCustomerCardRequest;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.SearchEntity;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/endcustomer/card")
public class EndCustomerCardController extends
		CRUDController<EndCustomerCard, CreateCustomerCardRequest, UpdateCustomerCardRequest, EndCustomerCard, SearchEntity> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.hibernate.core.controller.CRUDController#
	 * getHeaderLabelAndFieldPropertyMap()
	 */
	@Override
	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("id", new FieldHeaderLabelAndEntityProperty("Id", "id"));
		headerLabelAndFieldPropertyMap.put("nameOfCard",
				new FieldHeaderLabelAndEntityProperty("Name of Card", "nameOfCard"));
		headerLabelAndFieldPropertyMap.put("modelNo", new FieldHeaderLabelAndEntityProperty("Model Name", "modelNo"));
		headerLabelAndFieldPropertyMap.put("nameOnCard",
				new FieldHeaderLabelAndEntityProperty("Name on Card", "nameOnCard"));
		headerLabelAndFieldPropertyMap.put("cardNo", new FieldHeaderLabelAndEntityProperty("Card No", "cardNo"));
		headerLabelAndFieldPropertyMap.put("expriryDate",
				new FieldHeaderLabelAndEntityProperty("Expiry Date", "expriryDate"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<EndCustomerCard> setupList(List<EndCustomerCard> entities) {
		return entities;
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
	public EndCustomerCard setupCreate(CreateCustomerCardRequest createRequest, EndCustomerCard entity) {
		// TODO throw 403 if unauthorized user trying to add or edit the record
		entity.setCardNo(createRequest.getCardNo());
		entity.setExpriryDate(createRequest.getExpriryDate());
		entity.setNameOfCard(createRequest.getNameOfCard());
		entity.setNameOnCard(createRequest.getNameOnCard());
		entity.setEndCustomer(genericRepository.getByGUID(EndCustomer.class, createRequest.getUserGUID()));
		return entity;
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
	public EndCustomerCard setupUpdate(UpdateCustomerCardRequest updateRequest, EndCustomerCard entity) {
		entity.setCardNo(updateRequest.getCardNo());
		entity.setExpriryDate(updateRequest.getExpriryDate());
		entity.setNameOfCard(updateRequest.getNameOfCard());
		entity.setNameOnCard(updateRequest.getNameOnCard());
		entity.setEndCustomer(genericRepository.getByGUID(EndCustomer.class, updateRequest.getUserGUID()));
		return entity;
	}

}
