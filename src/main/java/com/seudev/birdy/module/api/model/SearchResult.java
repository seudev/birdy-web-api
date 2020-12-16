package com.seudev.birdy.module.api.model;

import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.Optional;

public class SearchResult<E> {

    private static final SearchResult<?> EMPTY_SEARCH_RESULT = new SearchResult<>(emptyList(), 0L);

    private final Optional<Long> count;

    private final Collection<E> records;

    @SuppressWarnings("unchecked")
    public static <E> SearchResult<E> empty() {
        return (SearchResult<E>) EMPTY_SEARCH_RESULT;
    }

    public static <E> SearchResult<E> from(Collection<E> records) {
        return from(records, null);
    }

    public static <E> SearchResult<E> from(Collection<E> records, Long count) {
        if (records.isEmpty() && ((count == null) || (count == 0L))) {
            return empty();
        }
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
