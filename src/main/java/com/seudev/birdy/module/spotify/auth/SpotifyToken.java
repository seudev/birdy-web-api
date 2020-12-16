package com.seudev.birdy.module.spotify.auth;

import java.time.Duration;
import java.time.Instant;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class SpotifyToken {

    @JsonbTransient
    private final Instant createdAt;

    @JsonbProperty("access_token")
    private String accessToken;

    @JsonbProperty("token_type")
    private String tokenType;

    @JsonbProperty("expires_in")
    private long expiresIn;

    @JsonbProperty("scope")
    private String scope;

    public SpotifyToken() {
        this.createdAt = Instant.now();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorization() {
        return String.format("%s %s", tokenType, accessToken);
    }

    public boolean isExpired(Duration validationDelay) {
        return createdAt.plusSeconds(expiresIn)
            .compareTo(Instant.now().plus(validationDelay)) <= 0;
    }

}
