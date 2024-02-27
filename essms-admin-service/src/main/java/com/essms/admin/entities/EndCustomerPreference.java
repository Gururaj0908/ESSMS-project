/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.essms.core.enums.PromotionalEmailPreference;
import com.essms.core.enums.PromotionalSMSPreference;
import com.essms.hibernate.core.entities.AuditableBaseEntity;
import com.essms.hibernate.core.models.ListEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
public class EndCustomerPreference extends AuditableBaseEntity implements ListEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated
	@Column(columnDefinition = "smallint")
	private PromotionalSMSPreference promotionalSMSPreference = PromotionalSMSPreference.ALL;

	@Enumerated
	@Column(columnDefinition = "smallint")
	private PromotionalEmailPreference promotionalEmailPreference = PromotionalEmailPreference.ALL;

	private Boolean isOfferNewsletterEnabled = true;

	private Boolean isMarketingNewsletterEnabled = true;

	private Boolean isReviewEnabled = true;

	private Boolean isSurveyEnabled = true;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private EndCustomer endCustomer;

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the promotionalSMSPreference
	 * 
	 * @return the promotionalSMSPreference
	 */
	public PromotionalSMSPreference getPromotionalSMSPreference() {
		return promotionalSMSPreference;
	}

	/**
	 * Sets the promotionalSMSPreference
	 * 
	 * @param promotionalSMSPreference
	 *            the promotionalSMSPreference to set
	 */
	public void setPromotionalSMSPreference(PromotionalSMSPreference promotionalSMSPreference) {
		this.promotionalSMSPreference = promotionalSMSPreference;
	}

	/**
	 * Returns the promotionalEmailPreference
	 * 
	 * @return the promotionalEmailPreference
	 */
	public PromotionalEmailPreference getPromotionalEmailPreference() {
		return promotionalEmailPreference;
	}

	/**
	 * Sets the promotionalEmailPreference
	 * 
	 * @param promotionalEmailPreference
	 *            the promotionalEmailPreference to set
	 */
	public void setPromotionalEmailPreference(PromotionalEmailPreference promotionalEmailPreference) {
		this.promotionalEmailPreference = promotionalEmailPreference;
	}

	/**
	 * Returns the isOfferNewsletterEnabled
	 * 
	 * @return the isOfferNewsletterEnabled
	 */
	public Boolean getIsOfferNewsletterEnabled() {
		return isOfferNewsletterEnabled;
	}

	/**
	 * Sets the isOfferNewsletterEnabled
	 * 
	 * @param isOfferNewsletterEnabled
	 *            the isOfferNewsletterEnabled to set
	 */
	public void setIsOfferNewsletterEnabled(Boolean isOfferNewsletterEnabled) {
		this.isOfferNewsletterEnabled = isOfferNewsletterEnabled;
	}

	/**
	 * Returns the isMarketingNewsletterEnabled
	 * 
	 * @return the isMarketingNewsletterEnabled
	 */
	public Boolean getIsMarketingNewsletterEnabled() {
		return isMarketingNewsletterEnabled;
	}

	/**
	 * Sets the isMarketingNewsletterEnabled
	 * 
	 * @param isMarketingNewsletterEnabled
	 *            the isMarketingNewsletterEnabled to set
	 */
	public void setIsMarketingNewsletterEnabled(Boolean isMarketingNewsletterEnabled) {
		this.isMarketingNewsletterEnabled = isMarketingNewsletterEnabled;
	}

	/**
	 * Returns the isReviewEnabled
	 * 
	 * @return the isReviewEnabled
	 */
	public Boolean getIsReviewEnabled() {
		return isReviewEnabled;
	}

	/**
	 * Sets the isReviewEnabled
	 * 
	 * @param isReviewEnabled
	 *            the isReviewEnabled to set
	 */
	public void setIsReviewEnabled(Boolean isReviewEnabled) {
		this.isReviewEnabled = isReviewEnabled;
	}

	/**
	 * Returns the isSurveyEnabled
	 * 
	 * @return the isSurveyEnabled
	 */
	public Boolean getIsSurveyEnabled() {
		return isSurveyEnabled;
	}

	/**
	 * Sets the isSurveyEnabled
	 * 
	 * @param isSurveyEnabled
	 *            the isSurveyEnabled to set
	 */
	public void setIsSurveyEnabled(Boolean isSurveyEnabled) {
		this.isSurveyEnabled = isSurveyEnabled;
	}

	/**
	 * Returns the endCustomer
	 * 
	 * @return the endCustomer
	 */
	public EndCustomer getEndCustomer() {
		return endCustomer;
	}

	/**
	 * Sets the endCustomer
	 * 
	 * @param endCustomer
	 *            the endCustomer to set
	 */
	public void setEndCustomer(EndCustomer endCustomer) {
		this.endCustomer = endCustomer;
	}

}
