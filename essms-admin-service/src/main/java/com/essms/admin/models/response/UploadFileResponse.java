/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.models.response;

/**
 * @author gaurav
 *
 */
public class UploadFileResponse {

	private String path;

	/**
	 * Will Return the path
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Pass the path to be set
	 *
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
