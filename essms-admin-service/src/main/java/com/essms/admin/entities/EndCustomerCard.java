/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.essms.hibernate.core.entities.AuditableBaseEntity;
import com.essms.hibernate.core.models.ListEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
public class EndCustomerCard extends AuditableBaseEntity implements ListEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100)
	private String nameOfCard;

	@Column(length = 100)
	private String nameOnCard;

	@Column(length = 20)
	private String cardNo;

	private Date expriryDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
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
