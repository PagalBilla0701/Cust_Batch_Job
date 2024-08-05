@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setTablePrefix("COPS_BATCH_");
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        JobLauncherFactoryBean factory = new JobLauncherFactoryBean();
        factory.setJobRepository(jobRepository);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public JobExplorer jobExplorer(DataSource dataSource) throws Exception {
        JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTablePrefix("COPS_BATCH_");
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
