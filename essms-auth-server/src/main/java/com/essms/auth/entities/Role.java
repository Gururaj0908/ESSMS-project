/**
 *
 */
package com.essms.auth.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class Role extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	private Boolean isSelectable = true;

	@OneToMany(mappedBy = "role")
	private List<RolePermission> rolePermissions;

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
	 * @return the rolePermissions
	 */
	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	/**
	 * @param rolePermissions the rolePermissions to set
	 */
	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
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
