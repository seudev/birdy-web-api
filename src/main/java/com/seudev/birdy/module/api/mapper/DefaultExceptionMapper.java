package com.seudev.birdy.module.api.mapper;

import static com.seudev.birdy.module.api.ApiResponses.internalServerError;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.jboss.logging.Logger;

@Provider
@ApplicationScoped
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = Logger.getLogger(DefaultExceptionMapper.class);

    @Inject
    @Metric(name = "frequency")
    Meter meter;

    @Override
    public Response toResponse(Exception ex) {
        meter.mark();
        if (ex instanceof NotAllowedException) {
            logger.debugf(ex, ex.getMessage());
            NotAllowedException ex2 = (NotAllowedException) ex;
            return ex2.getResponse();
        }
        logger.errorf(ex, ex.getMessage());
        return internalServerError();
    }

}
