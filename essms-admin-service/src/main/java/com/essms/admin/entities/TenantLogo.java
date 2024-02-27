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

import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class TenantLogo extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String fileName;

	@Column(nullable = false, length = 50)
	private String fileType;

	@Column(nullable = false, length = 1000)
	private String logo;

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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Will Return the logo
	 *
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * Pass the logo to be set
	 *
	 * @param logo
	 *            the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * Will Return the fileName
	 *
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Pass the fileName to be set
	 *
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Will Return the fileType
	 *
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * Pass the fileType to be set
	 *
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
