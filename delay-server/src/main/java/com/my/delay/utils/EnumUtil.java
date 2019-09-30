package com.my.delay.utils;


import java.lang.reflect.Array;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by frank.gou on 2019/8/15.
 */
public class EnumUtil {

    public static <T, E extends Enum<E>> Function<T, E> lookupMap(Class<E> clazz, Function<E, T> mapper) {
        E[] emptyArray = (E[]) Array.newInstance(clazz, 0);
        return lookupMap(EnumSet.allOf(clazz).toArray(emptyArray), mapper);
    }

    public static <T, E extends Enum<E>> Function<T, E> lookupMap(E[] values, Function<E, T> mapper) {
        Map<T, E> index = Maps.newHashMapWithExpectedSize(values.length);
        for (E value : values) {
            index.put(mapper.apply(value), value);
        }
        return (T key) -> index.get(key);
    }
}
