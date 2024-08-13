package com.alibou.batch.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchServiceTest {

    @Mock
    private JobLauncher mockJobLauncher;

    @Mock
    private Job kycIndicatorsJob;

    @Mock
    private Job transferExclusionJob;

    @Mock
    private Job sensitiveCustomerJob;

    @Mock
    private Job rbsCustomerJob;

    @InjectMocks
    private BatchService batchServiceUnderTest;

    @Test
    void testImportKycIndicators() throws Exception {
        batchServiceUnderTest.importKycIndicators();
        verify(mockJobLauncher).run(any(Job.class),
                eq(new JobParameters(Map.ofEntries(Map.entry("startAt", new JobParameter<>(null, Long.class)),
                        Map.entry("jobName", new JobParameter<>("KYC Indicators", String.class))))));
    }

    @Test
    void testImportKycIndicators_JobLauncherThrowsJobExecutionAlreadyRunningException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobExecutionAlreadyRunningException.class);
        batchServiceUnderTest.importKycIndicators();
    }

    @Test
    void testImportKycIndicators_JobLauncherThrowsJobRestartException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobRestartException.class);
        batchServiceUnderTest.importKycIndicators();
    }

    @Test
    void testImportKycIndicators_JobLauncherThrowsJobInstanceAlreadyCompleteException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobInstanceAlreadyCompleteException.class);
        batchServiceUnderTest.importKycIndicators();
    }

    @Test
    void testImportKycIndicators_JobLauncherThrowsJobParametersInvalidException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobParametersInvalidException.class);
        batchServiceUnderTest.importKycIndicators();
    }

    @Test
    void testImportTransferExclusion() throws Exception {
        batchServiceUnderTest.importTransferExclusion();
        verify(mockJobLauncher).run(any(Job.class),
                eq(new JobParameters(Map.ofEntries(Map.entry("startAt", new JobParameter<>(null, Long.class)),
                        Map.entry("jobName", new JobParameter<>("Transfer Exclusion", String.class))))));
    }

    @Test
    void testImportTransferExclusion_JobLauncherThrowsJobExecutionAlreadyRunningException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobExecutionAlreadyRunningException.class);
        batchServiceUnderTest.importTransferExclusion();
    }

    @Test
    void testImportTransferExclusion_JobLauncherThrowsJobRestartException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobRestartException.class);
        batchServiceUnderTest.importTransferExclusion();
    }

    @Test
    void testImportTransferExclusion_JobLauncherThrowsJobInstanceAlreadyCompleteException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobInstanceAlreadyCompleteException.class);
        batchServiceUnderTest.importTransferExclusion();
    }

    @Test
    void testImportTransferExclusion_JobLauncherThrowsJobParametersInvalidException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobParametersInvalidException.class);
        batchServiceUnderTest.importTransferExclusion();
    }

    @Test
    void testImportSensitiveCustomer() throws Exception {
        batchServiceUnderTest.importSensitiveCustomer();
        verify(mockJobLauncher).run(any(Job.class),
                eq(new JobParameters(Map.ofEntries(Map.entry("startAt", new JobParameter<>(null, Long.class)),
                        Map.entry("jobName", new JobParameter<>("Sensitive Customer", String.class))))));
    }

    @Test
    void testImportSensitiveCustomer_JobLauncherThrowsJobExecutionAlreadyRunningException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobExecutionAlreadyRunningException.class);
        batchServiceUnderTest.importSensitiveCustomer();
    }

    @Test
    void testImportSensitiveCustomer_JobLauncherThrowsJobRestartException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobRestartException.class);
        batchServiceUnderTest.importSensitiveCustomer();
    }

    @Test
    void testImportSensitiveCustomer_JobLauncherThrowsJobInstanceAlreadyCompleteException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobInstanceAlreadyCompleteException.class);
        batchServiceUnderTest.importSensitiveCustomer();
    }

    @Test
    void testImportSensitiveCustomer_JobLauncherThrowsJobParametersInvalidException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobParametersInvalidException.class);
        batchServiceUnderTest.importSensitiveCustomer();
    }

    @Test
    void testImportRbsCustomer() throws Exception {
        batchServiceUnderTest.importRbsCustomer();
        verify(mockJobLauncher).run(any(Job.class),
                eq(new JobParameters(Map.ofEntries(Map.entry("startAt", new JobParameter<>(null, Long.class)),
                        Map.entry("jobName", new JobParameter<>("RBS Customer", String.class))))));
    }

    @Test
    void testImportRbsCustomer_JobLauncherThrowsJobExecutionAlreadyRunningException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobExecutionAlreadyRunningException.class);
        batchServiceUnderTest.importRbsCustomer();
    }

    @Test
    void testImportRbsCustomer_JobLauncherThrowsJobRestartException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobRestartException.class);
        batchServiceUnderTest.importRbsCustomer();
    }

    @Test
    void testImportRbsCustomer_JobLauncherThrowsJobInstanceAlreadyCompleteException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobInstanceAlreadyCompleteException.class);
        batchServiceUnderTest.importRbsCustomer();
    }

    @Test
    void testImportRbsCustomer_JobLauncherThrowsJobParametersInvalidException() throws Exception {
        when(mockJobLauncher.run(any(Job.class), any(JobParameters.class)))
                .thenThrow(JobParametersInvalidException.class);
        batchServiceUnderTest.importRbsCustomer();
    }
}
