package es.automation.batch.writer;

import es.automation.batch.dto.AutomationDataDto;
import es.automation.batch.service.AutomationService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class AutomationWriter implements ItemWriter<AutomationDataDto> {

    private AutomationService automationService;

    @Autowired
    public AutomationWriter(final AutomationService automationService) {
        this.automationService = automationService;
    }

    @Override
    public void write(List<? extends AutomationDataDto> items) throws Exception {
        for (AutomationDataDto automationDataDtoItem: items) {
            automationService.doProcess(automationDataDtoItem);
            automationService.updateProcessedFlag();
        }
    }
}
