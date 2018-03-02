package es.automation.exceptions.config;

import es.automation.exceptions.ErrorManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertiesConfig {

    public static final String ERROR_FILE_NAME = "errorMessages.yaml";

    @Bean
    @Qualifier(ErrorManager.QUALIFIER_NAME)
    public YamlPropertiesFactoryBean errorPropertiesFromYamlFile() {
        final YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource(ERROR_FILE_NAME));
        return yaml;
    }
}
