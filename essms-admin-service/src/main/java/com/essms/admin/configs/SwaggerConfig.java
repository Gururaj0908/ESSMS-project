/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.configs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.essms.core.constants.HeaderConstant;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author gaurav
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${default.tenant.id}")
	private String defaultTenantId;

	@Bean
	public Docket api() throws IOException, XmlPullParserException {
		// Adding Header
		List<Parameter> aParameters = new ArrayList<>();
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name(HeaderConstant.TENANT_ID).modelRef(new ModelRef("string")).parameterType("header")
				.required(true).defaultValue(defaultTenantId).build();
		aParameters.add(aParameterBuilder.build());
		aParameterBuilder.name(HeaderConstant.AUTHORIZATION).modelRef(new ModelRef("string")).parameterType("header")
				.required(false).defaultValue("bearer ").build();
		aParameters.add(aParameterBuilder.build());
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.essms")).build()
				.apiInfo(new ApiInfo("Admin Service Api Documentation", "Documentation automatically generated",
						"1.1.0", null, null, "licence", null, new ArrayList<>()))
				.globalOperationParameters(aParameters);
	}
}
