# DCF Batch Application

This project is a Maven-based Spring Boot batch application for DCF, using Oracle JDBC and Spring Quartz Scheduler. It includes two simple Tasklet jobs and unit tests for steps and jobs.

## Features
- Spring Boot Batch with Tasklet jobs
- Oracle JDBC configuration
- Quartz Scheduler integration
- Unit tests for jobs and steps

## Getting Started
1. Build: `mvn clean install -DDB_USERID={userId} -DDB_PASSWORD={password}`
2. Run: `mvn spring-boot:run -Dspring-boot.run.arguments="--DB_USERID={userId} -DB_PASSWORD={password}"`

## Project Structure
- Base package: `gov.mass.dcf.batch`
- Source: `src/main/java/gov/mass/dcf/batch`
- Tests: `src/test/java/gov/mass/dcf/batch`

## Customization
- Update Oracle DB connection in `application.properties`

## How to Configure a New Job

To add a new batch job to this application, follow these steps:

###1. **Add Job & Schedule Details**
- Open `BatchJobConfigConstants.java` (in `gov.mass.dcf.batch.job.util`).
- Add new constants for your job name, step name(s), trigger, and schedule (cron expression).

###2. **Add a Tasklet**
- Create a new class in `gov.mass.dcf.batch.job.tasklet`.
- Extend `Tasklet` and implement the `execute` method with your job logic.

###3. **Configure the Job**
- Create or update a job configuration class in `gov.mass.dcf.batch.job.config`.
- Add job configuration class by extending `BaseJobConfig`.
- Define beans for your step(s) using your new Tasklet.
- Define a bean for the Job, referencing your step(s).
- Configure Quartz JobDetail and Trigger beans for scheduling.

