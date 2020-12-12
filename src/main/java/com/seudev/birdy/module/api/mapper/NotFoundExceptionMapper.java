package com.seudev.birdy.module.api.mapper;

import static com.seudev.birdy.module.api.ApiResponses.notFoundError;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.metrics.Meter;
import org.jboss.logging.Logger;

import io.jaegertracing.internal.metrics.Metric;

@Provider
@ApplicationScoped
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final Logger logger = Logger.getLogger(NotFoundExceptionMapper.class);

    @Inject
    @Metric(name = "frequency")
    Meter meter;

    @Override
    public Response toResponse(NotFoundException ex) {
        meter.mark();
        logger.debugf(ex, ex.getMessage());
        return notFoundError();
    }

}
