package com.totime.mail.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author JanYork
 * @date 2022/12/29 19:23
 * @description 数据源配置类
 */
@Configuration
public class DataSourceConfig {
    /**
     * 配置主数据源配置节点路径
     */
    private static final String BUSINESS_DATASOURCE_PREFIX = "spring.datasource";
    /**
     * 配置定时任务数据源配置节点路径
     */
    private static final String QUARTZ_DATASOURCE_PREFIX = "spring.datasource.quartz";

//    /**
//     * <p>@Primary注解适用于多个同类Bean时，优先使用被注解的Bean</p>
//     * <p>系统主数据源的配置Bean</p>
//     *
//     * @return DruidDataSource数据源对象
//     */
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = BUSINESS_DATASOURCE_PREFIX)
//    public DruidDataSource businessDataSource() {
//        return new DruidDataSource();
//    }
//
//    /**
//     * <p>@QuartzDataSource注解是用于配置Quartz独立数据源的Bean</p>
//     * <p>Quartz独立数据源的配置Bean</p>
//     *
//     * @return DruidDataSource数据源对象
//     */
//    @Bean
//    @QuartzDataSource
//    @ConfigurationProperties(prefix = QUARTZ_DATASOURCE_PREFIX)
//    public DataSource quartzDataSource() {
//        return new DruidDataSource();
//    }

    @Primary
    @Bean(value = "dataSourceBusiness")
    @ConfigurationProperties("spring.datasource.druid.business")
    public DataSource dataSourceBusiness(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(value = "dataSourceQuartz")
    @QuartzDataSource
    @ConfigurationProperties("spring.datasource.druid.quartz")
    public DataSource dataSourceQuartz(){
        return DruidDataSourceBuilder.create().build();
    }
}