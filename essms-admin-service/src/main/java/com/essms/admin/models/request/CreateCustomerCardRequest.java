/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.models.CreateEntity;

/**
 * @author gaurav
 *
 */
public class CreateCustomerCardRequest implements CreateEntity {

	@NotEmpty
	private String nameOfCard;

	@NotEmpty
	private String nameOnCard;

	@NotEmpty
	private String cardNo;

	@NotNull
	private Date expriryDate;

	@NotEmpty
	@SkipTextTransformation
	private String userGUID;

	/**
	 * Returns the nameOfCard
	 * 
	 * @return the nameOfCard
	 */
	public String getNameOfCard() {
		return nameOfCard;
	}

	/**
	 * Sets the nameOfCard
	 * 
	 * @param nameOfCard
	 *            the nameOfCard to set
	 */
	public void setNameOfCard(String nameOfCard) {
		this.nameOfCard = nameOfCard;
	}

	/**
	 * Returns the nameOnCard
	 * 
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * Sets the nameOnCard
	 * 
	 * @param nameOnCard
	 *            the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	/**
	 * Returns the cardNo
	 * 
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * Sets the cardNo
	 * 
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * Returns the expriryDate
	 * 
	 * @return the expriryDate
	 */
	public Date getExpriryDate() {
		return expriryDate;
	}

	/**
	 * Sets the expriryDate
	 * 
	 * @param expriryDate
	 *            the expriryDate to set
	 */
	public void setExpriryDate(Date expriryDate) {
		this.expriryDate = expriryDate;
	}

	/**
	 * Returns the userGUID
	 * 
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * Sets the userGUID
	 * 
	 * @param userGUID
	 *            the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

}
