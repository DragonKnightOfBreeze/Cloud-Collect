# 概述

毕业设计项目：云收藏的设计与实现。

# 参考

* Spring Boot
    * [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle)
	* [Spring Boot 中文导航](http://springboot.fun/)
	* [spring-boot - 纯洁的微笑博客](http://www.ityouknow.com/spring-boot.html)
* Spring Mail
    * [Spring Integration](https://docs.spring.io/spring-integration/docs/5.1.6.RELEASE/reference/html/#mail)
* Elastic Search
    * ……
* Vue
    * [Vuex 是什么？ | Vuex](https://vuex.vuejs.org/zh/)
* Weex
    * [WEEX](https://weex.apache.org/zh/)

# 主要功能

* 收藏网络上的各类链接，对其进行归类整理。
* 仅有一个（或者很少的）页面，但是功能较多，非常灵活。增强了一般浏览器的书签管理系统。

* 可以创建新的收藏。
* 可以为每个收藏设置简单的隐私权限。
* 可以为每个收藏进行分类。一个收藏只能有一个分类，由用户自定义。
* 可以为每个收藏添加标签。一个收藏可以有多个标签，由用户自定义。
* 可以为每个收藏添加特殊标记。如：待办，喜欢。由系统定义。
* 可以为每个收藏添加概述。
* 对于每个收藏，可以对其进行跳转、重命名、修改、移动、删除等操作。
* ［可能］可以同时对多个收藏进行重命名、修改外的相同的操作。
* 可以通过关键字、分类、标签、特殊标记查找收藏。
* 可以通过高级查询查找收藏。
* 可以基于多种方式复制收藏地址。例如：复制为html超链接、复制为Markdown超链接。
* ［扩展］可以基于多种方式导出收藏。例如：Xml、Json、Yaml。
* ［扩展］可以基于多种方式导入收藏。例如：Xml、Json、Yaml。
* ［可能］对于以上3个功能，用户可以自定义其他方式。
* ［可能］可以对收藏进行自由排序。
* ［扩展］可以对收藏进行评论。
* ［扩展］可以对收藏进行点赞。

* 可以创建新的分类。
* 对于每个分类，可以对其进行重命名、移动、删除等操作。删除时，可设置是否删除之中所有收藏，还是放回父级目录。
* ［可能］可以同时对对个标签进行除重命名之外的相同操作。

* 可以创建新的标签。
* 标签除了一个收藏可带有多个之外，其余与分类类似。
* 对于每个标签，可以对其进行重命名、移动、删除等操作。删除时对应收藏不删除。
* ［可能］可以同时对对个标签进行除重命名之外的相同操作。

* 拥有用户登录注册系统。
* 用户带有简单的个人资料，可以重置密码、修改个人资料（此时不能修改密码）。
* 用户可以查看他人的公开收藏。
* 用户可以关注其他用户。可以查看关注被被关注人数和用户信息。

* （其他尚未想到的，专注于收藏管理的功能）

# 技术要求

* 通用：
    * Markdown 用于编写项目文档，不直接编写doc
    * Yaml 用于编写配置文件
    * Git 用于版本管理
	* nginx 负载均衡
	* ［可能］Docker 应用容器
* 后台：
    * SpringBoot 核心框架
    * Spring+Spring Mvc+Spring Data Jpa 核心框架
    * Redis 用于缓存
    * Swagger 用于编写api文档
    * Lombok 便于实体类编写
    * Maven 用于后台依赖和目录管理
    * MySql 后台数据库
	* Spring Security 认证和授权框架
	* ［可能］Elastic Search 搜索引擎
* 前端：
    * Vue 前端核心框架
    * Vue-router, Vuex, ... 扩展Vue的功能
    * Weex 用于网页端页面和移动端页面
    * Npm 用于前端依赖和目录管理
