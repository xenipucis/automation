package es.automation.feeder.dto;

import es.automation.feeder.dto.TestDetails;

public class TestResponse {

    private final Long id;

    private final TestDetails testDetails;

    public TestResponse(final Long id, final TestDetails testDetails) {
        this.id = id;
        this.testDetails = testDetails;
    }

    public Long getId() {
        return id;
    }

    public TestDetails getTestDetails() {
        return testDetails;
    }
}
