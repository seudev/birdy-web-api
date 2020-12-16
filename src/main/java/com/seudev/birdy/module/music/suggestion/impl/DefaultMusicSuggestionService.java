package com.seudev.birdy.module.music.suggestion.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.seudev.birdy.model.MusicGenre;
import com.seudev.birdy.module.api.model.SearchResult;
import com.seudev.birdy.module.music.suggestion.MusicSuggestionRepository;
import com.seudev.birdy.module.music.suggestion.MusicSuggestionService;
import com.seudev.birdy.module.music.suggestion.model.MusicResponse;
import com.seudev.birdy.module.music.suggestion.model.MusicSuggestionParams;
import com.seudev.birdy.module.weather.WeatherInfoService;
import com.seudev.birdy.module.weather.model.WeatherInfo;

@ApplicationScoped
public class DefaultMusicSuggestionService implements MusicSuggestionService {

    @Inject
    MusicSuggestionRepository musicSuggestionRepository;

    @Inject
    WeatherInfoService weatherInfoService;

    @Override
    public SearchResult<MusicResponse> search(@Valid @NotNull MusicSuggestionParams params) {
        return params.getCoordinate()
            .map(weatherInfoService::getCurrentWeatherInfoByCoordinate)
            .orElseGet(() -> weatherInfoService.getCurrentWeatherInfoByQuery(params.getQ()))
            .map(WeatherInfo::getTemperature)
            .map(MusicGenre::fromTemperature)
            .map(musicSuggestionRepository::search)
            .orElseGet(SearchResult::empty);
    }

}
