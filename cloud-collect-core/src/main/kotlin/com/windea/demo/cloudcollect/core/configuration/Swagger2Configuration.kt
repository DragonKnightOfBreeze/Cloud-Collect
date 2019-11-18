package com.windea.demo.cloudcollect.core.configuration

import org.springframework.context.annotation.*
import springfox.documentation.builders.*
import springfox.documentation.service.*
import springfox.documentation.spi.*
import springfox.documentation.spring.web.plugins.*
import springfox.documentation.swagger2.annotations.*

/**Swagger2的配置类。*/
//TODO 另外实现Swagger2的自动配置。
@Configuration
@EnableSwagger2
class Swagger2Configuration {
	@Bean
	fun createRestApi(): Docket = Docket(DocumentationType.SWAGGER_2)
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.windea.demo.cloudcollect.core.controller"))
		.paths(PathSelectors.any())
		.build()
	
	private fun apiInfo(): ApiInfo {
		return ApiInfoBuilder()
			.title("云收藏后台系统")
			.description("微风的龙骑士的云收藏项目的后台系统。")
			.contact(contact())
			.license("MIT License")
			.licenseUrl("https://github.com/DragonKnightOfBreeze/Cloud-Collect/blob/master/LICENSE")
			.version("1.0.0")
			.build()
	}
	
	
	private fun contact(): Contact {
		return Contact(
			"DragonKnightOfBreeze",
			"https://github.com/DragonKnightOfBreeze",
			"dk_breeze@qq.com"
		)
	}
	
	//DELAY 添加关于Spring Security的配置
}
