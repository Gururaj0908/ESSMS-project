/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import com.essms.core.annotation.SkipTextTransformation;

/**
 * @author gaurav
 *
 */
public class RefreshTokenRequest {

	@SkipTextTransformation
	private String refresh_token;

	/**
	 * Returns the refresh_token
	 * 
	 * @return the refresh_token
	 */
	public String getRefresh_token() {
		return refresh_token;
	}

	/**
	 * Sets the refresh_token
	 * 
	 * @param refresh_token
	 *            the refresh_token to set
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

}
