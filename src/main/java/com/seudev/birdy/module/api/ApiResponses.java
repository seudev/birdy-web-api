package com.seudev.birdy.module.api;

import static com.seudev.birdy.module.api.ApiHeaders.X_TOTAL_COUNT;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.seudev.birdy.module.api.model.ErrorMessage;
import com.seudev.birdy.module.api.model.SearchResult;
import com.seudev.birdy.util.Identifiable;

public final class ApiResponses {

    private static final int UNPROCESSABLE_ENTITY = 422;

    private static final String REF_PREFIX = "#/components/responses/";

    public static final String CREATED_CODE = "201";

    public static final String READED_CODE = "200";

    public static final String SEARCHED_CODE = "200";

    public static final String UPDATED = "UPDATED";

    public static final String UPDATED_CODE = "200";

    public static final String UPDATED_REF = REF_PREFIX + UPDATED;

    public static final String DELETED = "DELETED";

    public static final String DELETED_CODE = "200";

    public static final String DELETED_REF = REF_PREFIX + DELETED;

    public static final String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";

    public static final String NOT_FOUND_ERROR_CODE = "404";

    public static final String NOT_FOUND_ERROR_REF = REF_PREFIX + NOT_FOUND_ERROR;

    public static final String CONFLIT_ERROR = "CONFLIT_ERROR";

    public static final String CONFLIT_ERROR_CODE = "409";

    public static final String CONFLIT_ERROR_REF = REF_PREFIX + CONFLIT_ERROR;

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    public static final String VALIDATION_ERROR_CODE = "422";

    public static final String VALIDATION_ERROR_REF = REF_PREFIX + VALIDATION_ERROR;

    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    public static final String INTERNAL_SERVER_ERROR_CODE = "500";

    public static final String INTERNAL_SERVER_ERROR_REF = REF_PREFIX + INTERNAL_SERVER_ERROR;

    public static final String UNAVAILABLE_SERVICE_ERROR = "UNAVAILABLE_SERVICE_ERROR";

    public static final String UNAVAILABLE_SERVICE_ERROR_CODE = "503";

    public static final String UNAVAILABLE_SERVICE_ERROR_REF = REF_PREFIX + UNAVAILABLE_SERVICE_ERROR;

    private ApiResponses() {}

    public static URI buildLocationUri(UriInfo uriInfo, Object id) {
        return UriBuilder.fromPath(uriInfo.getPath())
            .path(id.toString())
            .build();
    }

    public static Response created(UriInfo uriInfo, Identifiable<?> entity) {
        URI locationUri = buildLocationUri(uriInfo, entity.getId());
        return Response.created(locationUri)
            .entity(entity)
            .build();
    }

    public static Response readed(Object entity) {
        return Response.ok(entity)
            .build();
    }

    public static Response searched(SearchResult<?> result) {
        if (result.getCount().isPresent()) {
            return Response.ok(result.getRecords())
                .header(X_TOTAL_COUNT, result.getCount().get())
                .build();
        }
        return searched(result.getRecords());
    }

    public static Response searched(Collection<?> records) {
        return Response.ok(records)
            .build();
    }

    public static Response updated() {
        return updated(null);
    }

    public static Response updated(Object entity) {
        return Response.ok(entity)
            .build();
    }

    public static Response deleted() {
        return deleted(null);
    }

    public static Response deleted(Object entity) {
        return Response.ok(entity)
            .build();
    }

    public static Response notFoundError() {
        return Response.status(Status.NOT_FOUND)
            .build();
    }

    public static Response conflictError() {
        return Response.status(Status.CONFLICT)
            .build();
    }

    public static Response validationError(List<ErrorMessage> errors) {
        return Response.status(UNPROCESSABLE_ENTITY)
            .entity(errors)
            .build();
    }

    public static Response internalServerError() {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
            .build();
    }

}
