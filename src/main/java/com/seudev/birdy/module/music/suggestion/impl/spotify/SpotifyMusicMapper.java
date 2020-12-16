package com.seudev.birdy.module.music.suggestion.impl.spotify;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.core.UriBuilder;

import com.seudev.birdy.module.music.suggestion.model.AlbumResponse;
import com.seudev.birdy.module.music.suggestion.model.ArtistResponse;
import com.seudev.birdy.module.music.suggestion.model.ImageResponse;
import com.seudev.birdy.module.music.suggestion.model.MusicResponse;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SpotifyMusicMapper {

    @Inject
    @ConfigProperty(name = "spotify.embed.music.url.template")
    String embedMusicUrlTemplate;

    @Inject
    @ConfigProperty(name = "spotify.embed.album.url.template")
    String embedAlbumUrlTemplate;

    private UriBuilder embedMusicUrlBuilder;

    private UriBuilder embedAlbumUrlBuilder;

    @PostConstruct
    void init() {
        embedMusicUrlBuilder = UriBuilder.fromUri(embedMusicUrlTemplate);
        embedAlbumUrlBuilder = UriBuilder.fromUri(embedAlbumUrlTemplate);
    }

    public List<MusicResponse> mapMusics(JsonObject json) {
        JsonArray tracks = json.getJsonArray("tracks");
        if (tracks == null) {
            return emptyList();
        }

        return tracks.stream()
            .map(JsonValue::asJsonObject)
            .map(this::mapMusic)
            .collect(toList());
    }

    private MusicResponse mapMusic(JsonObject json) {
        MusicResponse music = new MusicResponse();
        music.setAlbum(mapAlbum(json.getJsonObject("album")));
        music.setId(json.getString("id"));
        music.setName(json.getString("name"));
        music.setExternalUrl(mapExternalUrl(json.getJsonObject("external_urls")));
        music.setEmbedUrl(embedMusicUrlBuilder.build(music.getId()).toString());
        music.setArtists(mapArtists(json.getJsonArray("artists")));
        return music;
    }

    private List<ArtistResponse> mapArtists(JsonArray array) {
        if (array == null) {
            return emptyList();
        }

        return array.stream()
            .map(JsonValue::asJsonObject)
            .map(a -> {
                ArtistResponse artist = new ArtistResponse();
                artist.setId(a.getString("id"));
                artist.setName(a.getString("name"));
                artist.setExternalUrl(mapExternalUrl(a.getJsonObject("external_urls")));
                return artist;
            })
            .collect(toList());
    }

    private AlbumResponse mapAlbum(JsonObject json) {
        if (json == null) {
            return null;
        }

        AlbumResponse album = new AlbumResponse();
        album.setId(json.getString("id"));
        album.setName(json.getString("name"));
        album.setExternalUrl(mapExternalUrl(json.getJsonObject("external_urls")));
        album.setEmbedUrl(embedAlbumUrlBuilder.build(album.getId()).toString());
        album.setImages(mapImages(json.getJsonArray("images")));
        return album;
    }

    private String mapExternalUrl(JsonObject json) {
        if (json != null) {
            return json.getString("spotify");
        }
        return null;
    }

    private List<ImageResponse> mapImages(JsonArray array) {
        if (array == null) {
            return emptyList();
        }

        return array.stream()
            .map(JsonValue::asJsonObject)
            .map(a -> new ImageResponse(a.getString("url"), a.getInt("width"), a.getInt("height")))
            .sorted()
            .collect(toList());
    }

}
