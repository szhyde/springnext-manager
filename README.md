springnext
==========

一个Spirng的模板项目，原则:不重复做轮子

产品选型：
1.Web
MVC Framwork： SpringMVC
Layout Decoration： SiteMesh2
Javascript Library： JQuery
JavaScript/CSS Compressor: BUI
Validation: JS Validation:BUI,JAVA Validation:Hibernate Valiator

2.WebService
SOAP WebService:Apache CXF
Restful:CXF 与 SpringMVC

3.Database
ORM Framework：JPA 与 MyBatis,混用,MyBatis主要用于复杂查询，其他由JPA处理
NOSQL数据库:Redis
数据库连接池: Tomcat JDBC
Cache：JVM级：Ehcache，中央式缓存：Memcached

4.Services
Security Framework： Apache Shiro
JMS： ActiveMQ for spring jms封装
Schedule：Quartz
JMX：Jolokia

5.Utilizes
General： Apache Commons 与 google Guava
XML: JAXB
JSON:Jackson
Email:Spring for Email
Logging:Slf4j 与 Logback

6.Test
Unit Test： JUnit
Mock：Mockito
Functional Test：Selenium
