package com.seudev.birdy.module.spotify.auth;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.time.Duration;
import java.util.Base64;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.seudev.birdy.module.api.mapper.DefaultResponseExceptionMapper;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class SpotifyAuthorizationProvider {

    @Inject
    @RestClient
    SpotifyTokenRestClient restClient;

    @Inject
    @ConfigProperty(name = "spotify.auth.token.validationDelay")
    Duration validationDelay;

    @Inject
    @ConfigProperty(name = "spotify.auth.token.grantType")
    String grantType;

    @Inject
    @ConfigProperty(name = "spotify.auth.client.id")
    String clientId;

    @Inject
    @ConfigProperty(name = "spotify.auth.client.secret")
    String clientSecret;

    private SpotifyToken token;

    // Metrics
    @Timed(name = "time")
    @Metered(name = "frequency")
    public synchronized String getAuthorization() {
        if (isExpired()) {
            String clientAuthorization = getClientAuthorization();
            token = restClient.generateToken(clientAuthorization, grantType);
        }
        return token.getAuthorization();
    }

    private boolean isExpired() {
        return ((token == null) || token.isExpired(validationDelay));
    }

    private String getClientAuthorization() {
        String encodedCredentials = Base64.getEncoder()
            .encodeToString(String.format("%s:%s", clientId, clientSecret).getBytes(UTF_8));

        return String.format("Basic %s", encodedCredentials);
    }

    @Path("/token")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_FORM_URLENCODED)
    @RegisterRestClient(configKey = "spotify-token-web-api")
    @RegisterProvider(DefaultResponseExceptionMapper.class)
    @ApplicationScoped
    public interface SpotifyTokenRestClient {

        @POST
        public SpotifyToken generateToken(
            @HeaderParam(AUTHORIZATION) String authorization,
            @FormParam("grant_type") String grantType);

    }

}
