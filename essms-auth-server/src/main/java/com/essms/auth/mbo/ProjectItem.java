/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.mbo;

import java.util.ArrayList;
import java.util.List;

import com.essms.auth.enums.ProjectStage;

/**
 * @author gaurav
 *
 */
public class ProjectItem {

	private String id;

	private String title;

	private String description;

	private ProjectStage stage;

	private Integer index;

	private String parentId;

	private List<ProjectItem> children;

	private List<AccessControl> accessControl = new ArrayList<>();

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the stage
	 * 
	 * @return the stage
	 */
	public ProjectStage getStage() {
		return stage;
	}

	/**
	 * Sets the stage
	 * 
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(ProjectStage stage) {
		this.stage = stage;
	}

	/**
	 * Returns the index
	 * 
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Sets the index
	 * 
	 * @param index
	 *            the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * Returns the parentId
	 * 
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the parentId
	 * 
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Returns the number of immediate children of this ProjectItem.
	 * 
	 * @return the number of immediate children.
	 */
	public int getNumberOfChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	/**
	 * Returns the children
	 * 
	 * @return the children
	 */
	public List<ProjectItem> getChildren() {
		return children;
	}

	/**
	 * Adds a child to the list of children for this ProjectItem. The addition of
	 * the first child will create a new List<ProjectItem>.
	 * 
	 * @param child
	 *            a ProjectItem object to set.
	 */
	public void addChild(ProjectItem child) {
		if (children == null) {
			children = new ArrayList<ProjectItem>();
		}
		children.add(child);
	}

	/**
	 * Inserts a ProjectItem at the specified position in the child list. Will *
	 * throw an ArrayIndexOutOfBoundsException if the index does not exist.
	 * 
	 * @param index
	 *            the position to insert at.
	 * @param child
	 *            the ProjectItem object to insert.
	 * @throws IndexOutOfBoundsException
	 *             if thrown.
	 */
	public void insertChildAt(int index, ProjectItem child) throws IndexOutOfBoundsException {
		if (index == getNumberOfChildren()) {
			// this is really an append
			addChild(child);
			return;
		} else {
			children.get(index); // just to throw the exception, and stop here
			children.add(index, child);
		}
	}

	/**
	 * Remove the ProjectItem element at index index of the List<ProjectItem>.
	 * 
	 * @param index
	 *            the index of the element to delete.
	 * @throws IndexOutOfBoundsException
	 *             if thrown.
	 */
	public void removeChildAt(int index) throws IndexOutOfBoundsException {
		children.remove(index);
	}

	/**
	 * Returns the accessControl
	 * 
	 * @return the accessControl
	 */
	public List<AccessControl> getAccessControl() {
		return accessControl;
	}

	/**
	 * Sets the accessControl
	 * 
	 * @param accessControl
	 *            the accessControl to set
	 */
	public void setAccessControl(List<AccessControl> accessControl) {
		this.accessControl = accessControl;
	}

	/**
	 * Sets the children
	 * 
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<ProjectItem> children) {
		this.children = children;
	}

}
