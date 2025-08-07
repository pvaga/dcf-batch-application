package gov.mass.dcf.batch.tasklet;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FirstTaskletTest {
	
    @Test
    void testExecute() throws Exception {
        FirstTasklet tasklet = new FirstTasklet();
        StepContribution contribution = mock(StepContribution.class);
        ChunkContext chunkContext = mock(ChunkContext.class);
        RepeatStatus status = tasklet.execute(contribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, status);
    }
    
}
