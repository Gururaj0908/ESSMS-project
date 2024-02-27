/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import com.essms.core.enums.PromotionalEmailPreference;
import com.essms.core.enums.PromotionalSMSPreference;
import com.essms.hibernate.core.models.UpdateEntity;

/**
 * @author gaurav
 *
 */
public class UpdateEndCustomerPreference implements UpdateEntity {

	private Long id;

	private PromotionalSMSPreference promotionalSMSPreference;

	private PromotionalEmailPreference promotionalEmailPreference;

	private Boolean isOfferNewsletterEnabled;

	private Boolean isMarketingNewsletterEnabled;

	private Boolean isReviewEnabled;

	private Boolean isSurveyEnabled;

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
	@Override
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

}
