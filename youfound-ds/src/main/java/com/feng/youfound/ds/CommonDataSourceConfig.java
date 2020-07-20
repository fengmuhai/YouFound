package com.feng.youfound.ds;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * @author fengmuhai
 * @date 2020/7/20
 */
@Configuration
@MapperScan(basePackages = "com.feng.youfound.**.dao")
public class CommonDataSourceConfig {

    public static final int defaultStatementTimeout = 3000;
    public static final String commonDataSource = "dataSource";
    public static final String mapperBasePackage = "com.feng.youfound.web.dao";
    public static final String commonSqlSessionFactory = "commonSqlSessionFactory";
    public static final String commonTransactionManager = "commonTransactionManager";
    public static final String commonSqlSessionTemplate = "commonSqlSessionTemplate";
    public static final String commonMapperScannerConfigurer = "commonMapperScannerConfigurer";

    @Primary
    @Bean(name = commonDataSource)
    @ConfigurationProperties(prefix = "spring.datasource.hikari.primary")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Primary
    @Bean(name = commonSqlSessionFactory)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(commonDataSource) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setDefaultStatementTimeout(defaultStatementTimeout);
        configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.FAILING);
        // 开启下划线自动转驼峰
        configuration.setMapUnderscoreToCamelCase(true);
        // 该插件是系统自带监控SQL执行时长的
        // configuration.addInterceptor(new CatInterceptor());
        sqlSessionFactoryBean.setConfiguration(configuration);
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }

    @Primary
    @Bean(name = commonTransactionManager)
    public TransactionManager transactionManager(@Qualifier(commonDataSource) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = commonSqlSessionTemplate)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(commonSqlSessionFactory) SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = commonMapperScannerConfigurer)
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(commonSqlSessionFactory);
        mapperScannerConfigurer.setSqlSessionTemplateBeanName(commonSqlSessionTemplate);
        mapperScannerConfigurer.setAnnotationClass(Mapper.class);
        mapperScannerConfigurer.setBasePackage(mapperBasePackage);
        return mapperScannerConfigurer;
    }
}
