package com.seudev.birdy.model;

import java.math.BigDecimal;

public enum MusicGenre {

    PARTY,
    POP,
    ROCK,
    CLASSICAL;

    private static final BigDecimal THIRTY_DEGREES = new BigDecimal(30);

    private static final BigDecimal FIFTEEN_DEGREES = new BigDecimal(15);

    private static final BigDecimal FOURTEEN_DEGREES = new BigDecimal(14);

    private static final BigDecimal TEN_DEGREES = BigDecimal.TEN;

    public static MusicGenre fromTemperature(BigDecimal temperature) {
        if (temperature.compareTo(THIRTY_DEGREES) > 0) {
            return PARTY;
        } else if (temperature.compareTo(FIFTEEN_DEGREES) >= 0 && temperature.compareTo(THIRTY_DEGREES) <= 0) {
            return POP;
        } else if (temperature.compareTo(TEN_DEGREES) >= 0 && temperature.compareTo(FOURTEEN_DEGREES) <= 0) {
            return ROCK;
        }
        return CLASSICAL;
    }

}
