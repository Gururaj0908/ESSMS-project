/**
 *
 */
package com.essms.auth.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.essms.hibernate.core.entities.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class RolePermission extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable = false)
	private Role role;

	@ManyToOne
	@JoinColumn(nullable = false)
	private BusinessObject businessObject;

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the businessObject
	 */
	public BusinessObject getBusinessObject() {
		return businessObject;
	}

	/**
	 * @param businessObject
	 *            the businessObject to set
	 */
	public void setBusinessObject(BusinessObject businessObject) {
		this.businessObject = businessObject;
	}

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
	public void setId(Long id) {
		this.id = id;
	}

}
