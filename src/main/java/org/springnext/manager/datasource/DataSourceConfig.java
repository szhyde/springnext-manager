/*
 * 文件名：DataSourceConfig.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：hyde
 * 修改时间：2017年9月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package org.springnext.manager.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 
 * 多数据源初始化
 * 用来初始化多个数据源，每个系统都要独立，因为聊了IM是的人员等与组织结构信息是通用，其他的都只查自己的库
 * @author hyde
 * @version 2017年9月14日
 * @see DataSourceConfig
 * @since
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "userDataSource")
    @Qualifier("userDataSource")
    @ConfigurationProperties(prefix="spring.datasource.user")
    public DataSource imDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "cloudDataSource")
    //主数据源
    @Primary
    @Qualifier("cloudDataSource")
    @ConfigurationProperties(prefix="spring.datasource.cloud")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

}
