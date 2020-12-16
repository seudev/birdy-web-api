package com.seudev.birdy.util;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

    private static final Pattern CORDINATE_PATTERN = Pattern.compile("(-?\\d+(\\.\\d+)?)\\s*,\\s*(-?\\d+(\\.\\d+)?)");

    private final double latitude;

    private final double longitude;

    public static Coordinate parse(String coordinate) {
        Optional<Coordinate> optional = tryParse(coordinate);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new IllegalArgumentException(String.format("Coordinate parse failed: %s", coordinate));
    }

    public static Optional<Coordinate> tryParse(String coordinate) {
        if ((coordinate == null) || coordinate.isBlank()) {
            return Optional.empty();
        }

        Matcher matcher = CORDINATE_PATTERN.matcher(coordinate);
        if (matcher.find()) {
            double latitude = Double.parseDouble(matcher.group(1));
            double longitude = Double.parseDouble(matcher.group(3));

            return Optional.of(new Coordinate(latitude, longitude));
        }
        return Optional.empty();
    }

    public static Coordinate from(double latitude, double longitude) {
        return new Coordinate(latitude, longitude);
    }

    private Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Coordinate) {
            Coordinate c = (Coordinate) obj;

            return ((latitude == c.latitude)
                && (longitude == c.longitude));
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Coordinate [latitude=").append(latitude)
            .append(", longitude=").append(longitude)
            .append("]").toString();
    }

}
