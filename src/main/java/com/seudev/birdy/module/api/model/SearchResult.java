package com.seudev.birdy.module.api.model;

import java.util.Collection;
import java.util.Optional;

public class SearchResult<E> {

    private final Optional<Long> count;

    private final Collection<E> records;

    public static <E> SearchResult<E> from(Collection<E> records) {
        return new SearchResult<>(records, null);
    }

    public static <E> SearchResult<E> from(Collection<E> records, Long count) {
        return new SearchResult<>(records, count);
    }

    private SearchResult(Collection<E> records, Long count) {
        this.records = records;
        this.count = Optional.ofNullable(count);
    }

    public Optional<Long> getCount() {
        return count;
    }

    public Collection<E> getRecords() {
        return records;
    }

}
