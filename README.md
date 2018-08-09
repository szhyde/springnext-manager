travis-ci status:[![Build Status](https://api.travis-ci.org/szhyde/springnext-manager.png?branch=master)](https://travis-ci.org/szhyde/springnext-manager/)

# SpringNext #
springnext是Spring Boot的J2EE后台工程模板
==========
相关主要技术：
## 1.WEB ##
MVC组件：thymeleaf+SpringMVC
UI组件：LayUI
## 2.Database ##
ORM组件：JPA 
数据源:HikariCP,Spring boot默认
数据库脚本管理:flywaydb
默认数据库：H2与mysql
## 3.cache ##
基于：spring-boot-starter-cache支持ehcache与redis
## 4.Utilizes ##
General： Apache Commons 与 google Guava
Logging:Slf4j 与 Logback
系统资源监控:spring-boot-starter-actuator
## 5.security ##
基于spring-boot-starter-security和thymeleaf-extras-springsecurity4进行权限验证
基于spring-boot-starter-validation进行参数验证