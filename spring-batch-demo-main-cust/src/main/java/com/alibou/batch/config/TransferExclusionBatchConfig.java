package com.alibou.batch.config;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.processor.TransferExclusionProcessor;
import com.alibou.batch.reader.TransferExclusionReader;
import com.alibou.batch.writer.TransferExclusionWriter;
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
public class TransferExclusionBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final TransferExclusionReader transferExclusionReader;
    private final TransferExclusionProcessor transferExclusionProcessor;
    private final TransferExclusionWriter transferExclusionWriter;

    @Bean
    public Step transferExclusionStep() {
        return new StepBuilder("transferExclusionStep", jobRepository)
                .<CustIndicator, CustIndicator>chunk(10, platformTransactionManager)
                .reader(transferExclusionReader.transferExclusionFileReader())
                .processor(transferExclusionProcessor)
                .writer(transferExclusionWriter.transferExclusionFileWriter())
                .build();
    }

    @Bean
    public Job transferExclusionJob() {
        return new JobBuilder("transferExclusionJob", jobRepository)
                .start(transferExclusionStep())
                .build();
    }
}
