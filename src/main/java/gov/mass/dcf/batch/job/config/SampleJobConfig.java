/*
 * Created on Aug 6, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 * 
 * $Header$  
 */
package gov.mass.dcf.batch.job.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import gov.mass.dcf.batch.config.BatchJobLauncher;
import gov.mass.dcf.batch.config.JvmShutdownListener;
import gov.mass.dcf.batch.config.QuartzConfig;
import gov.mass.dcf.batch.job.tasklet.FirstTasklet;
import gov.mass.dcf.batch.job.tasklet.SecondTasklet;
import gov.mass.dcf.batch.job.util.BatchJobConfigConstants;

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
        return createStep(BatchJobConfigConstants.FIRST_STEP, firstTasklet);
    }

    @Bean
    public Step secondStep(SecondTasklet secondTasklet) {
        return createStep(BatchJobConfigConstants.SECOND_STEP, secondTasklet);
    }

    @Bean
    public Job sampleJob(JobRepository jobRepository, Step firstStep, Step secondStep) {
        return createJob(BatchJobConfigConstants.SAMPLE_JOB, firstStep, secondStep);
    }
    
    //Quartz job and trigger configuration
    @Bean
    public JobDetail sampleJobDetail() {
        return QuartzConfig.createJobDetail(BatchJobConfigConstants.SAMPLE_JOB, BatchJobLauncher.class);
    }

    @Bean
    public Trigger sampleJobTrigger(JobDetail sampleJobDetail) {
        return QuartzConfig.createTrigger(sampleJobDetail, BatchJobConfigConstants.SAMPLE_JOB_TRIGGER, BatchJobConfigConstants.SAMPLE_JOB_SCHEDULE); // Example: run every minute
    }
}