# DCF Batch Application

This project is a Maven-based Spring Boot batch application for DCF, using Oracle JDBC and Spring Quartz Scheduler. It includes two simple Tasklet jobs and unit tests for steps and jobs.

## Features
- Spring Boot Batch with Tasklet jobs
- Oracle JDBC configuration
- Quartz Scheduler integration
- Unit tests for jobs and steps

## Getting Started
1. Build: `mvn clean install`
2. Run: `mvn spring-boot:run`

## Project Structure
- Base package: `gov.mass.dcf.batch`
- Source: `src/main/java/gov/mass/dcf/batch`
- Tests: `src/test/java/gov/mass/dcf/batch`

## Customization
- Update Oracle DB connection in `application.properties`

---
