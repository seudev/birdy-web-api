package com.seudev.birdy.module.weather.impl.openweather;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;

import com.seudev.birdy.module.weather.model.Location;
import com.seudev.birdy.module.weather.model.WeatherInfo;
import com.seudev.birdy.util.Coordinate;

@ApplicationScoped
public class OpenWeatherInfoMapper {

    public WeatherInfo map(JsonObject json) {
        Location location = new Location(json.getString("name"), mapCoordinate(json.getJsonObject("coord")));
        return new WeatherInfo(location, mapTempeture(json.getJsonObject("main")));
    }

    private BigDecimal mapTempeture(JsonObject json) {
        return json.getJsonNumber("temp").bigDecimalValue();
    }

    private Coordinate mapCoordinate(JsonObject json) {
        if (json == null) {
            return null;
        }
        double latitude = json.getJsonNumber("lat").doubleValue();
        double longitude = json.getJsonNumber("lon").doubleValue();
        return Coordinate.from(latitude, longitude);
    }

}
