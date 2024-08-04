package com.alibou.batch.reader;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.mapper.RbsCustomerLineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class RbsCustomerReader {

    private final RbsCustomerLineMapper rbsCustomerLineMapper;

    @Bean
    public FlatFileItemReader<CustIndicator> rbsCustomerFileReader() {
        FlatFileItemReader<CustIndicator> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/rbs_customer.csv"));
        itemReader.setName("rbsCustomerFileReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(rbsCustomerLineMapper.lineMapper());
        return itemReader;
    }
}
