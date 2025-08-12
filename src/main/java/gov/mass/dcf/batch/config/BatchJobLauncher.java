/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.config;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz job bean for launching Spring Batch jobs from Quartz triggers.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
public class BatchJobLauncher extends QuartzJobBean {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;

    /**
     * Executes the batch job as triggered by Quartz.
     *
     * @param context the Quartz JobExecutionContext
     */
    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            String jobName = jobDataMap.getString("jobName");

            Job job = jobLocator.getJob(jobName);
            JobParameters params = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    										 .toJobParameters();

            jobLauncher.run(job, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
