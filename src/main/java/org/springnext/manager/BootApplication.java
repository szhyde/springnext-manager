package org.springnext.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * 系统启动入口
 * 先以war包的方式启动，加载Servlet相关支持
 * @author hyde
 * @see BootApplication
 * @since
 */
// SpringBoot 应用标识
@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

}
