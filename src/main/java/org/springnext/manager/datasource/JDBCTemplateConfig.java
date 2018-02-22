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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JDBCTemplateConfig {

	//主数据源
    @Primary
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate userJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
