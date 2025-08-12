/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.job.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.transaction.PlatformTransactionManager;

import gov.mass.dcf.batch.config.JvmShutdownListener;

/**
 * Base configuration for Spring Batch jobs, providing common step and job creation logic.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
public abstract class BaseJobConfig {

	protected JobRepository jobRepository;
	
	protected PlatformTransactionManager transactionManager;
	
	protected JvmShutdownListener listener;
	
	/**
	 * Constructor for BaseJobConfig.
	 *
	 * @param jobRepository      the job repository
	 * @param transactionManager the transaction manager
	 * @param listener           the JVM shutdown listener
	 */
	public BaseJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, JvmShutdownListener listener) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.listener = listener;
		
		if (jobRepository == null) {
			throw new IllegalArgumentException("JobRepository must not be null");
		}
		
		if (listener == null) {
			throw new IllegalArgumentException("JvmShutdownListener must not be null");
		}
	}
	
	/**
	 * Creates a Spring Batch Step with the given name and tasklet.
	 *
	 * @param stepName the name of the step
	 * @param tasklet the Tasklet to execute
	 * @return Step instance
	 */
	protected Step createStep(String stepName, Tasklet tasklet) {
		return new StepBuilder(stepName, jobRepository).tasklet(tasklet, transactionManager)
													   .build();
	}

	/**
	 * Creates a Spring Batch Job with the given name and steps.
	 *
	 * @param jobName the name of the job
	 * @param steps the steps to include in the job
	 * @return Job instance
	 */
	protected Job createJob(String jobName, Step... steps) {

		if (steps.length == 0) throw new IllegalArgumentException("At least one step required");

		SimpleJobBuilder builder = new JobBuilder(jobName, jobRepository).listener(listener)
																		 .incrementer(new RunIdIncrementer())
																		 .start(steps[0]);

		for (int i = 1; i < steps.length; i++) {
			builder = builder.next(steps[i]);
		}

		return builder.build();
	}
}
