/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import org.apache.commons.codec.EncoderException;
import org.json.JSONException;
import org.springframework.web.client.RestClientException;

import com.essms.admin.entities.Investor;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author gaurav
 *
 */
public interface UserManagement {

	/**
	 *
	 * @param username
	 * @param emailId
	 * @param mobileNo
	 * @return
	 * @throws JsonProcessingException
	 * @throws RestClientException
	 * @throws EncoderException
	 * @throws JSONException 
	 */
	RegisterUserModel getUser(String username, String emailId, String mobileNo)
			throws JsonProcessingException, RestClientException, EncoderException, JSONException;

	/**
	 *
	 * @param investor
	 * @param password
	 */
	void publishUser(Investor investor, String password);

}
