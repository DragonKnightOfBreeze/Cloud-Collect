package com.windea.demo.cloudcollect.core.configuration

import com.windea.demo.cloudcollect.core.properties.*
import org.springframework.context.annotation.*
import org.springframework.security.authentication.*
import org.springframework.security.crypto.password.*
import org.springframework.security.oauth2.config.annotation.configurers.*
import org.springframework.security.oauth2.config.annotation.web.configuration.*
import org.springframework.security.oauth2.config.annotation.web.configurers.*
import org.springframework.security.oauth2.provider.token.*
import org.springframework.security.oauth2.provider.token.store.*

/**安全校验服务器的配置。*/
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(
	private val tokenStore: TokenStore,
	private val accessTokenConverter: JwtAccessTokenConverter,
	private val authenticationManager: AuthenticationManager,
	private val passwordEncoder: PasswordEncoder,
	private val jwtProperties: SecurityProperties
) : AuthorizationServerConfigurerAdapter() {
	override fun configure(configurer: ClientDetailsServiceConfigurer) {
		configurer.inMemory()
			.withClient(jwtProperties.clientId)
			.secret(passwordEncoder.encode(jwtProperties.secret))
			.authorizedGrantTypes(jwtProperties.grantType)
			.resourceIds(jwtProperties.resourceId)
	}
	
	override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
		val enhancerChain = TokenEnhancerChain()
		enhancerChain.setTokenEnhancers(listOf<TokenEnhancer>(accessTokenConverter))
		endpoints.tokenStore(tokenStore)
			.accessTokenConverter(accessTokenConverter)
			.tokenEnhancer(TokenEnhancerChain())
			.authenticationManager(authenticationManager)
	}
}
