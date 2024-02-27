/**
 *
 */
package com.essms.auth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.essms.auth.models.CustomUserDetails;
import com.essms.core.constants.HeaderConstant;

/**
 * @author gaurav
 *
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		additionalInfo.put(HeaderConstant.USER_GUID, customUserDetails.getUserGUID());
		additionalInfo.put(HeaderConstant.NAME, customUserDetails.getName());
		additionalInfo.put(HeaderConstant.USER_NAME, customUserDetails.getUsername());
		additionalInfo.put(HeaderConstant.EMAIL_ID, customUserDetails.getEmailId());
		additionalInfo.put(HeaderConstant.MOBILE_NO, customUserDetails.getMobileNo());
		additionalInfo.put(HeaderConstant.BRANCH_GUID, customUserDetails.getBranchGUID());
		additionalInfo.put(HeaderConstant.USER_TYPE, customUserDetails.getUserType());
		additionalInfo.put(HeaderConstant.ROLE_GUIDS, customUserDetails.getRoleGUIDs());
		additionalInfo.put(HeaderConstant.ALLOWED_BRANCH_GUIDS, customUserDetails.getAllowedBranchGUIDs());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}

}
