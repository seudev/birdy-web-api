package com.seudev.birdy.module.music.suggestion;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.seudev.birdy.module.api.model.SearchResult;
import com.seudev.birdy.module.music.suggestion.model.MusicResponse;
import com.seudev.birdy.module.music.suggestion.model.MusicSuggestionParams;

public interface MusicSuggestionService {

    public SearchResult<MusicResponse> search(@Valid @NotNull MusicSuggestionParams params);

}
