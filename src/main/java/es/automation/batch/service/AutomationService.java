package es.automation.batch.service;

import es.automation.feeder.dto.TestDetails;
import es.automation.feeder.entity.TestEntity;
import es.automation.feeder.entity.TestHeaderEntity;
import es.automation.feeder.repository.TestHeadersRepository;
import es.automation.batch.dto.AutomationDataDto;
import es.automation.batch.repository.AutomationRepository;
import es.automation.feeder.resource.AutomationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AutomationService {

    private final AutomationRepository automationRepository;

    private final TestHeadersRepository testHeadersRepository;

    private final AutomationController automationController;

    @Autowired
    public AutomationService(final AutomationRepository automationRepository
                            , final TestHeadersRepository testHeadersRepository
                            , final AutomationController automationController) {
        this.automationRepository = automationRepository;
        this.testHeadersRepository = testHeadersRepository;
        this.automationController = automationController;
    }

    public AutomationDataDto readAutomationData() {
        List<TestDetails> allTestsToBeProcessed = retrieveTestDetailsToBeProcessed();
        return new AutomationDataDto().allTestsToBeProcessed(allTestsToBeProcessed);
    }

    public void doProcess(final AutomationDataDto automationDataDto) {
        automationController.doWebClientCall(automationDataDto);
    }

    public void updateProcessedFlag() {

    }

    @Transactional
    public List<TestDetails> retrieveTestDetailsToBeProcessed() {

        final List<TestDetails> testDetailsToBeProcessed = new ArrayList<>();
        final List<TestEntity> testEntitiesToBeProcessed = automationRepository.findAllTestsToBeProcessed();

        if (!testEntitiesToBeProcessed.isEmpty()) {
            for (TestEntity testEntity : testEntitiesToBeProcessed) {
                TestDetails testDetails = new TestDetails()
                        .testName(testEntity.getTestName())
                        .baseUri(testEntity.getBaseUri())
                        .port(testEntity.getPort())
                        .relativeUri(testEntity.getRelativeUri())
                        .httpMethod(testEntity.getHttpMethod())
                        .body(testEntity.getBody())
                        .testValidatorForResult(testEntity.getTestValidatorForResult())
                        .expectedHttpStatus(testEntity.getExpectedHttpStatus());

                final List<TestHeaderEntity> testHeaderEntitiesRequest = testHeadersRepository.retrieveRequestTestHeaders(testEntity.getId());
                final Map<String, String> testHeaders = new HashMap<>();
                for (TestHeaderEntity testHeaderEntity : testHeaderEntitiesRequest) {
                    testHeaders.put(testHeaderEntity.getKey(), testHeaderEntity.getValue());
                }

                testDetails = testDetails.requestHeaders(testHeaders);

                testDetailsToBeProcessed.add(testDetails);
            }
        }

        return testDetailsToBeProcessed;
    }
}
