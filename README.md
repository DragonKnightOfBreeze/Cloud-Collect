# 概述

毕业设计项目：云收藏的设计与实现。

# 参考

* Spring Boot
    * [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle)
	* [Spring Boot 中文导航](http://springboot.fun/)
	* [spring-boot - 纯洁的微笑博客](http://www.ityouknow.com/spring-boot.html)
    * [Spring Integration](https://docs.spring.io/spring-integration/docs/5.1.6.RELEASE/reference/html/#mail)
* Vue
    * [介绍 — Vue.js](https://cn.vuejs.org/v2/guide/)
    * [Vuex 是什么？ | Vuex](https://vuex.vuejs.org/zh/)
    * [介绍 | Vue Router](https://router.vuejs.org/zh/)
* Element
    * [Element - 网站快速成型工具](https://element.eleme.cn/#/zh-CN)

# 环境要求

* Idea
* Git
* MySql 5+
* ［可能］Nginx
* ［可能］Docker
* Jdk 11
* Kotlin Sdk
* Maven
* Tomcat
* Npm

# 技术要求

## 通用

* Markdown 用于编写项目文档，不直接编写doc
* Yaml 用于编写配置文件
* Git 用于版本管理
* MySql 数据库
* ［可能］Nginx 负载均衡
* ［可能］Docker 应用容器

# 前端

* Html & Css & Typescript
* Vue 前端核心框架
* Vue-router, Vuex 扩展Vue的功能
* Element 用于网页端页面和移动端页面
* Axios 用于发送http请求
* Npm 用于前端依赖和目录管理
	
## 后台

* Kotlin
* SpringBoot 核心框架
* Spring+Spring Mvc+Spring Data Jpa 核心框架
* Redis 用于缓存
* Swagger 用于编写api文档
* Maven 用于后台依赖和目录管理
* Spring Security 认证和授权框架
* ~~Lombok 便于实体类编写~~
* ~~［可能］Elastic Search 搜索引擎~~

# 主要功能

* 收藏网络上的各类链接，对其进行归类整理。
* 仅有一个（或者很少的）页面，但是功能较多，非常灵活。增强了一般浏览器的书签管理系统。

***

* 可以创建新的收藏。
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
* 用户可以自行选择页面主题。

***

* 其他尚未想到的，专注于收藏管理的功能。

# 规范

* Spring Bean定义一律使用Kotlin的表达式写法，且显示声明返回类型。
* 转化扩展可以使用Kotlin的表达式写法。 
* 创建实体时，尽量使用构造方法或者数据类的复制方法。
* 修改实体时，其合法性交给前端管理。
* 创建通知、浏览记录，发送邮件等操作交由前端主动调用。

# 注意事项

## 后台

* Spring Boot会对某些bean进行自动配置。例如，Converter、Formatter。
* 对于数据库：text类型不能指定默认值。java boolean类型会转换为bit类型。
* 当为作为主构造函数参数的实体类的属性注上validation注解时，需要添加`@field:`前缀。
* 使用`@Value`或`@ConfigurationProperties`时，对应的属性必须是`lateinit var`或者可空类型/存在默认值。
* 使用`@ConfigurationProperties`时，对应的属性必须是公开的。
* 使用Jpa时，为了让懒加载如预期工作，实体类必须是`open class`，实体类属性必须是`open var`或`open var`，推荐存在无参构造。
* Jpa不建议与`data class`和只读属性一同工作。
* 使用Jpa时，作为id的实体类属性可为可空类型，可为不可空类型，不可空时的默认值可为-1或0。也可以为`val`。
* 使用kotlin编译器插件`spring`时，spring bean可以不显示声明为`open class`。
* 使用kotlin编译器插件`jpa`时，实体类可以不显示声明无参构造函数。
* `@Valid`和`@Validated`的区别：[CSDN](https://blog.csdn.net/qq_27680317/article/details/79970590)
    * `@Valid`属于标准JSR-303规范，`@Validated`属于Spring's JSR-303规范。
    * `@Valid`放在实体类属性上，用于嵌套验证。
    * `@Validated`可以进行分组验证。
* 当自定义验证器存在依赖注入，且通过Jpa进行数据库进行操作时，需要为每个验证注解显式添加自定义的分组。
* 在实体类中，不要将Boolean属性写成`isRead`的形式。IDE认为幕后字段名为`read`，但是jpa仍然只识别`isRead`。
* 使用Maven指令`kotlin:kapt`为配置文件生成元数据（存在中文注释乱码问题）。
* 使用MockMvc进行测试：需要在测试之前启动项目。可以使用相对于context-path的url。
* 使用`spring-boot-starter-data-redis`时，可能需要添加依赖`commons-pool2`。
* 自定义参数验证器时，验证器需要提供无参构造函数。
* 在验证器中使用`@Autowired lateinit var`失败的问题：
    * Hibernate在将数据存储到数据库之前会再验证一次，使用自己的反射机制得到验证器。
    * 使用明确的验证分组，而非默认的Default，可以解决这个问题。
* 如何启用Spring的`@CreatedTime`等审计注解：[简书](https://www.jianshu.com/p/30aef87f3171)
    * 对应的属性可以是非空的`lateinit var`。 
* 验证码功能的几种实现：
	* 存储到http session中。
	* 存储到数据库的user表中。（不推荐，待重构）
	* 存储到redis缓存中。
* 关于noarg和allopen编译器插件
    * 对于Jpa实体类需要使用noarg和allopen插件，建议使用var属性。
    * 对于Spring bean需要使用allopen插件。
    * 对于json持久化也需要使用noarg插件，可以使用val属性。
	* 使用方法：配置插件，指定特定注解。为需要的类添加该注解，或该元注解（注有该注解的注解）。
	* 之后插件将会适用于所有注有该注解（或其元注解）的类/接口（或其子类/实现类）。
* 关于作为请求参数传递的Pageable对象
    * 示例：`"page=0&size=10&direction=asc&sort=name,age"`
* Jpa报错：`org.hibernate.AnnotationException: Collection has neither generic type or OneToMany.targetEntity() defined`
    * 当实体类中存在`MutableList<T>`或`MutableSet<T>`类型的属性时，将会报如上异常。
    * 可将其变成可空的`var`解决，可以为其添加`@JvmSuppressWildcards`注解解决。
