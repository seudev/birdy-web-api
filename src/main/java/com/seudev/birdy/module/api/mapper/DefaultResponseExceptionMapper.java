package com.seudev.birdy.module.api.mapper;

import static java.util.stream.Collectors.joining;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.WARN;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

@ApplicationScoped
public class DefaultResponseExceptionMapper implements ResponseExceptionMapper<WebApplicationException> {

    private static final Logger logger = Logger.getLogger(DefaultResponseExceptionMapper.class.getName());

    @Inject
    @Metric(name = "frequency")
    Meter meter;

    @Override
    public WebApplicationException toThrowable(Response response) {
        meter.mark();
        int clientStatus = response.getStatus();
        Family clientStatusFamily = response.getStatusInfo().getFamily();

        Status appStatus;
        Level logLevel;
        if (NOT_FOUND.getStatusCode() == clientStatus) {
            appStatus = NOT_FOUND;
            logLevel = DEBUG;
        } else if (Family.CLIENT_ERROR == clientStatusFamily) {
            appStatus = INTERNAL_SERVER_ERROR;
            logLevel = ERROR;
        } else {
            appStatus = SERVICE_UNAVAILABLE;
            logLevel = WARN;
        }

        String message = String.format("Client response error: %s => %s", clientStatus, appStatus.getStatusCode());

        logger.logf(logLevel, message);
        logClientResponseErrorDetail(response);

        if (NOT_FOUND == appStatus) {
            return new NotFoundException(message);
        } else if (Family.CLIENT_ERROR == appStatus.getFamily()) {
            return new ClientErrorException(message, appStatus);
        }
        return new WebApplicationException(message, appStatus);
    }

    private void logClientResponseErrorDetail(Response response) {
        if (logger.isDebugEnabled()) {
            String headers = response.getHeaders()
                .entrySet()
                .stream()
                .flatMap(e -> {
                    return e.getValue()
                        .stream()
                        .map(k -> String.format("    %s: %s", e.getKey(), k));
                }).collect(joining("\n"));

            Object body = response.readEntity(String.class);

            logger.debugf("Client response error detail:\nStatus: %s\n\nHeaders:\n%s\n\nBody:\n%s\n", response.getStatus(), headers, body);
        }
    }

}
