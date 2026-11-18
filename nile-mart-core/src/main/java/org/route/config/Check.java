package org.route.config;

import org.route.common.StoreException;

import java.util.Optional;
import java.util.function.Supplier;

public final class Check {
   public static <T>  T found( Optional<T> value, Supplier<?extends StoreException> error) {
        return value.orElseThrow(error);
    }

}
