package com.seudev.birdy.module.api.mapper;

import static com.seudev.birdy.module.api.ApiResponses.validationError;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.seudev.birdy.module.api.ValidationException;

import org.eclipse.microprofile.metrics.Meter;
import org.jboss.logging.Logger;

import io.jaegertracing.internal.metrics.Metric;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger logger = Logger.getLogger(ValidationExceptionMapper.class);

    @Inject
    @Metric(name = "frequency")
    Meter meter;

    @Override
    public Response toResponse(ValidationException ex) {
        meter.mark();
        logger.tracef(ex, ex.getMessage());
        return validationError(ex.getErrors());
    }

}
