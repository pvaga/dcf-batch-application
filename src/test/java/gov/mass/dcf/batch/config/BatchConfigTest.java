package gov.mass.dcf.batch.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import gov.mass.dcf.batch.tasklet.FirstTasklet;
import gov.mass.dcf.batch.tasklet.SecondTasklet;

@SpringBatchTest
@SpringBootTest
class BatchConfigTest {
	
    @Autowired
    private FirstTasklet firstTasklet;
    
    @Autowired
    private SecondTasklet secondTasklet;

    @Test
    void testJobAndSteps() {
        Step firstStep = new StepBuilder("firstStep", null)
                .tasklet(firstTasklet, null)
                .build();
        
        Step secondStep = new StepBuilder("secondStep", null)
                .tasklet(secondTasklet, null)
                .build();
        
        Job job = new JobBuilder("sampleJob", null)
                .start(firstStep)
                .next(secondStep)
                .build();
        
        assertNotNull(job);
        
        assertEquals("sampleJob", job.getName());
    }
}
