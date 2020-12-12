package com.seudev.birdy.module.api.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.DefaultOptionsMethodException;

@Provider
@ApplicationScoped
public class DefaultOptionsMethodExceptionMapper implements ExceptionMapper<DefaultOptionsMethodException> {

    @Override
    public Response toResponse(DefaultOptionsMethodException ex) {
        return Response.ok()
            .build();
    }

}
