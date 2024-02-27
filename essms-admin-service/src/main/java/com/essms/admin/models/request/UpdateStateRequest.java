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
public class UpdateStateRequest implements UpdateEntity {

	@NotNull
	private Long id;

	private String code;

	private String tin;

	private String name;

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
	 * Returns the code
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code
	 * 
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the tin
	 * 
	 * @return the tin
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * Sets the tin
	 * 
	 * @param tin
	 *            the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
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

}
