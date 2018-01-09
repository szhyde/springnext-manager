package org.springnext.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 
 * 系统启动入口
 * 先以war包的方式启动，加载Servlet相关支持
 * @author hyde
 * @version 2017年9月15日
 * @see BootCloudPlusApplication
 * @since
 */
// SpringBoot 应用标识
@SpringBootApplication
public class BootCloudPlusApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<BootCloudPlusApplication> applicationClass = BootCloudPlusApplication.class;
}
