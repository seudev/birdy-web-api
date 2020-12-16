package com.seudev.birdy.module.weather.impl.openweather;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

import java.util.Optional;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.seudev.birdy.module.api.mapper.DefaultResponseExceptionMapper;
import com.seudev.birdy.module.weather.WeatherInfoService;
import com.seudev.birdy.module.weather.model.WeatherInfo;
import com.seudev.birdy.util.Coordinate;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.cache.CacheResult;

// Metrics
@Timed(name = "time")
@Metered(name = "frequency")
@ConcurrentGauge(name = "concurrent_count")
@ApplicationScoped
public class OpenWeatherService implements WeatherInfoService {

    @Inject
    @ConfigProperty(name = "openweather.appId")
    String appId;

    @Inject
    @ConfigProperty(name = "openweather.units")
    String units;

    @Inject
    @RestClient
    OpenWeatherRestClient restClient;

    @Inject
    OpenWeatherInfoMapper mapper;

    @Override
    @CacheResult(cacheName = "weather-cache")
    public Optional<WeatherInfo> getCurrentWeatherInfoByQuery(String query) {
        return getCurrentWeatherInfo(() -> {
            JsonObject json = restClient.getByCityName(appId, units, query);
            return mapper.map(json);
        });
    }

    @Override
    @CacheResult(cacheName = "weather-cache")
    public Optional<WeatherInfo> getCurrentWeatherInfoByCoordinate(Coordinate coordinate) {
        return getCurrentWeatherInfo(() -> {
            JsonObject json = restClient.getByCoordinate(appId, units, coordinate.getLatitude(), coordinate.getLongitude());
            return mapper.map(json);
        });
    }

    private Optional<WeatherInfo> getCurrentWeatherInfo(Supplier<WeatherInfo> supplier) {
        try {
            WeatherInfo weatherInfo = supplier.get();
            return Optional.of(weatherInfo);
        } catch (NotFoundException ex) {
            return Optional.empty();
        }
    }

    @Path("/weather")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @RegisterRestClient(configKey = "openweather-web-api")
    @RegisterProvider(DefaultResponseExceptionMapper.class)
    @ApplicationScoped
    public interface OpenWeatherRestClient {

        @GET
        public JsonObject getByCityName(
            @QueryParam("appid") String appId,
            @QueryParam("units") String units,
            @QueryParam("q") String query);

        @GET
        public JsonObject getByCoordinate(
            @QueryParam("appid") String appId,
            @QueryParam("units") String units,
            @QueryParam("lat") double latitude,
            @QueryParam("lon") double longitude);

    }

}
