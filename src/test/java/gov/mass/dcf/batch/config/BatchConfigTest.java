package gov.mass.dcf.batch.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import gov.mass.dcf.batch.job.tasklet.FirstTasklet;
import gov.mass.dcf.batch.job.tasklet.SecondTasklet;

@SpringBatchTest
@SpringJUnitConfig({BaseTestConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
class BatchConfigTest {
	
    @Autowired
    private FirstTasklet firstTasklet;
    
    @Autowired
    private SecondTasklet secondTasklet;

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void testJobAndSteps() {
        Step firstStep = new StepBuilder("firstStep", jobRepository)
                .tasklet(firstTasklet, transactionManager)
                .build();

        Step secondStep = new StepBuilder("secondStep", jobRepository)
                .tasklet(secondTasklet, transactionManager)
                .build();

        Job job = new JobBuilder("sampleJob", jobRepository)
                .start(firstStep)
                .next(secondStep)
                .build();

        assertNotNull(job);
        assertEquals("sampleJob", job.getName());
    }
}
