package com.spw.authmg.configuration.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Description 数据源配置类
 * @Author spw
 * @Date 2021/03/28
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DatasourceConfig {
    @Autowired
    private DruidDataSourceProperties dataSourceProperties;

    /**
     * 主库 （写库）
     * @return
     */
    @Primary
    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource master() {
        return this.config();
    }

    /**
     * 从库 （读库）
     * @return
     */
    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource slave() {
        return this.config();
    }

    /**
     * mybatis配置
     * @param datasource
     * @return
     */
    @Bean("customSqlSessionFactory")
    public SqlSessionFactory customSqlSessionFactory(DynamicDatasource datasource) {
        log.info("mybatis config begin...");
        try {
            //配置数据源
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(datasource);
            //配置model路径
            factoryBean.setTypeAliasesPackage("com.spw.authmg.pojo");
            //扫描mapper文件路径
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            //下划线 -> 驼峰  & sql语句打印
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
            factoryBean.setConfiguration(configuration);
            return factoryBean.getObject();
        } catch (Exception e) {
            log.error("mybatis config error: {}", e);
        }
        return null;
    }

    /**
     * 注入数据源事务
     * @param datasource
     * @return
     */
    @Bean("customTransactionManger")
    public DataSourceTransactionManager customTransactionManger(DynamicDatasource datasource) {
        return new DataSourceTransactionManager(datasource);
    }


    /**
     * druid 参数配置
     * @return
     */
    private DruidDataSource config() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setInitialSize(dataSourceProperties.getInitialSize());
        dataSource.setMaxActive(dataSourceProperties.getMaxActive());
        dataSource.setMinIdle(dataSourceProperties.getMinIdle());
        dataSource.setMaxWait(dataSourceProperties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(dataSourceProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dataSourceProperties.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(dataSourceProperties.getValidationQuery());
        return dataSource;
    }
}
