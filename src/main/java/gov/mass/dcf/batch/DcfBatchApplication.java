/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the DCF Batch Spring Boot application.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
@SpringBootApplication
public class DcfBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DcfBatchApplication.class, args);
    }
}
