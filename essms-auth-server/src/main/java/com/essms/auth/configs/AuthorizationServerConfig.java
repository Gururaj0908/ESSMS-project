/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.essms.auth.security.CustomTokenEnhancer;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private final String scopeWrite = "write";

	@Value("${security.jwt.grant-type.password}")
	private String grantTypePassword;

	@Value("${security.jwt.grant-type.refresh_token}")
	private String grantTypeRefreshToken;

	@Value("${security.jwt.grant-type.authorization_code}")
	private String grantTypeAuthorizationCode;

	@Value("${security.jwt.grant-type.implicit}")
	private String grantTypeImplicit;

	@Value("${security.jwt.access.token.validity}")
	private Integer accessTokenValidity;

	@Value("${security.jwt.refresh.token.validity}")
	private Integer refreshTokenValidity;

	@Value("${security.jwt.signing-key}")
	private String jwtSigningKey;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Pass Resource Ids
		clients.inMemory().withClient(clientId).secret(clientSecret).resourceIds("foo").scopes(scopeRead, scopeWrite)
				.autoApprove(true)
				.authorizedGrantTypes(grantTypePassword, grantTypeRefreshToken, grantTypeAuthorizationCode,
						grantTypeImplicit)
				.accessTokenValiditySeconds(accessTokenValidity).refreshTokenValiditySeconds(refreshTokenValidity);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtTokenEnhancer()));
		endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtTokenEnhancer());
	}

	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(jwtSigningKey);
		return converter;
	}

	@Bean
	// Making this primary to avoid any accidental duplication with another token
	// service instance of the same name
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}
