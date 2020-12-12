package com.seudev.birdy.module.api;

import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

import java.util.List;

import com.seudev.birdy.module.api.model.ErrorMessage;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final List<ErrorMessage> errors;

    public ValidationException(List<ErrorMessage> errors) {
        this(errors, null);
    }

    public ValidationException(ErrorMessage error) {
        this(error, null);
    }

    public ValidationException(ErrorMessage error, Throwable cause) {
        this(singletonList(error), cause);
    }

    public ValidationException(List<ErrorMessage> errors, Throwable cause) {
        super(toString(errors), cause);
        this.errors = unmodifiableList(errors);
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    private static String toString(List<ErrorMessage> errors) {
        return errors.stream()
            .map(ErrorMessage::toString)
            .map(s -> String.format("    %s", s))
            .collect(collectingAndThen(joining(",\n"), s -> String.format("errors: [\n%s\n]", s)));
    }

}
