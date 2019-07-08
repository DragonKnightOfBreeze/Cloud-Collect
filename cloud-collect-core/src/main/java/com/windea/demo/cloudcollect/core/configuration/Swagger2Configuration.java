package com.windea.demo.cloudcollect.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2的配置类。
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.windea.demo.cloudcollect.core.controller"))
			.paths(PathSelectors.any())
			.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("云收藏后台系统")
			.description("微风的龙骑士的云收藏项目的后台系统。")
			.contact(contact())
			.license("MIT License")
			.licenseUrl("https://github.com/DragonKnightOfBreeze/Cloud-Collect/blob/master/LICENSE")
			.version("0.1.19")
			.build();
	}

	private Contact contact() {
		return new Contact(
			"DragonKnightOfBreeze",
			"https://github.com/DragonKnightOfBreeze",
			"dk_breeze@qq.com"
		);
	}

	//TODO 添加关于Spring Security的配置
}
