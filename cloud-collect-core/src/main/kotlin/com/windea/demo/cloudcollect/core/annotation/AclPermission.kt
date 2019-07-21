package com.windea.demo.cloudcollect.core.annotation

/**
 * 添加访问权限。针对新创建的实体。
 *
 * 用于Spring Security Acl（访问控制列表）。
 */
@Deprecated("使用基于属性的许可控制。")
@Target(AnnotationTarget.FUNCTION)
annotation class AclPermission(
	vararg val value: String = []
)

//确定为创建方法： @annotation(postMapping) & arg(principal)
//targetId - 从返回值domain.id获得
//targetDomainType - 从返回值domain.class获得
//principalName - 从参数principal.name获得
//permission - 从注解的value属性获得。

// private void addPermission(Long targetId, String principalName, Permission permission) {
//    ObjectIdentity oid = new ObjectIdentityImpl(T.class, targetId);
//    Sid recipient = new PrincipalSid(principalName)
//    MutableAcl acl;
//    try {
//        acl = (MutableAcl) mutableAclService.readAclById(oid);
//    } catch (NotFoundException nfe) {
//        acl = mutableAclService.createAcl(oid);
//    }
//    acl.insertAce(acl.getEntries().size(), permission, recipient, true);
//    mutableAclService.updateAcl(acl);
// }
