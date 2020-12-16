package com.seudev.birdy.module.music.suggestion.impl.spotify;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.seudev.birdy.model.MusicGenre;
import com.seudev.birdy.module.api.mapper.DefaultResponseExceptionMapper;
import com.seudev.birdy.module.api.model.SearchResult;
import com.seudev.birdy.module.music.suggestion.MusicSuggestionRepository;
import com.seudev.birdy.module.music.suggestion.model.MusicResponse;
import com.seudev.birdy.module.spotify.auth.SpotifyAuthorizationProvider;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Metrics
@Timed(name = "time")
@Metered(name = "frequency")
@ConcurrentGauge(name = "concurrent_count")
@ApplicationScoped
public class SpotifyMusicSuggestionRepository implements MusicSuggestionRepository {

    @Inject
    SpotifyAuthorizationProvider authorizationProvider;

    @Inject
    @RestClient
    SpotifyRecommendationsRestClient restClient;

    @Inject
    SpotifyMusicMapper musicMapper;

    @Inject
    @ConfigProperty(name = "spotify.music.suggestion.limit")
    int limit;

    @Override
    public SearchResult<MusicResponse> search(MusicGenre genre) {
        String authorization = authorizationProvider.getAuthorization();
        JsonObject json = restClient.search(authorization, mapGenre(genre), limit);
        List<MusicResponse> musics = musicMapper.mapMusics(json);
        return SearchResult.from(musics, (long) musics.size());
    }

    private String mapGenre(MusicGenre genre) {
        return genre.name().toLowerCase();
    }

    @Path("/recommendations")
    @Produces(APPLICATION_JSON)
    @RegisterRestClient(configKey = "spotify-recomendations-web-api")
    @RegisterProvider(DefaultResponseExceptionMapper.class)
    @ApplicationScoped
    public static interface SpotifyRecommendationsRestClient {

        @GET
        public JsonObject search(
            @HeaderParam(AUTHORIZATION) String authorization,
            @QueryParam("seed_genres") String genre,
            @QueryParam("limit") int limit);

    }

}
