# 搁置事项

## 功能上

* ［扩展］收藏导入导出功能。
* ［扩展］多用户互动功能。
* ［可能］自由排序功能。
* ［可能］树状目录/分类功能。

## 结构上

* 整合Swagger。
* 整合Spring Security。
* 整合Elastic Search。
* ［可能］整合Docker。

# 待办事项

## domain

整合jpa、lombok、bean validation、jackson。 

### entity

* [X] `Collect` 
    * 属性：id、用户、名字、地址、logo地址、概述、分类、标签集合?、类型?、隐私权限、评论列表、点赞、创建时间、修改时间、删除状态
* [X] `CollectCategory` 
    * 属性：id、用户、名字、概述、创建时间、修改时间
* [X] `CollectTag`
    * 属性：id、用户、名字、概述、创建时间、修改时间
* [X] `Comment`
    * 属性：id、用户、发起用户、回复用户?、内容、创建时间
* [X] `Notice`
    * 属性：id、用户、类型、关联收藏?、关联回复?、创建时间、是否已读
* [X] `Follow`
    * 属性：id、用户、关注用户列表、被关注用户列表、创建时间、修改时间
* [X] `Praise`
    * 属性：id、收藏、点赞用户列表、创建时间、修改时间
* [X] `User`
    * 属性：id、用户名、邮箱地址、密码、昵称、头像地址、背景地址、个人简介、角色、关注、注册时间、更新时间，激活状态

### enums

* [X] `CollectMark`
    * 成员：重要、喜欢、待办、搁置
* [X] `CollectPrivacy`
    * 成员：私有、公开
* [X] `Role`
    * 成员：管理员、普通用户
* [X] `ResponseMessage`
    * 成员：成功/各种失败操作，带有状态码和消息文本属性

### request

* [X] `LoginView`
* [X] `UsernamePasswordLoginView`
    * 属性：用户名、密码
* [X] `EmailPasswordLoginView`
    * 属性：邮箱地址、密码
* [ ] ［搁置］`UserDetailsView`
    * 用于安全验证，与用户实体类相分离。

### response    

* [ ] `CollectPraiseView`
* [ ] `UserFollowView`
* [ ] `UserPraiseView`
* [ ] ［搁置］`UserResponseView`

## repository

* [ ] `CollectRepository`
* [ ] `CollectCategoryRepository`
* [ ] `CollectTagRepository`
* [ ] `CommentRepository`
* [ ] `NoticeRepository`
* [ ] `UserRepository`
    
## service

* [ ] `CollectService`
* [ ] `CollectCategoryService`
* [ ] `CollectTagService`
* [ ] `CommentService`
* [ ] `NoticeService`
* [ ] `UserService`
* [ ] `QiniuService`
    * 方法：upload, download
* [ ] `EmailService`
    * 方法：send, sendHtml, sendMarkdown
* [ ] ［扩展］`ImportExportService`
    * 方法：fromXml, fromJson, from Yaml, toXml, toJson, toYaml 

## controller

* [ ] `IndexController`
* [ ] `AccountController`
* [ ] `CollectController`
* [ ] `CollectCategoryController`
* [ ] `CollectTagController`
* [ ] `CommentController`
* [ ] `NoticeController`
* [ ] `UserController`

## exception

* [X] `NotFoundException`
* [X] `NotImplementedException`
* [X] `ValidationException`
* [X] `GlobalExceptionHandler`

## security

（搁置）

## validation

（略）
