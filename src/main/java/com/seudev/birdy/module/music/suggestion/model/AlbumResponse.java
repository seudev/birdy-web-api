package com.seudev.birdy.module.music.suggestion.model;

import java.util.List;

import javax.json.bind.annotation.JsonbPropertyOrder;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonbPropertyOrder({ "id", "name", "externalUrl", "embedUrl", "images" })
@Schema(
    description = "The music album schema.",
    example = "{"
              + "  \"id\": \"5s0rmjP8XOPhP6HhqOhuyC\","
              + "  \"name\": \"Stoney (Deluxe)\","
              + "  \"externalUrl\": \"https://open.spotify.com/album/5s0rmjP8XOPhP6HhqOhuyC\","
              + "  \"embedUrl\": \"https://open.spotify.com/embed/album/5s0rmjP8XOPhP6HhqOhuyC\","
              + "  \"images\": ["
              + "    {"
              + "      \"url\": \"https://i.scdn.co/image/ab67616d0000485155404f712deb84d0650a4b41\","
              + "      \"width\": 64,"
              + "      \"heigth\": 64"
              + "    },"
              + "    {"
              + "      \"url\": \"https://i.scdn.co/image/ab67616d00001e0255404f712deb84d0650a4b41\","
              + "      \"width\": 300,"
              + "      \"heigth\": 300"
              + "    }"
              + "  ]"
              + "}")
public class AlbumResponse {

    @Schema(description = "album ID")
    private String id;

    @Schema(description = "album name")
    private String name;

    @Schema(description = "external URL to listen the album")
    private String externalUrl;

    @Schema(description = "URL to listen the album in an embed iframe")
    private String embedUrl;

    @Schema(description = "album images")
    private List<ImageResponse> images;

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

    public List<ImageResponse> getImages() {
        return images;
    }

    public void setImages(List<ImageResponse> images) {
        this.images = images;
    }

}
