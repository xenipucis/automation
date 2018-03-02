package es.automation.exceptions;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorType {

    // Generic Errors
    INTERNAL_SERVER_ERROR
    , BAD_REQUEST_ERROR

    // Custom Errors
    , TEST_NAME_NOT_BLANK_ERROR
    , TEST_NAME_LENGTH_ERROR
    , BASE_URI_NOT_BLANK_ERROR
    , BASE_URI_LENGTH_ERROR
    , BASE_URI_PATTERN_ERROR
    , PORT_MIN_VALUE_ERROR
    , PORT_MAX_VALUE_ERROR
    , RELATIVE_URI_PATTERN_ERROR
    , HTTP_METHOD_NOT_SUPPORTED_ERROR
    , HTTP_BODY_NOT_SUPPORTED_ERROR
    , HTTP_METHOD_NOT_NULL_ERROR
    , TEST_VALIDATOR_NOT_BLANK_ERROR
    , HTTP_STATUS_MIN_VALUE_ERROR
    , HTTP_STATUS_MAX_VALUE_ERROR
    , RELATIVE_URI_MAX_LENGTH_ERROR
    , BODY_MAX_LENGTH_ERROR
    , TEST_VALIDATOR_MAX_LENGTH_ERROR
    ;

    public static final String TEST_NAME_NOT_BLANK_ERROR_KEY = "TEST_NAME_NOT_BLANK_ERROR";
    public static final String TEST_NAME_LENGTH_ERROR_KEY = "TEST_NAME_LENGTH_ERROR";
    public static final String BASE_URI_NOT_BLANK_ERROR_KEY = "BASE_URI_NOT_BLANK_ERROR";
    public static final String BASE_URI_LENGTH_ERROR_KEY = "BASE_URI_LENGTH_ERROR";
    public static final String PORT_MIN_VALUE_ERROR_KEY = "PORT_MIN_VALUE_ERROR";
    public static final String PORT_MAX_VALUE_ERROR_KEY = "PORT_MAX_VALUE_ERROR";
    public static final String HTTP_METHOD_NOT_SUPPORTED_ERROR_KEY = "HTTP_METHOD_NOT_SUPPORTED_ERROR";
    public static final String HTTP_METHOD_NOT_NULL_ERROR_KEY = "HTTP_METHOD_NOT_NULL_ERROR";
    public static final String TEST_VALIDATOR_NOT_BLANK_ERROR_KEY = "TEST_VALIDATOR_NOT_BLANK_ERROR";
    public static final String HTTP_STATUS_MIN_VALUE_ERROR_KEY = "HTTP_STATUS_MIN_VALUE_ERROR";
    public static final String HTTP_STATUS_MAX_VALUE_ERROR_KEY = "HTTP_STATUS_MAX_VALUE_ERROR";
    public static final String RELATIVE_URI_MAX_LENGTH_ERROR_KEY = "RELATIVE_URI_MAX_LENGTH_ERROR";
    public static final String BODY_MAX_LENGTH_ERROR_KEY = "BODY_MAX_LENGTH_ERROR";
    public static final String TEST_VALIDATOR_MAX_LENGTH_ERROR_KEY = "TEST_VALIDATOR_MAX_LENGTH_ERROR";


    public static Optional<ErrorType> getErrorTypeByName(final String name) {
        return Arrays.stream(ErrorType.values()).filter(errorType -> name.contains(errorType.name())).findFirst();
    }
}

