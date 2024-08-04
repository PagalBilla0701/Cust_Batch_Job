package com.alibou.batch.writer;

import com.alibou.batch.entity.CustIndicator;
import com.alibou.batch.repo.CustIndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SensitiveCustomerWriter {

    private final CustIndicatorRepository custIndicatorRepository;

    @Bean
    public RepositoryItemWriter<CustIndicator> sensitiveCustomerFileWriter() {
        RepositoryItemWriter<CustIndicator> writer = new RepositoryItemWriter<>();
        writer.setRepository(custIndicatorRepository);
        writer.setMethodName("save");
        return writer;
    }
}
