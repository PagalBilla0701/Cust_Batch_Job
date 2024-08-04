package com.alibou.batch.reader;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.mapper.SensitiveCustomerLineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class SensitiveCustomerReader {

    private final SensitiveCustomerLineMapper sensitiveCustomerLineMapper;

    @Bean
    public FlatFileItemReader<CustIndicator> sensitiveCustomerFileReader() {
        FlatFileItemReader<CustIndicator> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/sensitive_customer.csv"));
        itemReader.setName("sensitiveCustomerFileReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(sensitiveCustomerLineMapper.lineMapper());
        return itemReader;
    }
}
