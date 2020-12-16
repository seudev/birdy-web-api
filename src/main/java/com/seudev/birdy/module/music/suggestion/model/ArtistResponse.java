package com.seudev.birdy.module.music.suggestion.model;

import javax.json.bind.annotation.JsonbPropertyOrder;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonbPropertyOrder({ "id", "name", "externalUrl" })
@Schema(
    description = "The artist schema.",
    example = "{"
              + "  \"id\": \"246dkjvS1zLTtiykXe5h60\","
              + "  \"name\": \"Post Malone\","
              + "  \"externalUrl\": \"https://open.spotify.com/artist/246dkjvS1zLTtiykXe5h60\""
              + "}")
public class ArtistResponse {

    @Schema(description = "artist ID")
    private String id;

    @Schema(description = "artist name")
    private String name;

    @Schema(description = "external URL to listen the artist's musics")
    private String externalUrl;

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

}
