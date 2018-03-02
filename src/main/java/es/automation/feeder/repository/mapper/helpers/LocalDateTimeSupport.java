package es.automation.feeder.repository.mapper.helpers;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class LocalDateTimeSupport extends PropertyEditorSupport {
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 3, true)
            .toFormatter();

    @Override public void setAsText(final String text) {
        setValue(LocalDateTime.parse(text));
    }

    @Override public void setValue(final Object value) {
        super.setValue(value == null || value instanceof LocalDateTime ? value : LocalDateTime.parse(value.toString(), FORMATTER));
    }

    @Override public LocalDateTime getValue() {
        return (LocalDateTime) super.getValue();
    }

    @Override public String getAsText() {
        return getValue().toString();
    }
}