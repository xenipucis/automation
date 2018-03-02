package es.automation.feeder.validators;

import es.automation.exceptions.CustomException;
import es.automation.exceptions.ErrorManager;
import es.automation.exceptions.ErrorType;
import es.automation.feeder.dto.TestDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestDetailsValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDetailsValidator.class);

    private final ErrorManager errorManager;

    @Autowired
    public TestDetailsValidator(final ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public void validate(final TestDetails testDetails) {
        validateUri(testDetails.getBaseUri(), ErrorType.BASE_URI_PATTERN_ERROR);

        validateUri(buildFullUri(testDetails), ErrorType.RELATIVE_URI_PATTERN_ERROR);

        validateBody(testDetails.getHttpMethod(), testDetails.getBody());

    }

    private String buildFullUri(final TestDetails testDetails) {
        final StringBuilder fullUriStringBuilder = new StringBuilder(testDetails.getBaseUri())
                .append(":")
                .append(testDetails.getPort())
                ;

        if (StringUtils.isNotBlank(testDetails.getRelativeUri())) {
            fullUriStringBuilder
                    .append("/")
                    .append(testDetails.getRelativeUri());
        }

        return fullUriStringBuilder.toString();
    }

    private void validateUri(final String uri, final ErrorType errorTypeToShow) {
        final String[] schemes = {"http", "https"};
        final UrlValidator urlValidator = new UrlValidator(schemes);


        if (!urlValidator.isValid(uri)) {
            LOGGER.error("Invalid Uri: {}", uri);
            throw new CustomException(errorManager.getError(errorTypeToShow));
        }
    }

    private void validateBody(final String httpMethodAsString, final String body) {
        final HttpMethod httpMethod = HttpMethod.resolve(httpMethodAsString);
        if (Arrays.asList(HttpMethod.GET, HttpMethod.OPTIONS, HttpMethod.DELETE).contains(httpMethod) && StringUtils.isNotBlank(body)) {
            LOGGER.error("When httpMethod is one of the following: [OPTIONS, GET, DELETE], body field is not supported: {}", body);
            throw new CustomException(errorManager.getError(ErrorType.HTTP_BODY_NOT_SUPPORTED_ERROR));
        }
    }


}
