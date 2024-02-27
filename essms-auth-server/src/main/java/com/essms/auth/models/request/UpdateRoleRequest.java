/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.essms.hibernate.core.models.SystemDefined;
import com.essms.hibernate.core.models.UpdateEntity;

/**
 * @author gaurav
 *
 */
public class UpdateRoleRequest extends SystemDefined implements UpdateEntity {

	@NotNull
	private Long id;

	@NotEmpty
	private String name;

	private Boolean isSelectable;

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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the isSelectable
	 */
	public Boolean getIsSelectable() {
		return isSelectable;
	}

	/**
	 * @param isSelectable the isSelectable to set
	 */
	public void setIsSelectable(Boolean isSelectable) {
		this.isSelectable = isSelectable;
	}

}
