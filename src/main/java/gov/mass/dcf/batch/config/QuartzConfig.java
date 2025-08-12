/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * Utility class for creating Quartz JobDetail and Trigger objects for batch jobs.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
public class QuartzConfig {

	private static final String JOB_NAME = "jobName";

    /**
     * Creates a Quartz JobDetail for the given job name and class.
     *
     * @param jobName the name of the job
     * @param jobClass the class implementing the job
     * @return JobDetail instance
     */
    public static JobDetail createJobDetail(String jobName, Class<? extends Job> jobClass) {
        return JobBuilder.newJob(jobClass)
                         .withIdentity(jobName)
                         .usingJobData(JOB_NAME, jobName)
                         .storeDurably(true)
                         .build();
    }

    /**
     * Creates a Quartz Trigger for the given job detail and cron expression.
     *
     * @param jobDetail the JobDetail to trigger
     * @param triggerName the name of the trigger
     * @param cronExpression the cron schedule expression
     * @return Trigger instance
     */
    public static Trigger createTrigger(JobDetail jobDetail, String triggerName, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
}
