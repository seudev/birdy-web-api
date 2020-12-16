package com.seudev.birdy.module.music.suggestion.model;

import java.util.Comparator;

import javax.json.bind.annotation.JsonbPropertyOrder;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonbPropertyOrder({ "url", "width", "heigth" })
@Schema(
    description = "The album image schema.",
    example = "{"
              + "  \"url\": \"https://i.scdn.co/image/ab67616d0000485155404f712deb84d0650a4b41\","
              + "  \"width\": 64,"
              + "  \"heigth\": 64"
              + "}")
public class ImageResponse implements Comparable<ImageResponse> {

    private static final Comparator<ImageResponse> COMPARATOR = Comparator.comparing(ImageResponse::getWidth)
        .thenComparing(ImageResponse::getHeigth);

    @Schema(description = "URL to view the image")
    private String url;

    @Schema(description = "image width")
    private int width;

    @Schema(description = "image heigth")
    private int heigth;

    public ImageResponse(String url, int width, int heigth) {
        this.url = url;
        this.width = width;
        this.heigth = heigth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    @Override
    public int compareTo(ImageResponse o) {
        return COMPARATOR.compare(this, o);
    }

}
