package com.alibou.batch.config;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.processor.KycIndicatorsProcessor;
import com.alibou.batch.reader.KycIndicatorsReader;
import com.alibou.batch.writer.KycIndicatorsWriter;
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
public class KycIndicatorsBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final KycIndicatorsReader kycIndicatorsReader;
    private final KycIndicatorsProcessor kycIndicatorsProcessor;
    private final KycIndicatorsWriter kycIndicatorsWriter;

    @Bean
    public Step kycStep() {
        return new StepBuilder("kycStep", jobRepository)
                .<CustIndicator, CustIndicator>chunk(10, platformTransactionManager)
                .reader(kycIndicatorsReader.kycFileReader())
                .processor(kycIndicatorsProcessor)
                .writer(kycIndicatorsWriter.kycFileWriter())
                .build();
    }

    @Bean
    public Job kycIndicatorsJob() {
        return new JobBuilder("kycIndicatorsJob", jobRepository)
                .start(kycStep())
                .build();
    }
}
