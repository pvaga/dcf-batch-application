package gov.mass.dcf.batch.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is used to configure the batch processing settings for the application.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

	public static final String BATCH_TABLE_PREFIX = "IFNET_BATCH_";

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }
    
    @Override
    public String getTablePrefix() {
        return BATCH_TABLE_PREFIX;
    }
}
