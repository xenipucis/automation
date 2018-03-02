package es.automation.feeder.repository.mapper;


import es.automation.feeder.repository.mapper.helpers.LocalDateSupport;
import es.automation.feeder.repository.mapper.helpers.LocalDateTimeSupport;
import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

    private CustomBeanPropertyRowMapper(Class<T> mappedClass) {
        super(mappedClass);
    }

    public static <T> BeanPropertyRowMapper<T> newInstance(final Class<T> mappedClass) {
        return new CustomBeanPropertyRowMapper<>(mappedClass);
    }

    @Override
    protected void initBeanWrapper(final BeanWrapper beanWrapper) {
        beanWrapper.registerCustomEditor(LocalDateTime.class, new LocalDateTimeSupport());
        beanWrapper.registerCustomEditor(LocalDate.class, new LocalDateSupport());
    }
}


