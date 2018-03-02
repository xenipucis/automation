package es.automation.feeder.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestEntity {

    private Long id;

    private String testName;

    private String baseUri;

    private int port = 80;

    private String relativeUri;

    private String httpMethod;

    private Map<String, String> requestHeaders = new HashMap<>();

    private String testValidatorForResult;

    private int expectedHttpStatus;

    private String body;

    private boolean processed;

    public TestEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public TestEntity testName(final String testName) {
        this.testName = testName;
        return this;
    }

    public void setTestName(final String testName) {
        this.testName = testName;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public TestEntity baseUri(final String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public void setBaseUri(final String baseUri) {
        this.baseUri = baseUri;
    }

    public int getPort() {
        return port;
    }

    public TestEntity port(final int port) {
        this.port = port;
        return this;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getRelativeUri() {
        return relativeUri;
    }

    public TestEntity relativeUri(final String relativeUri) {
        this.relativeUri = relativeUri;
        return this;
    }

    public void setRelativeUri(final String relativeUri) {
        this.relativeUri = relativeUri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public TestEntity httpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public TestEntity requestHeaders(final Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
        return this;
    }

    public void setRequestHeaders(final Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getTestValidatorForResult() {
        return testValidatorForResult;
    }

    public TestEntity testValidatorForResult(final String testValidatorForResult) {
        this.testValidatorForResult = testValidatorForResult;
        return this;
    }

    public void setTestValidatorForResult(final String testValidatorForResult) {
        this.testValidatorForResult = testValidatorForResult;
    }

    public int getExpectedHttpStatus() {
        return expectedHttpStatus;
    }

    public TestEntity expectedHttpStatus(final int expectedHttpStatus) {
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

    public TestEntity body(final String body) {
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
        TestEntity test = (TestEntity) o;
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
        sb.append("TestEntity {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
