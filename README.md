import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.batch.item.file.LineMapper;

@Configuration
public class KycIndicatorsReader {

    @Bean
    public FlatFileItemReader<KycIndicatorDTO> kycFileReader() {
        FlatFileItemReader<KycIndicatorDTO> itemReader = new FlatFileItemReader<>();
        
        // Update the file path to point to the new directory on the desktop
        itemReader.setResource(new FileSystemResource("C:/Users/user/Desktop/CsvReader/kyc_indicators.csv"));
        itemReader.setName("kycFileReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        
        return itemReader;
    }

    public LineMapper<KycIndicatorDTO> lineMapper() {
        DefaultLineMapper<KycIndicatorDTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("relId", "fKycStatus");

        BeanWrapperFieldSetMapper<KycIndicatorDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(KycIndicatorDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
