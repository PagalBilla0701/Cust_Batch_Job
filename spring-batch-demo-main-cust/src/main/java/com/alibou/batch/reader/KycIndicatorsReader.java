package com.alibou.batch.reader;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.mapper.KycIndicatorsLineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class KycIndicatorsReader {

    private final KycIndicatorsLineMapper kycIndicatorsLineMapper;

    @Bean
    public FlatFileItemReader<CustIndicator> kycFileReader() {
        FlatFileItemReader<CustIndicator> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/kyc_indicators.csv"));
        itemReader.setName("kycFileReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(kycIndicatorsLineMapper.lineMapper());
        return itemReader;
    }
}
