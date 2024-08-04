package com.alibou.batch.config;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.processor.SensitiveCustomerProcessor;
import com.alibou.batch.reader.SensitiveCustomerReader;
import com.alibou.batch.writer.SensitiveCustomerWriter;
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
public class SensitiveCustomerBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final SensitiveCustomerReader sensitiveCustomerReader;
    private final SensitiveCustomerProcessor sensitiveCustomerProcessor;
    private final SensitiveCustomerWriter sensitiveCustomerWriter;

    @Bean
    public Step sensitiveCustomerStep() {
        return new StepBuilder("sensitiveCustomerStep", jobRepository)
                .<CustIndicator, CustIndicator>chunk(10, platformTransactionManager)
                .reader(sensitiveCustomerReader.sensitiveCustomerFileReader())
                .processor(sensitiveCustomerProcessor)
                .writer(sensitiveCustomerWriter.sensitiveCustomerFileWriter())
                .build();
    }

    @Bean
    public Job sensitiveCustomerJob() {
        return new JobBuilder("sensitiveCustomerJob", jobRepository)
                .start(sensitiveCustomerStep())
                .build();
    }
}
