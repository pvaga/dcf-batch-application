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
 * Configuration class for the sample batch job.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@Configuration
public class SampleJobConfig extends BaseJobConfig {
	
	/**
	 * Constructor for SampleJobConfig.
	 *
	 * @param jobRepository       the job repository
	 * @param transactionManager  the transaction manager
	 * @param jvmShutdownListener the JVM shutdown listener
	 */
	public SampleJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, JvmShutdownListener jvmShutdownListener) {
		super(jobRepository, transactionManager, jvmShutdownListener);
	}
	
    /**
     * Defines the first step of the sample job.
     *
     * @param firstTasklet the tasklet for the first step
     * @return Step instance
     */
    @Bean
    public Step firstStep(FirstTasklet firstTasklet) {
        return createStep(BatchJobConfigConstants.FIRST_STEP, firstTasklet);
    }

    /**
     * Defines the second step of the sample job.
     *
     * @param secondTasklet the tasklet for the second step
     * @return Step instance
     */
    @Bean
    public Step secondStep(SecondTasklet secondTasklet) {
        return createStep(BatchJobConfigConstants.SECOND_STEP, secondTasklet);
    }

    /**
     * Defines the sample job with its steps.
     *
     * @param jobRepository the job repository
     * @param firstStep the first step
     * @param secondStep the second step
     * @return Job instance
     */
    @Bean
    public Job sampleJob(JobRepository jobRepository, Step firstStep, Step secondStep) {
        return createJob(BatchJobConfigConstants.SAMPLE_JOB, firstStep, secondStep);
    }

    /**
     * Defines the Quartz JobDetail for the sample job.
     *
     * @return JobDetail instance
     */
    @Bean
    public JobDetail sampleJobDetail() {
        return QuartzConfig.createJobDetail(BatchJobConfigConstants.SAMPLE_JOB, BatchJobLauncher.class);
    }

    /**
     * Defines the Quartz Trigger for the sample job.
     *
     * @param sampleJobDetail the JobDetail for the sample job
     * @return Trigger instance
     */
    @Bean
    public Trigger sampleJobTrigger(JobDetail sampleJobDetail) {
        return QuartzConfig.createTrigger(sampleJobDetail, BatchJobConfigConstants.SAMPLE_JOB_TRIGGER, BatchJobConfigConstants.SAMPLE_JOB_SCHEDULE); // Example: run every minute
    }
}