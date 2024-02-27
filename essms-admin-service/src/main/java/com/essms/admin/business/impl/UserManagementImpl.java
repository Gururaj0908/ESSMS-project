/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.essms.admin.business.UserManagement;
import com.essms.admin.constants.APIConstant;
import com.essms.admin.entities.Investor;
import com.essms.admin.publishers.UserExchangePublisher;
import com.essms.core.constants.HeaderConstant;
import com.essms.core.enums.UserType;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.queue.SystemUserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gaurav
 *
 */
@Service
public class UserManagementImpl implements UserManagement {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserExchangePublisher userExchangePublisher;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.money.business.UserManagement#publishUser(com.ssms.money.entities.
	 * Investor, java.lang.String)
	 */
	@Override
	public void publishUser(Investor investor, String password) {
		SystemUserModel systemUserModel = new SystemUserModel();
		if (StringUtils.isNotBlank(password)) {
			systemUserModel.setPassword(password);
		}
		systemUserModel.setCreatedBy(investor.getCreatedBy());
		systemUserModel.setCreatedDate(investor.getCreatedDate());
		systemUserModel.setEmailId(investor.getEmailId());
		systemUserModel.setEnabled(investor.getIsActive());
		systemUserModel.setLastModifiedBy(investor.getLastModifiedBy());
		systemUserModel.setLastModifiedDate(investor.getLastModifiedDate());
		systemUserModel.setMobileNo(investor.getMobileNo());
		systemUserModel.setName(investor.getName());
		systemUserModel.setTenantId(UserInfoUtil.getTenantId());
		systemUserModel.setUserGUID(investor.getGuid());
		systemUserModel.setUsername(investor.getUsername());
		systemUserModel.setUserType(UserType.BACKOFFICE);
		userExchangePublisher.produceUser(systemUserModel);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.money.business.UserManagement#getUser(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public RegisterUserModel getUser(String username, String emailId, String mobileNo)
			throws JsonProcessingException, RestClientException, EncoderException, JSONException {
		// TODO Retry and implement fall back
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(HeaderConstant.TENANT_ID, UserInfoUtil.getTenantId());
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("||username", username);
		criterias.put("||emailId", emailId);
		criterias.put("||mobileNo", mobileNo);
		String jsonString = new ObjectMapper().writeValueAsString(criterias);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				APIConstant.LIST_USER + "?jsonQueryString={jsonString}", HttpMethod.GET, entity, String.class,
				jsonString);
		if (responseEntity.getBody().indexOf("error") > 1) {

		} else {
			JSONObject json = new JSONObject(responseEntity.getBody());
			Long recordCount = json.getLong("recordCount");
			if (recordCount > 0) {
				JSONArray jsonArray = json.getJSONArray("entityList");
				RegisterUserModel registerUserModel = new RegisterUserModel();
				registerUserModel.setName(jsonArray.getJSONObject(0).getString("name"));
				registerUserModel.setMobileNo(jsonArray.getJSONObject(0).getString("mobileNo"));
				registerUserModel.setEmailId(jsonArray.getJSONObject(0).getString("emailId"));
				registerUserModel.setUsername(jsonArray.getJSONObject(0).getString("username"));
				return registerUserModel;
			}
		}
		return null;
	}

}
