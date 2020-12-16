package com.seudev.birdy.module.music.suggestion.model;

import java.util.List;

import javax.json.bind.annotation.JsonbPropertyOrder;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonbPropertyOrder({ "id", "name", "externalUrl", "embedUrl", "album", "artists" })
@Schema(
    description = "The music schema.",
    example = "[{"
              + "  \"id\": \"6eT7xZZlB2mwyzJ2sUKG6w\","
              + "  \"name\": \"White Iverson\","
              + "  \"externalUrl\": \"https://open.spotify.com/track/6eT7xZZlB2mwyzJ2sUKG6w\","
              + "  \"embedUrl\": \"https://open.spotify.com/embed/track/6eT7xZZlB2mwyzJ2sUKG6w\","
              + "  \"album\": {"
              + "    \"id\": \"5s0rmjP8XOPhP6HhqOhuyC\","
              + "    \"name\": \"Stoney (Deluxe)\","
              + "    \"externalUrl\": \"https://open.spotify.com/album/5s0rmjP8XOPhP6HhqOhuyC\","
              + "    \"embedUrl\": \"https://open.spotify.com/embed/album/5s0rmjP8XOPhP6HhqOhuyC\","
              + "    \"images\": ["
              + "      {"
              + "        \"url\": \"https://i.scdn.co/image/ab67616d0000485155404f712deb84d0650a4b41\","
              + "        \"width\": 64,"
              + "        \"heigth\": 64"
              + "      },"
              + "      {"
              + "        \"url\": \"https://i.scdn.co/image/ab67616d00001e0255404f712deb84d0650a4b41\","
              + "        \"width\": 300,"
              + "        \"heigth\": 300"
              + "      }"
              + "    ]"
              + "  },"
              + "  \"artists\": ["
              + "    {"
              + "      \"id\": \"246dkjvS1zLTtiykXe5h60\","
              + "      \"name\": \"Post Malone\","
              + "      \"externalUrl\": \"https://open.spotify.com/artist/246dkjvS1zLTtiykXe5h60\""
              + "    }"
              + "  ]"
              + "}]")
public class MusicResponse {

    @Schema(description = "music ID")
    private String id;

    @Schema(description = "music name")
    private String name;

    @Schema(description = "external URL to listen the music")
    private String externalUrl;

    @Schema(description = "URL to listen the music in an embed iframe")
    private String embedUrl;

    @Schema(description = "music album")
    private AlbumResponse album;

    @Schema(description = "music artists")
    private List<ArtistResponse> artists;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public AlbumResponse getAlbum() {
        return album;
    }

    public void setAlbum(AlbumResponse album) {
        this.album = album;
    }

    public List<ArtistResponse> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistResponse> artists) {
        this.artists = artists;
    }

}
