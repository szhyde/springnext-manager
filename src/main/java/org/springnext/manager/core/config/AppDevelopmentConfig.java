package org.springnext.manager.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 开发环境配置文件
 * @author HyDe
 *
 */
@Profile("development")  
@Configuration  
@PropertySource(value = "classpath:application-development.properties", ignoreResourceNotFound = false)  
public class AppDevelopmentConfig {
	@Bean  
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {  
	    return new PropertySourcesPlaceholderConfigurer();  
	}  
}
