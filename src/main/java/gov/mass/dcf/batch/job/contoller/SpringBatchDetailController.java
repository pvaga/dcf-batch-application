/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.job.contoller;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for retrieving Spring Batch job execution details.
 *
 * Provides an endpoint to list running job executions.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@RestController
@RequestMapping("/batch")
public class SpringBatchDetailController {

    @Autowired
    private JobExplorer jobExplorer;

    @GetMapping("/job-executions")
    public ResponseEntity<List<String>> getJobExecutions() {
        List<String> executions = jobExplorer.getJobNames().stream()
            .flatMap(jobName -> jobExplorer.findRunningJobExecutions(jobName).stream())
            .map(JobExecution::toString)
            .collect(Collectors.toList());
        return ResponseEntity.ok(executions);
    }
}
