package gov.mass.dcf.batch.tasklet;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import gov.mass.dcf.batch.job.tasklet.SecondTasklet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBatchTest
@SpringJUnitConfig
class SecondTaskletTest {
	
    @Test
    void testExecute() throws Exception {
        SecondTasklet tasklet = new SecondTasklet();
        StepContribution contribution = mock(StepContribution.class);
        ChunkContext chunkContext = mock(ChunkContext.class);
        RepeatStatus status = tasklet.execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }
    
}
