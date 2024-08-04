package com.alibou.batch.reader;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.mapper.TransferExclusionLineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class TransferExclusionReader {

    private final TransferExclusionLineMapper transferExclusionLineMapper;

    @Bean
    public FlatFileItemReader<CustIndicator> transferExclusionFileReader() {
        FlatFileItemReader<CustIndicator> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/transfer_exclusion.csv"));
        itemReader.setName("transferExclusionFileReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(transferExclusionLineMapper.lineMapper());
        return itemReader;
    }
}
