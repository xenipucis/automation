package es.automation.batch.reader;

import es.automation.batch.service.AutomationService;
import es.automation.batch.dto.AutomationDataDto;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class AutomationReader implements ItemReader<AutomationDataDto> {

    private final AutomationService automationService;

    @Autowired
    public AutomationReader(final AutomationService automationService) {
        this.automationService = automationService;
    }

    @Override
    public AutomationDataDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return automationService.readAutomationData();
    }
}
