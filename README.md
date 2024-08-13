import com.alibou.batch.reader.KycIndicatorsReader;
import com.alibou.batch.processor.KycIndicatorsProcessor;
import com.alibou.batch.writer.KycIndicatorsWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.transaction.support.ResourcelessTransactionManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KycIndicatorsBatchConfigTest {

    @Mock
    private KycIndicatorsReader mockKycIndicatorsReader;

    @Mock
    private KycIndicatorsProcessor mockKycIndicatorsProcessor;

    @Mock
    private KycIndicatorsWriter mockKycIndicatorsWriter;

    @InjectMocks
    private KycIndicatorsBatchConfig kycIndicatorsBatchConfigUnderTest;

    @Test
    void testKycStep() {
        // Setup
        final JobRepository jobRepository = mock(JobRepository.class);
        final ResourcelessTransactionManager transactionManager = new ResourcelessTransactionManager();

        // Configure mocks
        when(mockKycIndicatorsReader.kycFileReader()).thenReturn(new FlatFileItemReader<>());
        when(mockKycIndicatorsWriter.kycFileWriter()).thenReturn(new RepositoryItemWriter<>());

        // Run the test
        final Step result = kycIndicatorsBatchConfigUnderTest.kycStep(jobRepository, transactionManager);

        // Verify the results
        assertNotNull(result);
        verify(mockKycIndicatorsReader).kycFileReader();
        verify(mockKycIndicatorsWriter).kycFileWriter();
    }

    @Test
    void testKycIndicatorsJob() {
        // Setup
        final Step kycStep = mock(Step.class);
        final JobRepository jobRepository = mock(JobRepository.class);

        // Run the test
        final Job result = kycIndicatorsBatchConfigUnderTest.kycIndicatorsJob(kycStep, jobRepository);

        // Verify the results
        assertNotNull(result);
    }
}
