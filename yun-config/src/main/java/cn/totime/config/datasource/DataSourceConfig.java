package cn.totime.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
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
    private static final String DATASOURCE_NAME = "dbDataSource";

    /**
     * 主数据源yml配置路径
     */
    private static final String BUSINESS_DATASOURCE_PREFIX = "spring.datasource.primary";

    /**
     * 备用数据源yml配置路径
     */
    private static final String STANDBY_DATASOURCE_PREFIX = "spring.datasource.standby";

    /**
     * 定时任务数据源yml配置路径
     */
    private static final String TASK_DATASOURCE_PREFIX = "spring.datasource.task";

    /**
     * <p>主数据源配置</p>
     *
     * @return DruidDataSource对象
     */
    @Primary
    @Bean(name = DATASOURCE_NAME)
    @ConfigurationProperties(prefix = BUSINESS_DATASOURCE_PREFIX)
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * <p>备用数据源配置</p>
     *
     * @return DruidDataSource对象
     */
    @Bean(name = "standbyDataSource")
    @ConfigurationProperties(prefix = STANDBY_DATASOURCE_PREFIX)
    public DataSource standbyDataSource() {
        return new DruidDataSource();
    }

    /**
     * <p>@QuartzDataSource注解则用于配置Quartz独立数据源</p>
     * <p>Quartz独立数据源配置</p>
     *
     * @return DruidDataSource对象
     */
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = TASK_DATASOURCE_PREFIX)
    public DataSource quartzDataSource() {
        return new DruidDataSource();
    }
}