/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.essms.hibernate.core.entities.AuditableEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class UserPermission extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "user_guid")
	private String userGUID;

	@Column(name = "branch_guid")
	private String branchGUID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private BusinessObject businessObject;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Pass the id to be set
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Will Return the userGUID
	 *
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * Pass the userGUID to be set
	 *
	 * @param userGUID the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * @return the branchGUID
	 */
	public String getBranchGUID() {
		return branchGUID;
	}

	/**
	 * @param branchGUID the branchGUID to set
	 */
	public void setBranchGUID(String branchGUID) {
		this.branchGUID = branchGUID;
	}

	/**
	 * Will Return the businessObject
	 *
	 * @return the businessObject
	 */
	public BusinessObject getBusinessObject() {
		return businessObject;
	}

	/**
	 * Pass the businessObject to be set
	 *
	 * @param businessObject the businessObject to set
	 */
	public void setBusinessObject(BusinessObject businessObject) {
		this.businessObject = businessObject;
	}

}
