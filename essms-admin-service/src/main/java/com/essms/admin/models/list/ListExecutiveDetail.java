/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.list;

import com.essms.hibernate.core.models.ListEntity;

/**
 * @author gaurav
 *
 */
public class ListExecutiveDetail implements ListEntity {

	private String name;

	private String dateOfTravel;

	private Object map;

	private Object bill;

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
	 * @return the dateOfTravel
	 */
	public String getDateOfTravel() {
		return dateOfTravel;
	}

	/**
	 * @param dateOfTravel the dateOfTravel to set
	 */
	public void setDateOfTravel(String dateOfTravel) {
		this.dateOfTravel = dateOfTravel;
	}

	/**
	 * @return the map
	 */
	public Object getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Object map) {
		this.map = map;
	}

	/**
	 * @return the bill
	 */
	public Object getBill() {
		return bill;
	}

	/**
	 * @param bill the bill to set
	 */
	public void setBill(Object bill) {
		this.bill = bill;
	}

}
