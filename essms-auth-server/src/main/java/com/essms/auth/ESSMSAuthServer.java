/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *
 * @author gaurav
 *
 */
@RefreshScope
@SpringBootApplication
@ComponentScan(value = { "com.essms" })
@ServletComponentScan(value = { "com.essms" })
@EnableJpaAuditing
@EnableEurekaClient
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = { LiquibaseAutoConfiguration.class })
public class ESSMSAuthServer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ESSMSAuthServer.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ESSMSAuthServer.class, args);
	}

}
