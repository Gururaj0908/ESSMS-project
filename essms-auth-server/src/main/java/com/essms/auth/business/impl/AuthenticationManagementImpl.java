/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.business.impl;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.essms.auth.business.AuthenticationManagement;
import com.essms.auth.handlers.CustomResponseErrorHandler;
import com.essms.auth.models.LoginError;
import com.essms.auth.models.LoginSuccess;
import com.essms.auth.models.request.LoginRequest;
import com.essms.auth.models.response.LoginResponse;
import com.essms.core.constants.HeaderConstant;
import com.essms.core.exception.ApplicationException;
import com.essms.core.exception.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gaurav
 *
 */
@Service
public class AuthenticationManagementImpl implements AuthenticationManagement {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${server.port}")
	private String serverPort;

	// @Value("${server.address}")
	// private String serverAddress;

	@Value("${server.ssl.enabled}")
	private Boolean sslEnabled;

	private static final String LOGIN_URI = "/oauth/token";

	@Autowired
	private CustomResponseErrorHandler customResponseErrorHandler;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.auth.business.AuthenticationManagement#login(com.ssms.auth.models.
	 * request.LoginRequest, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String tenantId = request.getHeader(HeaderConstant.TENANT_ID);
		if (StringUtils.isBlank(tenantId)) {
			throw new ApplicationException(ExceptionMessage.MISSING_HEADER_TENANTID);
		}
		String plainCreds = clientId + ":" + clientSecret;
		byte[] plainCredsBytes = plainCreds.getBytes();
		String base64Creds = Base64.getEncoder().encodeToString(plainCredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.add(HeaderConstant.TENANT_ID, tenantId);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", loginRequest.getUsername());
		map.add("password", loginRequest.getPassword());
		map.add("grant_type", "password");
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setOutputStreaming(false);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.setErrorHandler(customResponseErrorHandler);
		LoginResponse loginResponse = new LoginResponse();
		String protocol = "http";
		if (sslEnabled) {
			protocol = "https";
		}
		ResponseEntity<String> responseEntity = restTemplate
				.postForEntity(protocol + "://localhost:" + serverPort + LOGIN_URI, httpEntity, String.class);
		if (responseEntity.getBody().indexOf("error") > 1) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			loginResponse.setLoginError(new ObjectMapper().readValue(responseEntity.getBody(), LoginError.class));
			return loginResponse;
		}
		loginResponse.setLoginSuccess(new ObjectMapper().readValue(responseEntity.getBody(), LoginSuccess.class));
		return loginResponse;
	}

	@Override
	public LoginResponse refreshAccessToken(String refreshToken, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String tenantId = request.getHeader(HeaderConstant.TENANT_ID);
		if (StringUtils.isBlank(tenantId)) {
			throw new ApplicationException(ExceptionMessage.MISSING_HEADER_TENANTID);
		}
		String plainCreds = clientId + ":" + clientSecret;
		byte[] plainCredsBytes = plainCreds.getBytes();
		String base64Creds = Base64.getEncoder().encodeToString(plainCredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.add(HeaderConstant.TENANT_ID, tenantId);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("refresh_token", refreshToken);
		map.add("grant_type", "refresh_token");
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setOutputStreaming(false);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.setErrorHandler(customResponseErrorHandler);
		LoginResponse loginResponse = new LoginResponse();
		String protocol = "http";
		if (sslEnabled) {
			protocol = "https";
		}
		ResponseEntity<String> responseEntity = restTemplate
				.postForEntity(protocol + "://localhost:" + serverPort + LOGIN_URI, httpEntity, String.class);
		if (responseEntity.getBody().indexOf("error") > 1) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			loginResponse.setLoginError(new ObjectMapper().readValue(responseEntity.getBody(), LoginError.class));
			return loginResponse;
		}
		loginResponse.setLoginSuccess(new ObjectMapper().readValue(responseEntity.getBody(), LoginSuccess.class));
		return loginResponse;
	}
}
