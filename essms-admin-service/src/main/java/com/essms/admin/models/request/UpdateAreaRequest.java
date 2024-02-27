/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import javax.validation.constraints.NotNull;

import com.essms.hibernate.core.models.UpdateEntity;

/**
 * @author gaurav
 *
 */
public class UpdateAreaRequest implements UpdateEntity {

	@NotNull
	private Long id;

	private String name;

	private Long cityId;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Will Return the cityId
	 *
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * Pass the cityId to be set
	 *
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

}
