package com.seudev.birdy.module.api.mapper;

import static com.seudev.birdy.module.api.ApiResponses.internalServerError;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.metrics.Meter;
import org.jboss.logging.Logger;

import io.jaegertracing.internal.metrics.Metric;

@Provider
@ApplicationScoped
public class JavaxValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger logger = Logger.getLogger(JavaxValidationExceptionMapper.class);

    @Inject
    @Metric(name = "frequency")
    Meter meter;

    @Override
    public Response toResponse(ValidationException ex) {
        meter.mark();
        logger.errorf(ex, ex.getMessage());
        return internalServerError();
    }

}
