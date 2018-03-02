package es.automation.batch.dto;

import es.automation.feeder.dto.TestDetails;
import java.util.ArrayList;
import java.util.List;

public class AutomationDataDto {

    private List<TestDetails> allTestsToBeProcessed = new ArrayList<>();

    public AutomationDataDto allTestsToBeProcessed(final List<TestDetails> allTestsToBeProcessed) {
        this.allTestsToBeProcessed = allTestsToBeProcessed;
        return this;
    }

    public List<TestDetails> getAllTestsToBeProcessed() {
        return allTestsToBeProcessed;
    }

    public void setAllTestsToBeProcessed(final List<TestDetails> allTestsToBeProcessed) {
        this.allTestsToBeProcessed = allTestsToBeProcessed;
    }
}
