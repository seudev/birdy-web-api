package com.seudev.birdy.util;

import java.util.Objects;

public interface Identifiable<T> {

    public T getId();

    public static <I extends Identifiable<?>> int hashCode(I identifiable) {
        return Objects.hashCode(identifiable.getId());
    }

    public static <I extends Identifiable<?>> boolean equals(Class<I> identifiableClass, I identifiable, Object obj) {
        if (identifiable == obj) {
            return true;
        }
        if (identifiableClass.isInstance(obj)) {
            I other = identifiableClass.cast(obj);
            return Objects.equals(identifiable.getId(), other.getId());
        }
        return false;
    }

}
