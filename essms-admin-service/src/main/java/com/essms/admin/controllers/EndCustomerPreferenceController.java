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
import com.essms.admin.entities.EndCustomerPreference;
import com.essms.admin.models.request.CreateEndCustomerPreference;
import com.essms.admin.models.request.UpdateEndCustomerPreference;
import com.essms.hibernate.core.controller.CRUDController;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.SearchEntity;

/**
 * @author gaurav
 *
 */
@RestController
@RequestMapping("/endcustomer/preference")
public class EndCustomerPreferenceController extends
		CRUDController<EndCustomerPreference, CreateEndCustomerPreference, UpdateEndCustomerPreference, EndCustomerPreference, SearchEntity> {

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
		headerLabelAndFieldPropertyMap.put("isOfferNewsletterEnabled",
				new FieldHeaderLabelAndEntityProperty("Office News letter enabled", "isOfferNewsletterEnabled"));
		headerLabelAndFieldPropertyMap.put("isMarketingNewsletterEnabled",
				new FieldHeaderLabelAndEntityProperty("Marketing Newsletter enabled", "isMarketingNewsletterEnabled"));
		headerLabelAndFieldPropertyMap.put("isReviewEnabled",
				new FieldHeaderLabelAndEntityProperty("Review Enabled", "isReviewEnabled"));
		headerLabelAndFieldPropertyMap.put("isSurveyEnabled",
				new FieldHeaderLabelAndEntityProperty("Survey Enabled", "isSurveyEnabled"));
		headerLabelAndFieldPropertyMap.put("promotionalSMSPreference",
				new FieldHeaderLabelAndEntityProperty("Promotional SMS Preference", "promotionalSMSPreference"));
		headerLabelAndFieldPropertyMap.put("promotionalEmailPreference",
				new FieldHeaderLabelAndEntityProperty("Promotional Email Preference", "promotionalEmailPreference"));
		return headerLabelAndFieldPropertyMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.hibernate.core.controller.CRUDController#setupList(java.util.List)
	 */
	@Override
	public List<EndCustomerPreference> setupList(List<EndCustomerPreference> entities) {
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
	public EndCustomerPreference setupCreate(CreateEndCustomerPreference createRequest, EndCustomerPreference entity) {
		// TODO throw 403 if unauthorized user trying to add or edit the record
		entity.setIsMarketingNewsletterEnabled(createRequest.getIsMarketingNewsletterEnabled());
		entity.setIsOfferNewsletterEnabled(createRequest.getIsOfferNewsletterEnabled());
		entity.setIsReviewEnabled(createRequest.getIsReviewEnabled());
		entity.setIsSurveyEnabled(createRequest.getIsSurveyEnabled());
		entity.setPromotionalEmailPreference(createRequest.getPromotionalEmailPreference());
		entity.setPromotionalSMSPreference(createRequest.getPromotionalSMSPreference());
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
	public EndCustomerPreference setupUpdate(UpdateEndCustomerPreference updateRequest, EndCustomerPreference entity) {
		entity.setIsMarketingNewsletterEnabled(updateRequest.getIsMarketingNewsletterEnabled());
		entity.setIsOfferNewsletterEnabled(updateRequest.getIsOfferNewsletterEnabled());
		entity.setIsReviewEnabled(updateRequest.getIsReviewEnabled());
		entity.setIsSurveyEnabled(updateRequest.getIsSurveyEnabled());
		entity.setPromotionalEmailPreference(updateRequest.getPromotionalEmailPreference());
		entity.setPromotionalSMSPreference(updateRequest.getPromotionalSMSPreference());
		return entity;
	}

}
