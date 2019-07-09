package com.windea.demo.cloudcollect.core.configuration;

import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.*;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.sql.DataSource;

/**
 * Spring Security Acl（访问控制列表）的配置类。
 * <p>如此麻烦……
 */
@Deprecated
@Configuration
public class AclConfiguration {
	private final DataSource dataSource;
	private final RedisCacheManager redisCacheManager;

	public AclConfiguration(DataSource dataSource,
		RedisCacheManager redisCacheManager) {
		this.dataSource = dataSource;
		this.redisCacheManager = redisCacheManager;
	}

	@Bean
	public MutableAclService mutableAclService() {
		return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
	}

	@Bean
	public LookupStrategy lookupStrategy() {
		return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), grantingStrategy());
	}

	@Bean
	public AclCache aclCache() {
		return new SpringCacheBasedAclCache(cache(), grantingStrategy(), aclAuthorizationStrategy());
	}

	@Bean
	public Cache cache() {
		return redisCacheManager.getCache("acl");
	}

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		var authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "gaModifyAuditing", "gaGeneralChanges");
		return new AclAuthorizationStrategyImpl(authorities.toArray(new GrantedAuthority[0]));
	}

	@Bean
	public PermissionGrantingStrategy grantingStrategy() {
		return new DefaultPermissionGrantingStrategy(auditLogger());
	}

	@Bean
	public AuditLogger auditLogger() {
		return new ConsoleAuditLogger();
	}
}
