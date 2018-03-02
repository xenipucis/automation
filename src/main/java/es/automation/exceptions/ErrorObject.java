package es.automation.exceptions;

import java.util.Objects;

public class ErrorObject {

    private String name;
    private int internalCode;
    private int httpCode;
    private String shortDescription;
    private String moreInformation;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ErrorObject name(final String name) {
        this.name = name;
        return this;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(final String moreInformation) {
        this.moreInformation = moreInformation;
    }

    public ErrorObject moreInformation(final String moreInformation) {
        this.moreInformation = moreInformation;
        return this;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(final int httpCode) {
        this.httpCode = httpCode;
    }

    public ErrorObject httpCode(final int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    public int getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(final int internalCode) {
        this.internalCode = internalCode;
    }

    public ErrorObject internalCode(final int internalCode) {
        this.internalCode = internalCode;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(final String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ErrorObject shortDescription(final String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorObject errorObject = (ErrorObject) o;
        return Objects.equals(this.internalCode, errorObject.internalCode)
                && Objects.equals(this.httpCode, errorObject.httpCode)
                && Objects.equals(this.shortDescription, errorObject.shortDescription)
                && Objects.equals(this.moreInformation, errorObject.moreInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCode, httpCode, shortDescription, moreInformation);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ErrorObject {\n");
        sb.append("    internalCode: ").append(toIndentedString(internalCode)).append("\n");
        sb.append("    httpCode: ").append(toIndentedString(httpCode)).append("\n");
        sb.append("    shortDescription: ").append(toIndentedString(shortDescription)).append("\n");
        sb.append("    moreInformation: ").append(toIndentedString(moreInformation)).append("\n");
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
