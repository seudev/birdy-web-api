package com.seudev.birdy.module.weather.model;

import java.math.BigDecimal;
import java.util.Objects;

public class WeatherInfo {

    private final Location location;

    private final BigDecimal temperature;

    public WeatherInfo(Location location, BigDecimal temperature) {
        this.location = location;
        this.temperature = temperature;
    }

    public Location getLocation() {
        return location;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, temperature);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WeatherInfo) {
            WeatherInfo w = (WeatherInfo) obj;
            return Objects.equals(location, w.location)
                && Objects.equals(temperature, w.temperature);
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("WeatherInfo [location=").append(location)
            .append(", temperature=").append(temperature)
            .append("]").toString();
    }

}
