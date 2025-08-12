package gov.mass.dcf.batch.job.contoller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for retrieving Spring Batch job execution details.
 *
 * Provides an endpoint to list running job executions.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@RestController
@RequestMapping("/spring/batch")
@Tag(name = "Batch Details", description = "APIs for exploring Spring Batch job and execution details.")
public class SpringBatchDetailController {
	
	@Autowired
    private JobExplorer jobExplorer;
	
    /**
     * Get all registered Spring Batch job names.
     * @return List of job names
     */
    // 1. Get all registered job names
    @Operation(summary = "Get all registered Spring Batch job names", description = "Get all registered Spring Batch job names")
    @GetMapping("/job-names")
    public ResponseEntity<List<String>> getJobNames() {
        return ResponseEntity.ok(new ArrayList<>(jobExplorer.getJobNames()));
    }

    // 2. Get all running job executions
    @Operation(summary = "Get all running job executions")
    @GetMapping("/running-job-executions")
    public ResponseEntity<List<String>> getRunningJobExecutions() {
        List<String> executions = jobExplorer.getJobNames().stream()
            .flatMap(jobName -> jobExplorer.findRunningJobExecutions(jobName).stream())
            .map(JobExecution::toString)
            .collect(Collectors.toList());
        return ResponseEntity.ok(executions);
    }

    // 3. Find all running JobExecutions for a given job name
    @Operation(summary = "Find all running job executions for a given job name")
    @GetMapping("/running-job-executions/{jobName}")
    public ResponseEntity<List<String>> getRunningJobExecutions(@PathVariable String jobName) {
        Set<JobExecution> running = jobExplorer.findRunningJobExecutions(jobName);
        List<String> result = running.stream().map(JobExecution::toString).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // 4. Get a specific JobInstance by its instanceId
    @Operation(summary = "Get a specific JobInstance by its instanceId")
    @GetMapping("/job-instance/{instanceId}")
    public ResponseEntity<JobInstance> getJobInstance(@PathVariable Long instanceId) {
        JobInstance jobInstance = jobExplorer.getJobInstance(instanceId);
        return jobInstance != null ? ResponseEntity.ok(jobInstance) : ResponseEntity.notFound().build();
    }

    // 5. Get last 100 job instances for a given job name
    @Operation(summary = "Get last 100 job instances for a given job name")
    @GetMapping("/job-instances/{jobName}")
    public ResponseEntity<List<Long>> getJobInstances(@PathVariable String jobName) {
        List<JobInstance> instances = jobExplorer.getJobInstances(jobName, 0, 100);
        List<Long> ids = instances.stream().map(JobInstance::getInstanceId).collect(Collectors.toList());
        return ResponseEntity.ok(ids);
    }

    // 6. Get all job executions for a given job instance id
    @Operation(summary = "Get all job executions for a given job instance id")
    @GetMapping("/job-executions/{instanceId}")
    public ResponseEntity<List<JobExecution>> getJobExecutionsForInstance(@PathVariable Long instanceId) {
        List<JobExecution> executions = jobExplorer.getJobExecutions(jobExplorer.getJobInstance(instanceId));
        return ResponseEntity.ok(executions);
    }

    // 7. Get a specific JobExecution by its executionId
    @Operation(summary = "Get a specific JobExecution by its executionId")
    @GetMapping("/job-execution/{executionId}")
    public ResponseEntity<JobExecution> getJobExecution(@PathVariable Long executionId) {
        JobExecution jobExecution = jobExplorer.getJobExecution(executionId);
        return jobExecution != null ? ResponseEntity.ok(jobExecution) : ResponseEntity.notFound().build();
    }

    // 8. Get a specific StepExecution by jobExecutionId and stepExecutionId
    @Operation(summary = "Get a specific StepExecution by jobExecutionId and stepExecutionId")
    @GetMapping("/step-execution/{jobExecutionId}/{stepExecutionId}")
    public ResponseEntity<StepExecution> getStepExecution(@PathVariable Long jobExecutionId, @PathVariable Long stepExecutionId) {
        StepExecution stepExecution = jobExplorer.getStepExecution(jobExecutionId, stepExecutionId);
        return stepExecution != null ? ResponseEntity.ok(stepExecution) : ResponseEntity.notFound().build();
    }
}