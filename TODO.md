# 前端待办事项

……

# 后台待办事项

## 结构

* [X] 整合Jpa。
* [X] 整合Lombok。
* [X] 整合Bean Validation。
* [X] 整合Jackson。 
* [X] 整合Swagger。
* [X] 整合Jwt和Spring Security。
* [ ] ［可能］整合Elastic Search。
* [ ] ［可能］整合Docker。

## 目录

* 组件类 component
    [X] 安全验证的相关组件。
    [X] 自定义字符串到分页、排序对象转换器。
    [X] 适用于BindingResult的切面（当控制层方法中含有bindingResult参数时，在有错时抛出参数验证异常）。
* 配置类 configuration
    [X] 安全验证的相关配置。
    [X] Swagger2的相关配置。
* 异常类 exception
    [X] 基于Http状态的必要的自定义异常。
* 参数验证 validation
    [X] 特殊属性的相关验证。
    [X] 唯一实体类的相关验证。
* 实体类 domain
    [X] 一般的实体类、枚举、模型、视图。
* 持久层 repository
    [X] 一般的数据库操作方法。
* 服务层 service
    [X] 一般的增删改查服务。
    [X] 拓展服务（导入导出、发送邮件等）。
* 控制层 controller
    [X] 一般的增删改查api。
    [X] Swagger2注解。
    [X] 安全验证和权限控制。

## 功能

* [X] 基本的增删改查功能。
* [X] 跨表查询功能。
* [X] 基本的登录注册功能。
* [X] 整合邮箱激活的登录注册功能。
* [X] 收藏的导入导出功能。
* [X] 多用户互动功能。
* [X] 上传用户图片到远程图库。
* [X] ［可能］评论功能。
* [X] ［可能］点赞功能。
* [ ] ［可能］浏览记录功能。
* [ ] ［可能］收藏的自由排序功能。
* [ ] ［可能］收藏的树状目录/分类功能。
