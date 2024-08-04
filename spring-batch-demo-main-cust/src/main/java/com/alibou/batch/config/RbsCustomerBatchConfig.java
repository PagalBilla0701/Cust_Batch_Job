package com.alibou.batch.config;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.processor.RbsCustomerProcessor;
import com.alibou.batch.reader.RbsCustomerReader;
import com.alibou.batch.writer.RbsCustomerWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class RbsCustomerBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final RbsCustomerReader rbsCustomerReader;
    private final RbsCustomerProcessor rbsCustomerProcessor;
    private final RbsCustomerWriter rbsCustomerWriter;

    @Bean
    public Step rbsCustomerStep() {
        return new StepBuilder("rbsCustomerStep", jobRepository)
                .<CustIndicator, CustIndicator>chunk(10, platformTransactionManager)
                .reader(rbsCustomerReader.rbsCustomerFileReader())
                .processor(rbsCustomerProcessor)
                .writer(rbsCustomerWriter.rbsCustomerFileWriter())
                .build();
    }

    @Bean
    public Job importRbsCustomerJob() {
        return new JobBuilder("importRbsCustomerJob", jobRepository)
                .start(rbsCustomerStep())
                .build();
    }
}
