/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.job.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Tasklet for the first step of the sample batch job.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@Slf4j
@Component
public class FirstTasklet implements Tasklet {

    /**
     * Executes the first tasklet step.
     *
     * @param contribution the step contribution
     * @param chunkContext the chunk context
     * @return RepeatStatus indicating completion
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("####First Tasklet executed");
        return RepeatStatus.FINISHED;
    }
}
