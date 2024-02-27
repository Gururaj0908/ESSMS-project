/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author gaurav
 *
 */

@SpringBootApplication
@ComponentScan(value = { "com.essms" })
@ServletComponentScan(value = { "com.essms" })
@EnableEurekaClient
@EnableJpaAuditing
@EnableAutoConfiguration(exclude = { LiquibaseAutoConfiguration.class })
public class ESSMSAdminService extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ESSMSAdminService.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ESSMSAdminService.class, args);
	}

}
