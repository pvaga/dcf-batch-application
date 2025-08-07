package gov.mass.dcf.batch.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class QuartzConfig {

	private static final String JOB_NAME = "jobName";

	// Generic API to create JobDetail
    public static JobDetail createJobDetail(String jobName, Class<? extends Job> jobClass) {
		return JobBuilder.newJob(jobClass)
						 .withIdentity(jobName)
						 .usingJobData(JOB_NAME, jobName)
						 .storeDurably(true)
						 .build();
	}

    // Generic API to create Trigger with cron expression
    public static Trigger createTrigger(JobDetail jobDetail, String triggerName, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
}
