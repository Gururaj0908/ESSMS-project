/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

/**
 * @author gaurav
 *
 */
@Configuration
public class LiquibaseConfig {

	@Autowired
	private DataSource dataSource;

	@Value("${common.liquibase.change-log}")
	private String changeLog;

	@Value("${bms.liquibase.change-log}")
	private String bmsChangeLog;

	@Bean
	@ConfigurationProperties(prefix = "default.liquibase")
	public LiquibaseProperties defaultLiquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean
	public SpringLiquibase defaultLiquibase() {
		return springLiquibase(defaultLiquibaseProperties(), changeLog);
	}

	@Bean
	@ConfigurationProperties(prefix = "horolab.liquibase")
	public LiquibaseProperties horolabLiquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean
	public SpringLiquibase horolabLiquibase() {
		return springLiquibase(horolabLiquibaseProperties(), changeLog);
	}

	@Bean
	@ConfigurationProperties(prefix = "bms.liquibase")
	public LiquibaseProperties bmsLiquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean
	public SpringLiquibase bmsLiquibase() {
		return springLiquibase(bmsLiquibaseProperties(), bmsChangeLog);
	}

	private SpringLiquibase springLiquibase(LiquibaseProperties properties, String changeLog) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(changeLog);
		liquibase.setContexts(properties.getContexts());
		liquibase.setDefaultSchema(properties.getDefaultSchema());
		liquibase.setDropFirst(properties.isDropFirst());
		liquibase.setShouldRun(properties.isEnabled());
		liquibase.setLabels(properties.getLabels());
		liquibase.setChangeLogParameters(properties.getParameters());
		liquibase.setRollbackFile(properties.getRollbackFile());
		return liquibase;
	}

}
