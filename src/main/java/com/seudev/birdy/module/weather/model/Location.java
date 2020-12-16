package com.seudev.birdy.module.weather.model;

import java.util.Objects;

import com.seudev.birdy.util.Coordinate;

public class Location {

    private final String name;

    private final Coordinate coordinate;

    public Location(String name, Coordinate coordinate) {
        this.name = name;
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Location) {
            Location l = (Location) obj;
            return Objects.equals(coordinate, l.coordinate) && Objects.equals(name, l.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Location [coordinate=").append(coordinate)
            .append(", name=").append(name)
            .append("]").toString();
    }

}
