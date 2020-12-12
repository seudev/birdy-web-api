package com.seudev.birdy.module.api.model;

import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.STRING;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.ConstraintViolation;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonbPropertyOrder({ "code", "pointer", "message" })
@Schema(
    description = "Represents a validation error like a required property, duplicated value or other business validation.",
    example = "{ \"pointer\": \"/name\", \"message\": \"must be unique\" }")
public class ErrorMessage {

    @Schema(
        required = true,
        type = STRING,
        description = "JSON Pointer to the value in the request that caused the error",
        externalDocs = @ExternalDocumentation(url = "https://tools.ietf.org/html/rfc6901"))
    private final String pointer;

    @Schema(required = true, description = "human-readable error message")
    private final String message;

    public static ErrorMessage from(ConstraintViolation<?> constraintViolation) {
        return propertyError(getMessagePointer(constraintViolation), constraintViolation.getMessage());
    }

    public static List<ErrorMessage> from(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
            .map(ErrorMessage::from)
            .collect(Collectors.toList());
    }

    public static ErrorMessage rootError(String message) {
        return new ErrorMessage("/", message);
    }

    public static ErrorMessage propertyError(String pointer, String message) {
        return new ErrorMessage(pointer, message);
    }

    private ErrorMessage(String pointer, String message) {
        this.pointer = (((pointer == null) || pointer.isBlank()) ? "/" : pointer);
        this.message = message;
    }

    public String getPointer() {
        return pointer;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("ErrorMessage [pointer=").append(pointer)
            .append(", message=").append(message)
            .append("]").toString();
    }

    private static String getMessagePointer(ConstraintViolation<?> constraintViolation) {
        String path = constraintViolation.getPropertyPath().toString();
        path = removeFirstNode(path); // method name
        path = removeFirstNode(path); // paramether name
        path = "/" + path.replaceAll("\\.", "/");
        return path;
    }

    private static String removeFirstNode(String path) {
        int index = path.indexOf('.');
        if (index > 0) {
            return path.substring(index + 1);
        }
        return path;
    }

}
