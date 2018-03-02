package es.automation.feeder.dto;

public class Error {
    private final String name;

    private final Integer status;

    private final Integer internalCode;

    private final String shortMessage;

    private final String detailedMessage;

    public Error(final String name
            , final Integer status
            , final Integer internalCode
            , final String shortMessage
            , final String detailedMessage) {
        this.name = name;
        this.status = status;
        this.internalCode = internalCode;
        this.shortMessage = shortMessage;
        this.detailedMessage = detailedMessage;
    }

    public String getName() {
        return name;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getInternalCode() {
        return internalCode;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }
}
