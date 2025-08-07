/*
 * Created on Aug 6, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 * 
 * $Header$  
 */
package gov.mass.dcf.batch.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import gov.mass.dcf.batch.tasklet.FirstTasklet;
import gov.mass.dcf.batch.tasklet.SecondTasklet;
import gov.mass.dcf.batch.util.BatchJobNameConstants;

/**
 * TODO: DOCUMENT ME!!
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@Configuration
public class SampleJobConfig extends BaseJobConfig {
	
	
	public SampleJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, JvmShutdownListener jvmShutdownListener) {
		super(jobRepository, transactionManager, jvmShutdownListener);
	}
	
	// Tasklets for the job steps
    @Bean
    public Step firstStep(FirstTasklet firstTasklet) {
        return createStep(BatchJobNameConstants.FIRST_STEP, firstTasklet);
    }

    @Bean
    public Step secondStep(SecondTasklet secondTasklet) {
        return createStep(BatchJobNameConstants.SECOND_STEP, secondTasklet);
    }

    @Bean
    public Job sampleJob(JobRepository jobRepository, Step firstStep, Step secondStep) {
        return createJob(BatchJobNameConstants.SAMPLE_JOB, firstStep, secondStep);
    }
    
    //Quartz job and trigger configuration
    @Bean
    public JobDetail sampleJobDetail() {
        return QuartzConfig.createJobDetail(BatchJobNameConstants.SAMPLE_JOB, BatchJobLauncher.class);
    }

    @Bean
    public Trigger sampleJobTrigger(JobDetail sampleJobDetail) {
        return QuartzConfig.createTrigger(sampleJobDetail, BatchJobNameConstants.SAMPLE_JOB_TRIGGER, BatchJobNameConstants.SAMPLE_JOB_SCHEDULE); // Example: run every minute
    }
}