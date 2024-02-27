/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.models.request;

import com.essms.core.annotation.SkipTextTransformation;

/**
 * @author ekamra.nayak
 *
 */
public class CheckUserPermissionRequest {

	@SkipTextTransformation
	private String url;

	@SkipTextTransformation
	private String branchGUID;

	@SkipTextTransformation
	private String userGUID;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the userGUID
	 */
	public String getUserGUID() {
		return userGUID;
	}

	/**
	 * @param userGUID the userGUID to set
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}

	/**
	 * @return the branchGUID
	 */
	public String getBranchGUID() {
		return branchGUID;
	}

	/**
	 * @param branchGUID the branchGUID to set
	 */
	public void setBranchGUID(String branchGUID) {
		this.branchGUID = branchGUID;
	}

}
