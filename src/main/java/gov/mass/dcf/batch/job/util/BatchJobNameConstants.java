/*
 * Created on Aug 11, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.job.util;

/**
 * Constants for batch job and step names, triggers, and schedules.
 *
 * @author prava
 * @version $Revision$ $Date$
 */
public class BatchJobNameConstants {
	
	//Sample Job
	public static final String SAMPLE_JOB = "sampleJob";
	public static final String FIRST_STEP = "firstStep";
	public static final String SECOND_STEP = "secondStep";
	
	public static final String SAMPLE_JOB_TRIGGER = "sampleJobTrigger";
	public static final String SAMPLE_JOB_SCHEDULE = "0 0/5 * * * ?"; // Every 5 minutes
}
