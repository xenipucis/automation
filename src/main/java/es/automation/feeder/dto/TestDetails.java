package es.automation.feeder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.automation.exceptions.ErrorType;

import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestDetails {

    @NotBlank(message = ErrorType.TEST_NAME_NOT_BLANK_ERROR_KEY)
    @Size(min = 3, max = 100, message = ErrorType.TEST_NAME_LENGTH_ERROR_KEY)
    @JsonProperty("testName")
    private String testName;

    @NotBlank(message = ErrorType.BASE_URI_NOT_BLANK_ERROR_KEY)
    @Size(min = 6, max = 100, message = ErrorType.BASE_URI_LENGTH_ERROR_KEY)
    @JsonProperty("baseUri")
    private String baseUri;

    @Min(value = 0, message = ErrorType.PORT_MIN_VALUE_ERROR_KEY)
    @Max(value = 65535, message = ErrorType.PORT_MAX_VALUE_ERROR_KEY)
    @JsonProperty("port")
    private int port = 80;

    @JsonProperty("relativeUri")
    @Max(value = 100, message = ErrorType.RELATIVE_URI_MAX_LENGTH_ERROR_KEY)
    private String relativeUri;

    @NotNull(message = ErrorType.HTTP_METHOD_NOT_NULL_ERROR_KEY)
    @Pattern(regexp = "OPTIONS|GET|POST|PUT|DELETE", message = ErrorType.HTTP_METHOD_NOT_SUPPORTED_ERROR_KEY)
    @JsonProperty("httpMethod")
    private String httpMethod;

    @JsonProperty("headers")
    private Map<String, String> requestHeaders = new HashMap<>();

    @JsonProperty("body")
    @Max(value = 2500, message = ErrorType.BODY_MAX_LENGTH_ERROR_KEY)
    private String body;

    @NotBlank(message = ErrorType.TEST_VALIDATOR_NOT_BLANK_ERROR_KEY)
    @JsonProperty("testValidatorForResult")
    @Max(value = 2500, message = ErrorType.TEST_VALIDATOR_MAX_LENGTH_ERROR_KEY)
    private String testValidatorForResult;

    @JsonProperty("expectedHttpStatus")
    @Min(value = 100, message = ErrorType.HTTP_STATUS_MIN_VALUE_ERROR_KEY)
    @Max(value = 600, message = ErrorType.HTTP_STATUS_MAX_VALUE_ERROR_KEY)
    private int expectedHttpStatus;


    private boolean processed;

    public TestDetails() {

    }

    public String getTestName() {
        return testName;
    }

    public TestDetails testName(final String testName) {
        this.testName = testName;
        return this;
    }

    public void setTestName(final String testName) {
        this.testName = testName;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public TestDetails baseUri(final String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public void setBaseUri(final String baseUri) {
        this.baseUri = baseUri;
    }

    public int getPort() {
        return port;
    }

    public TestDetails port(final int port) {
        this.port = port;
        return this;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getRelativeUri() {
        return relativeUri;
    }

    public TestDetails relativeUri(final String relativeUri) {
        this.relativeUri = relativeUri;
        return this;
    }

    public void setRelativeUri(final String relativeUri) {
        this.relativeUri = relativeUri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public TestDetails httpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public TestDetails requestHeaders(final Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
        return this;
    }

    public void setRequestHeaders(final Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getTestValidatorForResult() {
        return testValidatorForResult;
    }

    public TestDetails testValidatorForResult(final String testValidatorForResult) {
        this.testValidatorForResult = testValidatorForResult;
        return this;
    }

    public void setTestValidatorForResult(final String testValidatorForResult) {
        this.testValidatorForResult = testValidatorForResult;
    }

    public int getExpectedHttpStatus() {
        return expectedHttpStatus;
    }

    public TestDetails expectedHttpStatus(final int expectedHttpStatus) {
        this.expectedHttpStatus = expectedHttpStatus;
        return this;
    }

    public void setExpectedHttpStatus(final int expectedHttpStatus) {
        this.expectedHttpStatus = expectedHttpStatus;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public TestDetails body(final String body) {
        this.body = body;
        return this;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(final boolean processed) {
        this.processed = processed;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestDetails test = (TestDetails) o;
        return Objects.equals(this.testName, test.testName)
                && Objects.equals(this.baseUri, test.baseUri)
                && Objects.equals(this.port, test.port)
                && Objects.equals(this.relativeUri, test.relativeUri)
                && Objects.equals(this.httpMethod, test.httpMethod)
                && Objects.equals(this.requestHeaders, test.requestHeaders)
                && Objects.equals(this.body, test.body)
                && Objects.equals(this.testValidatorForResult, test.testValidatorForResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testName, baseUri, port, relativeUri, httpMethod, requestHeaders, body, testValidatorForResult);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TestDetails {\n");
        sb.append("    testName: ").append(toIndentedString(testName)).append("\n");
        sb.append("    baseUri: ").append(toIndentedString(baseUri)).append("\n");
        sb.append("    port: ").append(toIndentedString(port)).append("\n");
        sb.append("    relativeUri: ").append(toIndentedString(relativeUri)).append("\n");
        sb.append("    httpMethod: ").append(toIndentedString(httpMethod)).append("\n");
        if (Arrays.asList("POST", "PUT").contains(httpMethod)) {
            sb.append("    body: ").append(toIndentedString(body)).append("\n");
        }
        sb.append("    requestHeaders: ").append(toIndentedString(requestHeaders)).append("\n");
        sb.append("    testValidatorForResult: ").append(toIndentedString(testValidatorForResult)).append("\n");
        sb.append("    expectedHttpStatus: ").append(toIndentedString(expectedHttpStatus)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
