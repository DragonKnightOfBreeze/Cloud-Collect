# 搁置

* 排序功能。
* 多用户互动功能。
* 树状目录功能。
* 整合Docker。
* 整合Swagger。

# 待办事项

* `domain` 整合jpa、lombok、bean validation、jackson。 
    * `entity`
        * [X] `Collect` id、标题、地址、logo地址、概述、分类、标签集合?、类型?、隐私权限、评论列表、用户、创建时间、修改时间、删除状态
        * [X] `CollectCategory` id、名字、概述、用户、创建时间、修改时间
        * [X] `CollectTag` id、名字、概述、用户、创建时间、修改时间
        * [X] `Comment` id、发起用户、回复用户?、内容、用户、创建时间
        * [X] `Notice` id、类型、关联收藏?、关联回复?、用户、创建时间、是否已读
        * [X] `Follow` id、用户、关注用户列表、被关注用户列表、创建时间、修改时间
        * [X] `Praise` id、收藏、被点赞用户列表、创建时间、修改时间
        * [X] `User` id、用户名、邮箱地址、密码、昵称、头像地址、背景地址、个人简介、角色、注册时间、更新时间，激活状态
        * [ ] ［搁置］`UserDetails` （用于安全验证，与用户实体类相分离）
    * `enums`
        * [X] `CollectMark` 重要、喜欢、待办、搁置
        * [X] `CollectPrivacy` 私有、公开
        * [X] `Role` 管理员、普通用户
        * [X] `ResponseMessage` 成功/各种失败操作，带有状态码和消息文本属性
    * `view`
        * [X] `UsernamePasswordLoginView` 用户名、密码
        * [X] `EmailPasswordLoginView` 邮箱地址、密码
        * [ ] ［待定］`CollectView`: Collect +是否被点赞、是否被评论、点赞数、评论数
        * [ ] ［待定］`UserView`: User +关注用户列表、被关注用户列表
        * [ ] ［搁置］`UserResponseView` 
* `repository`
    * [ ] `CollectRepository`
    * [ ] `CollectCategoryRepository`
    * [ ] `CollectTagRepository`
    * [ ] `CommentRepository`
    * [ ] `NoticeRepository`
    * [ ] `FollowRepository`
    * [ ] `PraiseRepository`
    * [ ] `UserRepository`
* `service`
    * [ ] `CollectService`
    * [ ] `CollectCategoryService`
    * [ ] `CollectTagService`
    * [ ] `CommentService`
    * [ ] `NoticeService`
    * [ ] `FollowService`
    * [ ] `PraiseService`
    * [ ] `UserService`
    * [ ] `QiniuService`
    * [ ] `EmailService`
* `api`
    * [ ] `IndexController`
    * [ ] `AccountController`
    * [ ] `CollectController`
    * [ ] `CollectCategoryController`
    * [ ] `CollectTagController`
    * [ ] `CommentController`
    * [ ] `NoticeController`
    * [ ] `UserController`
* `exception`
    * [X] `NotFoundException`
    * [X] `NotImplementedException`
    * [X] `ValidationException`
    * [X] `GlobalExceptionHandler`
* `security`
    * （搁置）
* `validation`
    * （略）
