/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gaurav
 *
 */
@SuppressWarnings("serial")
public class BusinessObjectTree implements Serializable {

	private BusinessObjectModel rootElement;

	/**
	 * Default ctor.
	 */
	public BusinessObjectTree() {
		super();
	}

	/**
	 * Return the root Node of the tree.
	 * 
	 * @return the root element.
	 */
	public BusinessObjectModel getRootElement() {
		return this.rootElement;
	}

	/**
	 * Set the root Element for the tree.
	 * 
	 * @param rootElement
	 *            the root element to set.
	 */
	public void setRootElement(BusinessObjectModel rootElement) {
		this.rootElement = rootElement;
	}

	/**
	 * Returns the Tree<T> as a List of BusinessObjectModel objects. The elements of
	 * the List are generated from a pre-order traversal of the tree.
	 * 
	 * @return a List<BusinessObjectModel>.
	 */
	public List<BusinessObjectModel> toList() {
		List<BusinessObjectModel> list = new ArrayList<BusinessObjectModel>();
		walk(rootElement, list);
		return list;
	}

	/**
	 * Returns a String representation of the Tree. The elements are generated from
	 * a pre-order traversal of the Tree.
	 * 
	 * @return the String representation of the Tree.
	 */
	@Override
	public String toString() {
		return toList().toString();
	}

	/**
	 * Walks the Tree in pre-order style. This is a recursive method, and is called
	 * from the toList() method with the root element as the first argument. It
	 * appends to the second argument, which is passed by reference * as it recurses
	 * down the tree.
	 * 
	 * @param element
	 *            the starting element.
	 * @param list
	 *            the output of the walk.
	 */
	private void walk(BusinessObjectModel element, List<BusinessObjectModel> list) {
		list.add(element);
		for (BusinessObjectModel data : element.getChildren()) {
			walk(data, list);
		}
	}

}
