package com.seudev.birdy.module.weather;

import java.util.Optional;

import com.seudev.birdy.module.weather.model.WeatherInfo;
import com.seudev.birdy.util.Coordinate;

public interface WeatherInfoService {

    public Optional<WeatherInfo> getCurrentWeatherInfoByQuery(String query);

    public Optional<WeatherInfo> getCurrentWeatherInfoByCoordinate(Coordinate coordinate);

}
