package gov.mass.dcf.batch.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuartzConfigTest {
	
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Test
    void testSchedulerFactoryBean() {
        assertNotNull(schedulerFactoryBean);
        assertTrue(schedulerFactoryBean.isAutoStartup());
    }
}
