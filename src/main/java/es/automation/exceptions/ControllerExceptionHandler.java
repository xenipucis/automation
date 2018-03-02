package es.automation.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final ErrorManager errorManager;

    @Autowired
    public ControllerExceptionHandler(final ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorObject> handleBaseException(CustomException e)
    {
        LOGGER.error(e.getMessage());
        final ErrorObject errorObject = e.getErrorObject();
        return new ResponseEntity<>(errorObject, HttpStatus.valueOf(errorObject.getHttpCode()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    ErrorObject handleException(Exception e){
        LOGGER.error(e.getMessage());
        final ErrorObject errorObject = errorManager.getError(ErrorType.INTERNAL_SERVER_ERROR);
        return errorObject;
    }

}
