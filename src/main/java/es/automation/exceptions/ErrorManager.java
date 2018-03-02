package es.automation.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class ErrorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorManager.class);

    public static final String QUALIFIER_NAME = "errorYml";
    public static final String ERRORS_CUSTOM = "errors.custom.";
    public static final String ERRORS_GENERIC = "errors.generic.";
    public static final String EMPTY_STRING = "";

    private Map<String, ErrorObject> errorsMap = new HashMap<>();

    private final StringsHelper stringsHelper;

    private YamlPropertiesFactoryBean yamlPropertiesFactoryBean;
    private Properties properties = new Properties();

    @Autowired
    public ErrorManager(final StringsHelper stringsHelper, @Qualifier(QUALIFIER_NAME) final YamlPropertiesFactoryBean yamlPropertiesFactoryBean) {
        this.yamlPropertiesFactoryBean = yamlPropertiesFactoryBean;
        this.stringsHelper = stringsHelper;
    }

    @PostConstruct
    public void initApplication() {
        properties = yamlPropertiesFactoryBean.getObject();
        errorsMap = buildErrorsMap();
    }

    private Map<String, ErrorObject> buildErrorsMap() {
        final Map<String, ErrorObject> map = new HashMap<>();

        for (Object keyObj : properties.keySet()) {
            final String key = (String) keyObj;

            if (key.startsWith(ERRORS_CUSTOM) || key.startsWith(ERRORS_GENERIC)) {
                String name = stringsHelper.extractSubStringBetweenTwoSubStrings(key, ERRORS_CUSTOM, StringsHelper.DOT);
                String typeOfProperty = stringsHelper.extractSubStringBetweenTwoSubStrings(key,
                        new StringBuilder(ERRORS_CUSTOM).append(name).append(StringsHelper.DOT).toString(), EMPTY_STRING);
                if (key.startsWith(ERRORS_GENERIC)) {
                    name = stringsHelper.extractSubStringBetweenTwoSubStrings(key, ERRORS_GENERIC, StringsHelper.DOT);
                    typeOfProperty = stringsHelper.extractSubStringBetweenTwoSubStrings(key,
                            new StringBuilder(ERRORS_GENERIC).append(name).append(StringsHelper.DOT).toString(), EMPTY_STRING);
                }
                final String valueOfProperty = properties.get(key).toString();

                if (map.get(name) == null) {
                    ErrorObject errorObject = new ErrorObject().name(name);

                    map.put(name, errorObject);
                }

                switch (typeOfProperty) {
                    case "shortDescription":
                        map.get(name).setShortDescription(valueOfProperty);
                        break;
                    case "internalCode":
                        int internalCode = -1;
                        try {
                            internalCode = Integer.parseInt(valueOfProperty);
                        } catch (NumberFormatException nfe) {
                            LOGGER.error("Invalid internal code in error configuration. Value: " + valueOfProperty, nfe);
                        }
                        map.get(name).setInternalCode(internalCode);
                        break;
                    case "httpCode":
                        int httpCode = -1;
                        try {
                            httpCode = Integer.parseInt(valueOfProperty);
                        } catch (NumberFormatException nfe) {
                            LOGGER.error("Invalid http code in error configuration. Value: " + valueOfProperty, nfe);
                        }
                        map.get(name).setHttpCode(httpCode);
                        break;
                    case "moreInformation":
                        map.get(name).setMoreInformation(valueOfProperty);
                        break;
                }
            }
        }
        return map;
    }

    public ErrorObject getError(ErrorType errorType) {
        return errorsMap.get(errorType.name());
    }
}

