package es.automation.feeder.service;

import es.automation.feeder.dto.TestResponse;
import es.automation.feeder.entity.TestHeaderEntity;
import es.automation.feeder.repository.TestHeadersRepository;
import es.automation.feeder.repository.TestRepository;
import es.automation.feeder.dto.TestDetails;
import es.automation.feeder.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TestService {

    private final TestRepository testRepository;

    private final TestHeadersRepository testHeadersRepository;

    @Autowired
    public TestService(final TestRepository testRepository
                    , final TestHeadersRepository testHeadersRepository) {
        this.testRepository = testRepository;
        this.testHeadersRepository = testHeadersRepository;
    }

    @Transactional
    public TestResponse saveTest(final TestDetails testDetails) {

        final TestEntity testEntity = testRepository.save(testEntity(testDetails));

        testHeadersRepository.saveRequestTestHeaders(testEntity.getId(), testDetails.getRequestHeaders());

        return testResponse(testEntity.getId(), testDetails);
    }

    public List<TestEntity> findAllNotProcessedTests() {
        return new ArrayList<>();
    }


    @Transactional
    public TestDetails testDetailsByTestId(final Long testId) {

        final TestEntity testEntity = testRepository.retrieveTestById(testId);

        TestDetails testDetails = null;

        if (testEntity != null) {
            testDetails = new TestDetails()
                    .testName(testEntity.getTestName())
                    .baseUri(testEntity.getBaseUri())
                    .port(testEntity.getPort())
                    .relativeUri(testEntity.getRelativeUri())
                    .httpMethod(testEntity.getHttpMethod())
                    .body(testEntity.getBody())
                    .testValidatorForResult(testEntity.getTestValidatorForResult())
                    .expectedHttpStatus(testEntity.getExpectedHttpStatus());

            final List<TestHeaderEntity> testHeaderEntitiesRequest = testHeadersRepository.retrieveRequestTestHeaders(testId);
            final Map<String, String> testHeaders = new HashMap<>();
            for (TestHeaderEntity testHeaderEntity : testHeaderEntitiesRequest) {
                testHeaders.put(testHeaderEntity.getKey(), testHeaderEntity.getValue());
            }

            testDetails = testDetails.requestHeaders(testHeaders);
        }

        return testDetails;
    }

    private TestResponse testResponse(final Long testId, final TestDetails testDetails) {
        return new TestResponse(testId, testDetails);
    }

    private TestEntity testEntity(final TestDetails testDetails) {
        return new TestEntity()
                .testName(testDetails.getTestName())
                .baseUri(testDetails.getBaseUri())
                .port(testDetails.getPort())
                .relativeUri(testDetails.getRelativeUri())
                .httpMethod(testDetails.getHttpMethod())
                .body(testDetails.getBody())
                .requestHeaders(testDetails.getRequestHeaders())
                .testValidatorForResult(testDetails.getTestValidatorForResult())
                .expectedHttpStatus(testDetails.getExpectedHttpStatus());
    }

}
