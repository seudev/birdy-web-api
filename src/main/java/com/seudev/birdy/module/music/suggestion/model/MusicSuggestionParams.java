package com.seudev.birdy.module.music.suggestion.model;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.QueryParam;

import com.seudev.birdy.util.Coordinate;

import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

public class MusicSuggestionParams {

    @NotBlank
    @QueryParam("q")
    @Parameter(
        name = "q", required = true, description = "city name or a latitude and longitude separated by comma",
        examples = {
            @ExampleObject(name = "by-city-name", summary = "search by a city name", value = "Amsterdam"),
            @ExampleObject(name = "by-city-name", summary = "search by a city name and state", value = "Amsterdam, North Holland"),
            @ExampleObject(
                name = "by-city-name", summary = "search by a city name, state and ISO country code", value = "Amsterdam, North Holland, NL"),
            @ExampleObject(name = "by-coordinate", summary = "search by latitude and longitude coordinates", value = "52.3589658,4.8841474"),
        })
    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Optional<Coordinate> getCoordinate() {
        return Coordinate.tryParse(q);
    }

}
