/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Batch configuration for Spring Batch jobs, including table prefix and job registry post-processor.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

	public static final String BATCH_TABLE_PREFIX = "IFNET_BATCH_";

    /**
     * Registers a JobRegistryBeanPostProcessor bean for job registry integration.
     *
     * @param jobRegistry the job registry
     * @return JobRegistryBeanPostProcessor instance
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    /**
     * Returns the table prefix for Spring Batch metadata tables.
     *
     * @return the table prefix string
     */
    @Override
    public String getTablePrefix() {
        return BATCH_TABLE_PREFIX;
    }
}
