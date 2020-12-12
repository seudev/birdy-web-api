package com.seudev.birdy.module.api;

import static com.seudev.birdy.module.api.ApiHeaders.X_TOTAL_COUNT;
import static com.seudev.birdy.module.api.ApiParameters.ID;
import static com.seudev.birdy.module.api.ApiParameters.LIMIT;
import static com.seudev.birdy.module.api.ApiParameters.OFFSET;
import static com.seudev.birdy.module.api.ApiParameters.USER_ID;
import static com.seudev.birdy.module.api.ApiResponses.CONFLIT_ERROR;
import static com.seudev.birdy.module.api.ApiResponses.CONFLIT_ERROR_CODE;
import static com.seudev.birdy.module.api.ApiResponses.DELETED;
import static com.seudev.birdy.module.api.ApiResponses.DELETED_CODE;
import static com.seudev.birdy.module.api.ApiResponses.INTERNAL_SERVER_ERROR;
import static com.seudev.birdy.module.api.ApiResponses.INTERNAL_SERVER_ERROR_CODE;
import static com.seudev.birdy.module.api.ApiResponses.NOT_FOUND_ERROR;
import static com.seudev.birdy.module.api.ApiResponses.NOT_FOUND_ERROR_CODE;
import static com.seudev.birdy.module.api.ApiResponses.UNAVAILABLE_SERVICE_ERROR;
import static com.seudev.birdy.module.api.ApiResponses.UNAVAILABLE_SERVICE_ERROR_CODE;
import static com.seudev.birdy.module.api.ApiResponses.UPDATED;
import static com.seudev.birdy.module.api.ApiResponses.UPDATED_CODE;
import static com.seudev.birdy.module.api.ApiResponses.VALIDATION_ERROR;
import static com.seudev.birdy.module.api.ApiResponses.VALIDATION_ERROR_CODE;
import static com.seudev.birdy.module.api.ApiSchemas.CREATED_AT;
import static com.seudev.birdy.module.api.ApiTags.MUSIC_SUGGESTION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.eclipse.microprofile.openapi.annotations.enums.ParameterIn.PATH;
import static org.eclipse.microprofile.openapi.annotations.enums.ParameterIn.QUERY;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.INTEGER;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.STRING;

import java.util.UUID;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.seudev.birdy.module.api.model.ErrorMessage;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.servers.ServerVariable;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Singleton
@ApplicationPath("/api")
@OpenAPIDefinition(
    tags = {
        @Tag(name = MUSIC_SUGGESTION, description = "music suggestion operations."),
    },
    info = @Info(
        version = "3.0.3",
        title = "Birdy API",
        description = "A API to provide music suggestion operations.",
        contact = @Contact(name = "API Suport", url = "https://seudev.com", email = "contact@seudev.com"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")),
    servers = {
        @Server(
            description = "Production Server", url = "{protocol}://birdy.seudev.com",
            variables = @ServerVariable(name = "protocol", defaultValue = "https", enumeration = { "https", "http" })),
        @Server(
            description = "Staging Server", url = "{protocol}://staging-birdy.seudev.com",
            variables = @ServerVariable(name = "protocol", defaultValue = "https", enumeration = { "https", "http" })),
        @Server(
            description = "Development Server", url = "{protocol}://dev-birdy.seudev.com",
            variables = @ServerVariable(name = "protocol", defaultValue = "https", enumeration = { "https", "http" })),
        @Server(description = "Local Server", url = "http://localhost:8080")
    },
    components = @Components(
        headers = {
            @Header(
                name = X_TOTAL_COUNT, description = "total records count",
                schema = @Schema(type = INTEGER, format = "int64", minimum = "0", readOnly = true)),
        },
        parameters = {
            @Parameter(
                name = ID, description = "resource ID", required = true, in = PATH,
                schema = @Schema(
                    description = "A Universally Unique IDentifier (UUID)",
                    example = "d216e7a1-6dd2-4b1b-b281-c0d86c190913",
                    type = STRING,
                    implementation = UUID.class,
                    externalDocs = @ExternalDocumentation(url = "https://www.ietf.org/rfc/rfc4122.txt"))),
            @Parameter(
                name = USER_ID, description = "user ID", required = true, in = PATH,
                schema = @Schema(
                    description = "A Universally Unique IDentifier (UUID)",
                    example = "d216e7a1-6dd2-4b1b-b281-c0d86c190913",
                    type = STRING,
                    implementation = UUID.class,
                    externalDocs = @ExternalDocumentation(url = "https://www.ietf.org/rfc/rfc4122.txt"))),
            @Parameter(
                name = OFFSET, required = false, in = QUERY, description = "number of records to skip",
                schema = @Schema(type = INTEGER, format = "int32", minimum = "0", defaultValue = "0", writeOnly = true)),
            @Parameter(
                name = LIMIT, required = false, in = QUERY, description = "max records to return",
                schema = @Schema(type = INTEGER, format = "int32", minimum = "0", maximum = "100", defaultValue = "15", writeOnly = true))
        },
        responses = {
            @APIResponse(name = UPDATED, responseCode = UPDATED_CODE, description = "The resource was successfully updated."),
            @APIResponse(name = DELETED, responseCode = DELETED_CODE, description = "The resource was successfully deleted."),
            @APIResponse(name = NOT_FOUND_ERROR, responseCode = NOT_FOUND_ERROR_CODE, description = "The resource was not found."),
            @APIResponse(
                name = CONFLIT_ERROR, responseCode = CONFLIT_ERROR_CODE,
                description = "The request data are outdated with the current state of the resource."),
            @APIResponse(
                name = VALIDATION_ERROR,
                responseCode = VALIDATION_ERROR_CODE,
                description = "Some validation error like required property, duplicated value or other business validation.",
                content = @Content(
                    mediaType = APPLICATION_JSON, schema = @Schema(readOnly = true, type = ARRAY, implementation = ErrorMessage.class))),
            @APIResponse(name = INTERNAL_SERVER_ERROR, responseCode = INTERNAL_SERVER_ERROR_CODE, description = "Occur some internal server error."),
            @APIResponse(
                name = UNAVAILABLE_SERVICE_ERROR, responseCode = UNAVAILABLE_SERVICE_ERROR_CODE, description = "The service is unavailable."),
        },
        schemas = {
            @Schema(name = CREATED_AT, type = STRING, description = "resource creation date")
        }))
public class App extends Application {

}
