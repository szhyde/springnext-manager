package org.springnext.manager.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 生产环境配置文件
 * @author HyDe
 *
 */
@Profile("production")  
@Configuration  
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false)  
public class AppProductionConfig {
	@Bean  
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {  
	    return new PropertySourcesPlaceholderConfigurer();  
	}  
}
