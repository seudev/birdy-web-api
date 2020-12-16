package com.seudev.birdy.module.music.suggestion;

import com.seudev.birdy.model.MusicGenre;
import com.seudev.birdy.module.api.model.SearchResult;
import com.seudev.birdy.module.music.suggestion.model.MusicResponse;

public interface MusicSuggestionRepository {

    public SearchResult<MusicResponse> search(MusicGenre genre);

}
