package es.automation.feeder.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class TestHeaderEntity {

    @NotNull
    private Long testId;

    @NotBlank
    private String key;

    private String value;

    public TestHeaderEntity() {

    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(final Long testId) {
        this.testId = testId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestHeaderEntity test = (TestHeaderEntity) o;
        return Objects.equals(this.testId, test.testId)
                && Objects.equals(this.key, test.key)
                && Objects.equals(this.value, test.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testId, key, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TestHeaderEntity {\n");
        sb.append("    testId: ").append(toIndentedString(testId)).append("\n");
        sb.append("    key: ").append(toIndentedString(key)).append("\n");
        sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
