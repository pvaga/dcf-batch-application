/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.config;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring Batch JobExecutionListener to handle JVM shutdown events and log job status.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@Slf4j
@Component
public class JvmShutdownListener implements JobExecutionListener {
    
    private final ThreadLocal<LocalDateTime> startTime = new ThreadLocal<>();

    /**
     * Called before a job starts. Logs job start and adds a JVM shutdown hook.
     *
     * @param jobExecution the JobExecution context
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime.set(LocalDateTime.now());
        String jobName = jobExecution.getJobInstance().getJobName();

        log.info("####Job Started: {} - Parameters: {} - Start Time: {}",
                jobName,
                jobExecution.getJobParameters(),
                startTime.get());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (jobExecution.isRunning()) {
                log.warn("####JVM shutdown requested while job {} is still running. Waiting for completion...", jobName);

                while (jobExecution.isRunning()) {
                    try {
                        Thread.sleep(1000);
                        log.debug("####Still waiting for job {} to complete...", jobName);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("####Interrupted while waiting for job {} completion: {}", jobName, e.getMessage());
                        break;
                    }
                }
            }
        }));
    }

    /**
     * Called after a job ends. Logs job completion or failure details.
     *
     * @param jobExecution the JobExecution context
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        LocalDateTime endTime = LocalDateTime.now();
        String jobName = jobExecution.getJobInstance().getJobName();
        Duration duration = Duration.between(startTime.get(), endTime);

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("####Job Completed Successfully: {} - Duration: {} seconds - End Time: {}",
                    jobName,
                    duration.getSeconds(),
                    endTime);

            log.debug("####Job {} Details - Read Count: {}, Write Count: {}, Skip Count: {}",
                    jobName,
                    jobExecution.getStepExecutions().stream().mapToLong(step -> step.getReadCount()).sum(),
                    jobExecution.getStepExecutions().stream().mapToLong(step -> step.getWriteCount()).sum(),
                    jobExecution.getStepExecutions().stream().mapToLong(step -> step.getSkipCount()).sum());
        } else {
            log.error("####Job Failed: {} - Status: {} - Duration: {} seconds - End Time: {}",
                    jobName,
                    jobExecution.getStatus(),
                    duration.getSeconds(),
                    endTime);

            log.error("####Job {} Failure Details - Exit Description: {}",
                    jobName,
                    jobExecution.getExitStatus().getExitDescription());

            jobExecution.getStepExecutions().forEach(stepExecution -> {
                if (stepExecution.getStatus() != BatchStatus.COMPLETED) {
                    log.error("####Failed Step: {} - Status: {} - Exit Description: {}",
                            stepExecution.getStepName(),
                            stepExecution.getStatus(),
                            stepExecution.getExitStatus().getExitDescription());
                }
            });
        }

        startTime.remove(); // Clean up ThreadLocal
    }
}
