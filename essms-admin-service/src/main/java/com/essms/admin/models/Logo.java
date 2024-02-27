/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author gaurav
 *
 */
public class Logo {

	@NotEmpty
	private String fileName;

	@NotEmpty
	private String fileType;

	@NotEmpty
	private String base64EncodedLogo;

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

	/**
	 * Will Return the base64EncodedLogo
	 *
	 * @return the base64EncodedLogo
	 */
	public String getBase64EncodedLogo() {
		return base64EncodedLogo;
	}

	/**
	 * Pass the base64EncodedLogo to be set
	 *
	 * @param base64EncodedLogo
	 *            the base64EncodedLogo to set
	 */
	public void setBase64EncodedLogo(String base64EncodedLogo) {
		this.base64EncodedLogo = base64EncodedLogo;
	}

}
