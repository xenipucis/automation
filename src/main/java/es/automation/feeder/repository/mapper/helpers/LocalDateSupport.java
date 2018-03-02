package es.automation.feeder.repository.mapper.helpers;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSupport extends PropertyEditorSupport {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override public void setAsText(final String text) {
        setValue(LocalDate.parse(text));
    }

    @Override public void setValue(final Object value) {
        super.setValue(value == null || value instanceof LocalDate ? value : LocalDate.parse(value.toString(), FORMATTER));
    }

    @Override public LocalDate getValue() {
        return (LocalDate) super.getValue();
    }

    @Override public String getAsText() {
        return getValue().toString();
    }
}
