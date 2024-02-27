/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author gaurav
 *
 */
@RefreshScope
@Configuration
public class RestTemplateConfig {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		rt.getMessageConverters().add(new StringHttpMessageConverter());
		return rt;
	}
}
