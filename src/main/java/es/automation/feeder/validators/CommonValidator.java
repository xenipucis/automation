package es.automation.feeder.validators;

import es.automation.exceptions.CustomException;
import es.automation.exceptions.ErrorManager;
import es.automation.exceptions.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Component
public class CommonValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonValidator.class);

    private final ErrorManager errorManager;

    @Autowired
    public CommonValidator(final ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public void validate(final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final String key = bindingResult.getAllErrors().get(0).getDefaultMessage();

            Optional<ErrorType> errorTypeOptional = ErrorType.getErrorTypeByName(key);

            if (errorTypeOptional.isPresent()) {
                throw new CustomException(errorManager.getError(errorTypeOptional.get()));
            }
            else {
                LOGGER.error("ErrorType does not exist for this key: {}, error: {}", key, bindingResult.getAllErrors().get(0).toString());
                throw new CustomException(errorManager.getError(ErrorType.INTERNAL_SERVER_ERROR));
            }
        }
    }
}
