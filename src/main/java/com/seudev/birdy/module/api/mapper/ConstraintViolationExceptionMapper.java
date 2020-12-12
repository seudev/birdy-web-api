package com.seudev.birdy.module.api.mapper;

import static com.seudev.birdy.module.api.ApiResponses.validationError;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.seudev.birdy.module.api.model.ErrorMessage;

import org.eclipse.microprofile.metrics.Meter;
import org.jboss.logging.Logger;

import io.jaegertracing.internal.metrics.Metric;

@Provider
@ApplicationScoped
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = Logger.getLogger(ConstraintViolationExceptionMapper.class);

    @Inject
    @Metric(name = "frequency")
    Meter meter;

    @Override
    public Response toResponse(ConstraintViolationException ex) {
        meter.mark();
        logger.tracef(ex, ex.getMessage());

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ErrorMessage> errors = ErrorMessage.from(constraintViolations);
        return validationError(errors);
    }

}
