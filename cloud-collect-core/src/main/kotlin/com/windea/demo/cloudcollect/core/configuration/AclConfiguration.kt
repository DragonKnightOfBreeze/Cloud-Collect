package com.windea.demo.cloudcollect.core.configuration

import org.springframework.cache.*
import org.springframework.context.annotation.*
import org.springframework.data.redis.cache.*
import org.springframework.security.acls.domain.*
import org.springframework.security.acls.jdbc.*
import org.springframework.security.acls.model.*
import org.springframework.security.core.authority.*
import javax.sql.*

/**
 * Spring Security Acl（访问控制列表）的配置类。
 *
 * 如此麻烦……
 */
@Deprecated("")
@Configuration
open class AclConfiguration(
	private val dataSource: DataSource,
	private val redisCacheManager: RedisCacheManager
) {
	@Bean
	open fun mutableAclService(): MutableAclService {
		return JdbcMutableAclService(dataSource, lookupStrategy(), aclCache())
	}
	
	@Bean
	open fun lookupStrategy(): LookupStrategy {
		return BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), grantingStrategy())
	}
	
	@Bean
	open fun aclCache(): AclCache {
		return SpringCacheBasedAclCache(cache()!!, grantingStrategy(), aclAuthorizationStrategy())
	}
	
	@Bean
	open fun cache(): Cache? {
		return redisCacheManager.getCache("acl")
	}
	
	@Bean
	open fun aclAuthorizationStrategy(): AclAuthorizationStrategy {
		val authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "gaModifyAuditing", "gaGeneralChanges")
		return AclAuthorizationStrategyImpl(*authorities.toTypedArray())
	}
	
	@Bean
	open fun grantingStrategy(): PermissionGrantingStrategy {
		return DefaultPermissionGrantingStrategy(auditLogger())
	}
	
	@Bean
	open fun auditLogger(): AuditLogger {
		return ConsoleAuditLogger()
	}
}
