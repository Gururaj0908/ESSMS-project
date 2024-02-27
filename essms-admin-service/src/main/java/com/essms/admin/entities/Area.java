/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.essms.core.annotation.AuditableFields;
import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class Area extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	@AuditableFields(fieldNames = { "name" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private City city;

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
	 * Will Return the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Pass the name to be set
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Will Return the city
	 *
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Pass the city to be set
	 *
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

}
