/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.essms.core.annotation.SkipTextTransformation;
import com.essms.hibernate.core.annotation.FormFieldProperty;
import com.essms.hibernate.core.enums.FormEditorType;

/**
 * @author gaurav
 *
 */
public class UpsertTenantThemeConfigRequest {

	@FormFieldProperty(label = "Id", placeHolder = "Id", formEditorType = FormEditorType.Number, hidden = true)
	private Long id;

	@NotEmpty
	@Length(max = 20)
	@FormFieldProperty(label = "Primary Color", placeHolder = "Primary Color", formEditorType = FormEditorType.Color)
	private String primary;

	@NotEmpty
	@Length(max = 20)
	@FormFieldProperty(label = "Accent Color", placeHolder = "Accent Color", formEditorType = FormEditorType.Color)
	private String accent;

	@NotEmpty
	@Length(max = 20)
	@FormFieldProperty(label = "Warn Color", placeHolder = "Warn Color", formEditorType = FormEditorType.Color)
	private String warn;

	@NotEmpty
	@Length(max = 20)
	@FormFieldProperty(label = "Primary Light Color", placeHolder = "Primary Light Color", formEditorType = FormEditorType.Color)
	private String primaryLight;

	@NotEmpty
	@Length(max = 20)
	@FormFieldProperty(label = "Accent Light Color", placeHolder = "Accent Light Color", formEditorType = FormEditorType.Color)
	private String accentLight;

	@NotEmpty
	@Length(max = 20)
	@FormFieldProperty(label = "Warn Light Color", placeHolder = "Warn Light Color", formEditorType = FormEditorType.Color)
	private String warnLight;

	@NotEmpty
	@Length(max = 20)
	@SkipTextTransformation
	@FormFieldProperty(label = "FontFamily", placeHolder = "FontFamily", formEditorType = FormEditorType.Text)
	private String fontFamily;

	/**
	 * @return the id
	 */
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

}
