package com.alibou.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.jpa.show-sql}")
    private boolean showSql;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private boolean formatSql;

    @Value("${spring.jpa.database-platform}")
    private String dialect;

    @Primary
    @Bean
    public DataSource dataSource() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(url);
        dataSourceProperties.setUsername(username);
        dataSourceProperties.setPassword(password);
        dataSourceProperties.setDriverClassName(driverClassName);
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("com.alibou.batch.entity");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        jpaProperties.setProperty("hibernate.show_sql", String.valueOf(showSql));
        jpaProperties.setProperty("hibernate.format_sql", String.valueOf(formatSql));
        jpaProperties.setProperty("hibernate.dialect", dialect);
        entityManagerFactory.setJpaProperties(jpaProperties);

        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
package com.alibou.batch.config;

import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceConfig.class)
public class BatchInfraConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean("jobRepository")
    public JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager());
        factory.setTablePrefix("COPS_BATCH_");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean("jobLauncher")
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }


    @Bean(name = "batchTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }
}
