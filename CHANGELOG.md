# 0.x.x

## 0.0.x

* 0.0.1 创建项目目录，更新模型类（包括实体类、枚举、视图对象等），添加可空性注解和日期注解。
* 0.0.2 为实体类添加jpa注解，为其属性设置必要的默认值。
* 0.0.3 整合lombok，为实体类和视图对象添加必要的lombok注解。
* 0.0.4 为实体类和视图对象添加必要的validation注解，暂时搁置自定义注解、message属性以及messages.properties。
* 0.0.5 编写包含验证信息的messages.properties，并设置validation注解的message属性为`validation.xxx`，以后再添加`{}`。
* 0.0.6 添加自定义校验注解和自定义校验器，具体功能暂未实现。
* 0.0.7 实现自定义校验器。

## 0.1.x

* 0.1.0 迁移包`com.windea.demo.cloudcollect`到`com.windea.demo.cloudcollect.core`。
* 0.1.1 编写自定义异常以及全局异常处理器。
* 0.1.2 将id的类型改为Long。
* 0.1.3 创建repository、service、api层的类和接口，注入依赖。
* 0.1.4 修正实体类，为属性添加注释。
* 0.1.5 修正实体类，添加视图接口。
* 0.1.6 初步完成repository。
* 0.1.7 初步完成service接口。
* 0.1.8 初步实现service接口。
* 0.1.9 移除实体类中的count只读属性，添加持久层的count方法。
* 0.1.10 添加事务注解。
