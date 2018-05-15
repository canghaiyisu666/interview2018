[博客地址](http://blog.didispace.com/Spring-Boot%E5%9F%BA%E7%A1%80%E6%95%99%E7%A8%8B/)

# 快速入门
* 基础项目构建，引入web模块，完成一个简单的RESTful API
* 使用Intellij中的Spring Initializr来快速构建Spring Boot/Cloud工程
* 配置文件详解：自定义属性、随机数、多环境配置等

# Web开发
* 构建一个较为复杂的RESTful API以及单元测试
* 使用Thymeleaf模板引擎渲染web视图
* 使用Freemarker模板引擎渲染web视图
* 使用Velocity模板引擎渲染web视图
* 使用Swagger2构建RESTful API
* 自制的spring-boot-starter-swagger
* 统一异常处理

# 安全管理
使用Spring Security
[使用Spring Session（未完成）]

# 数据访问
* 使用JdbcTemplate
* 使用Spring-data-jpa简化数据访问层（推荐）
* 多数据源配置（一）：JdbcTemplate
* 多数据源配置（二）：Spring-data-jpa
* 使用NoSQL数据库（一）：Redis
* 使用NoSQL数据库（二）：MongoDB
* 使用NoSQL数据库（三）：MongoDB的配置增强（连接池等）
* 整合MyBatis
* MyBatis注解配置详解
* 使用Flyway来管理数据库版本
* 使用LDAP来统一管理用户信息

# 事务管理
* 使用事务管理
* [分布式事务（未完成）]

# 缓存支持
* 注解配置与EhCache使用
* 使用Redis做集中式缓存

# 日志管理
* 默认日志的配置
* 使用log4j记录日志
* 对log4j进行多环境不同日志级别的控制
* 使用AOP统一处理Web请求日志
* 使用log4j记录日志到MongoDB
* Spring Boot 1.5.x新特性：动态修改日志级别]

# 消息服务
* [JMS（未完成）]
* Spring Boot中使用RabbitMQ
* [Spring Boot中使用（未完成）]

# 定时与异步任务
* 使用@Scheduled创建定时任务
* 使用@Async实现异步调用
* 使用@Async实现异步调用：自定义线程池
* 使用@Async实现异步调用：ThreadPoolTaskScheduler线程池的优雅关闭
* 使用@Async实现异步调用：使用Future以及定义超时

# 发送邮件
* 实现邮件发送：简单邮件、附件邮件、嵌入资源的邮件、模板邮件

# Spring Boot中使用Dubbo
* Spring Boot中使用Dubbo进行服务治理
* Spring Boot与Dubbo中管理服务依赖

# 监控管理
* Spring Boot Actuator监控端点小结
* 在传统Spring应用中使用spring-boot-actuator模块提供监控端点
* Spring Boot中使用Actuator的/info端点输出Git版本信息
 
# 其他功能
* Spring Boot自定义Banner
* 使用Spring StateMachine框架实现状态机
* Spring Boot应用的后台运行配置
* Spring Boot自动化配置的利弊及解决之道