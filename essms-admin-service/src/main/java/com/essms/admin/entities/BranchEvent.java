/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.essms.hibernate.core.models.BaseModel;

/**
 * @author gaurav
 *
 */

@Entity
@SuppressWarnings("serial")
public class BranchEvent extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long count;

	@ManyToOne(fetch = FetchType.LAZY)
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private SystemEvent systemEvent;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the systemEvent
	 */
	public SystemEvent getSystemEvent() {
		return systemEvent;
	}

	/**
	 * @param systemEvent the systemEvent to set
	 */
	public void setSystemEvent(SystemEvent systemEvent) {
		this.systemEvent = systemEvent;
	}

}
