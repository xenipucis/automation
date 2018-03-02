package es.automation.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@ConfigurationProperties
public class JobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutor.class);

    private static final JobParametersBuilder JOB_PARAMETERS_BUILDER = new JobParametersBuilder();

    private final JobLauncher jobLauncher;

    private final Job automationJob;

    private final boolean isAutomationJobEnabled;

    @Autowired
    public JobExecutor(final JobLauncher jobLauncher
            , @Qualifier("automationJob") final Job automationJob
            , @Value("${automation.enabled}") final boolean isAutomationEnabled) {
        this.jobLauncher = jobLauncher;
        this.automationJob = automationJob;
        this.isAutomationJobEnabled = isAutomationEnabled;
    }

    @Scheduled(cron="${automation.job-start-time}")
    public void runAutomation() {
        if (isAutomationJobEnabled) {
            final JobParameters jobParameters = JOB_PARAMETERS_BUILDER.addDate("executionDate", new Date())
                    .toJobParameters();
            try {
                jobLauncher.run(automationJob, jobParameters);
            } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException e) {
                e.printStackTrace();
            }
        }
        else {
            LOGGER.debug("Automation job is disabled. It can be enabled by setting the automation.enaled" +
                    " property to true in the application.yaml file");
        }
    }


}
