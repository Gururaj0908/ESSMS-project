/**
 *
 */
package com.essms.auth.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.essms.hibernate.core.entities.AuditableEntity;

/**
 * @author gaurav
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "findByUserGUIDs", query = "from UserRole where systemUser.guid in :commaSeparatedUserGUIDs"),
		@NamedQuery(name = "findByRoleGUIDs", query = "from UserRole where role.guid in :commaSeparatedRoleGUIDs") })
@SuppressWarnings("serial")
public class UserRole extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private SystemUser systemUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Role role;

	/**
	 * Will Return the systemUser
	 *
	 * @return the systemUser
	 */
	public SystemUser getSystemUser() {
		return systemUser;
	}

	/**
	 * Pass the systemUser to be set
	 *
	 * @param systemUser the systemUser to set
	 */
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

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

}
