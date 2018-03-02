package es.automation.batch.configuration;

import es.automation.batch.dto.AutomationDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConfigurationProperties(prefix = "automation")
@Import(BatchConfiguration.class)
public class AutomationConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ItemReader<AutomationDataDto> automationDataReader;

    private final ItemWriter<AutomationDataDto> automationWriter;

    private static final Logger LOGGER = LoggerFactory.getLogger(AutomationConfiguration.class);

    @Autowired
    public AutomationConfiguration(final JobBuilderFactory jobBuilderFactory
                                , final StepBuilderFactory stepBuilderFactory
                                , final ItemReader<AutomationDataDto> automationDataReader
                                , final ItemWriter<AutomationDataDto> automationWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.automationDataReader = automationDataReader;
        this.automationWriter = automationWriter;
    }

    @Bean
    public Job automationJob() {
        return jobBuilderFactory.get("automationJob").start(automationStep()).build();
    }

    @Bean(name="automationStep")
    public Step automationStep() {
        LOGGER.info("Automation Job step created.");

        final StepExecutionListener automationStepExecutionListener = new StepExecutionListener() {

            @Override
            public void beforeStep(StepExecution stepExecution) {

            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                if (stepExecution.getReadCount() == 0) {
                    LOGGER.warn("Nothing to read.");
                    return ExitStatus.FAILED;
                }
                return null;
            }
        };

        final StepBuilder stepBuilder = stepBuilderFactory.get("automationStep");

        return stepBuilder.listener(automationStepExecutionListener)
                .<AutomationDataDto, AutomationDataDto> chunk(new CompletionPolicy() {
                    @Override
                    public boolean isComplete(RepeatContext repeatContext, RepeatStatus repeatStatus) {
                        return isComplete(repeatContext);
                    }

                    @Override
                    public boolean isComplete(RepeatContext repeatContext) {
                        repeatContext.setCompleteOnly();
                        return Boolean.TRUE;
                    }

                    @Override
                    public RepeatContext start(RepeatContext repeatContext) {
                        return repeatContext;
                    }

                    @Override
                    public void update(RepeatContext repeatContext) {

                    }
                }).reader(automationDataReader).writer(automationWriter).build();
    }
}
