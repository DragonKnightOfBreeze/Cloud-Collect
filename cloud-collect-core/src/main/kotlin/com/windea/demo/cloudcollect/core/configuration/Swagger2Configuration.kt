package com.windea.demo.cloudcollect.core.configuration

import org.springframework.context.annotation.*
import springfox.documentation.builders.*
import springfox.documentation.service.*
import springfox.documentation.spi.*
import springfox.documentation.spring.web.plugins.*
import springfox.documentation.swagger2.annotations.*

/**Swagger2的配置类。*/
@Configuration
@EnableSwagger2
open class Swagger2Configuration {
	@Bean
	open fun createRestApi(): Docket {
		return Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.windea.demo.cloudcollect.core.controller"))
			.paths(PathSelectors.any())
			.build()
	}
	
	private fun apiInfo(): ApiInfo {
		return ApiInfoBuilder()
			.title("云收藏后台系统")
			.description("微风的龙骑士的云收藏项目的后台系统。")
			.contact(contact())
			.license("MIT License")
			.licenseUrl("https://github.com/DragonKnightOfBreeze/Cloud-Collect/blob/master/LICENSE")
			.version("0.2.3")
			.build()
	}
	
	
	private fun contact(): Contact {
		return Contact(
			"DragonKnightOfBreeze",
			"https://github.com/DragonKnightOfBreeze",
			"dk_breeze@qq.com"
		)
	}

	//TODO 添加关于Spring Security的配置
}
