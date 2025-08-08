/*
 * Created on Aug 7, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 * 
 * $Header$  
 */
package gov.mass.dcf.batch.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


/**
 * TODO: DOCUMENT ME!!
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@SpringBatchTest
@SpringJUnitConfig({BaseTestConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
public class SampleJobTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    //@MockBean
    //private SampleTasklet2 sampleTasklet2;

    @Autowired
    private Job sampleJob;

    @Test
    void testJob() throws Exception {
        // Given
        jobLauncherTestUtils.setJob(sampleJob);
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        
        while(jobExecution.isRunning()) {
            Thread.sleep(100);
        }

        // Then
        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }
}
