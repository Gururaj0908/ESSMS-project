/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class TenantThemeConfiguration extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 20, name = "primary_color")
	private String primary;

	@Column(length = 20, name = "accent_color")
	private String accent;

	@Column(length = 20, name = "warn_color")
	private String warn;

	@Column(length = 20, name = "primary_light_color")
	private String primaryLight;

	@Column(length = 20, name = "accent_light_color")
	private String accentLight;

	@Column(length = 20, name = "warn_light_color")
	private String warnLight;

	@Column(length = 20)
	@SkipTextTransformation
	private String fontFamily;

	@Column(length = 50)
	@SkipTextTransformation
	private String tenantId;

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
	 * @return the primary
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}

	/**
	 * @return the accent
	 */
	public String getAccent() {
		return accent;
	}

	/**
	 * @param accent the accent to set
	 */
	public void setAccent(String accent) {
		this.accent = accent;
	}

	/**
	 * @return the warn
	 */
	public String getWarn() {
		return warn;
	}

	/**
	 * @param warn the warn to set
	 */
	public void setWarn(String warn) {
		this.warn = warn;
	}

	/**
	 * @return the primaryLight
	 */
	public String getPrimaryLight() {
		return primaryLight;
	}

	/**
	 * @param primaryLight the primaryLight to set
	 */
	public void setPrimaryLight(String primaryLight) {
		this.primaryLight = primaryLight;
	}

	/**
	 * @return the accentLight
	 */
	public String getAccentLight() {
		return accentLight;
	}

	/**
	 * @param accentLight the accentLight to set
	 */
	public void setAccentLight(String accentLight) {
		this.accentLight = accentLight;
	}

	/**
	 * @return the warnLight
	 */
	public String getWarnLight() {
		return warnLight;
	}

	/**
	 * @param warnLight the warnLight to set
	 */
	public void setWarnLight(String warnLight) {
		this.warnLight = warnLight;
	}

	/**
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * @param fontFamily the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
