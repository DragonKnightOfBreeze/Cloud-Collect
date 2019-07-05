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
    * 属性：id、用户、名字、地址、logo地址、概述、分类、标签集合?、类型?、评论列表、点赞、创建时间、修改时间、删除状态
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

* [X] `CollectType`
    * 成员：无、重要、喜欢、待办、搁置
* [X] `NoticeType`
* [X] `Role`
    * 成员：普通用户、管理员

### request

* [X] `LoginView`
* [X] `UsernamePasswordLoginView`
    * 属性：用户名、密码
* [X] `EmailPasswordLoginView`
    * 属性：邮箱地址、密码
* [ ] ［搁置］`UserDetailsView`
    * 用于安全验证，与用户实体类相分离。

### response    

* [X] `CollectPraiseView`
* [X] `UserFollowView`
* [X] `UserPraiseView`
* [ ] ［搁置］`UserResponseView`

## repository

如果是实体类属性/实体类集合属性，则尽量根据实体类的id进行查询。  
（因为lombok的默认equals方法实现不仅基于id属性。）

* [X] `CollectRepository`
* [X] `CollectCategoryRepository`
* [X] `CollectTagRepository`
* [X] `CommentRepository`
* [X] `NoticeRepository`
* [X] `UserRepository`
    
## service

增加、删除、修改方法全部传入实体类，查询方法尽量传入实体类的id。  
类似“检查某一用户是否已存在”的功能，委托给自定义验证器。

* [X] `CollectService`
    * 创建自己的收藏。（如果是别人的收藏，默认点赞）
    * 删除自己的收藏。（修改`collect.deleted=false`，仍然保留在数据库中）
    * 修改自己的收藏的名字、概述和地址。
    * 修改自己的收藏的分类。
    * 修改自己的收藏的标签。
    * 修改自己的收藏的类型。
    * 展示某一用户的收藏。
    * 展示某一用户已删除的收藏。
    * 通过关键字查询某一用户的收藏。
    * 通过分类查询某一用户的收藏。
    * 通过标签查询某一用户的收藏。
    * 通过特殊标记查询某一用户的收藏。
    * 通过关键字查询所有收藏。
    * 查询某一用户点赞的所有收藏。
    * 点赞当前收藏。
    * ［扩展］创建或点赞收藏时通知好友。
* [X] `CollectCategoryService`
    * 创建自己的分类。
    * 删除自己的分类。（不删除对应的收藏）
    * 修改自己的分类的名字和概述。
    * 展示某一用户的所有分类。
* [X] `CollectTagService`
    * 创建自己的标签。（标签可在添加时自动创建）
    * 删除自己的标签。（不删除对应的收藏）
    * 修改自己的标签的名字和概述。
    * 展示某一用户的所有标签。
* [X] `CommentService`
    * 创建自己的评论。
    * 删除自己的评论。
    * 展示某一收藏下的所有评论。
    * 展示某一用户的所有评论。
    * 展示回复某一用户的所有评论。
    * ［扩展］创建评论时通知好友。
* [X] `NoticeService`
    * 阅读通知。
    * 展示自己的所有通知。
    * 展示自己的所有指定类型的通知。
    * 展示自己的所有已读通知。
* [X] `UserService`
    * 注册用户。
    * 激活用户。
    * 登录用户。
    * 注销用户。
    * 得到用户信息。
    * 更新用户信息。
    * 通过昵称查找用户。
    * 通过身份查找用户。
    * 得到该用户关注的用户。
    * 得到关注该用户的用户。
* [ ] `QiniuService`
    * 上传图片。
    * 下载图片。
* [ ] `EmailService`
    * 发送邮件。
* [ ] ［扩展］`ImportExportService`
    * 从xml/json/yaml文件导入收藏。
    * 导出收藏到xml/json/yaml文件。 

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
