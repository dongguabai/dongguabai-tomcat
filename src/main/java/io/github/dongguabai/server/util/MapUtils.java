package io.github.dongguabai.server.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongguabai
 * @date 2024-01-28 01:00
 */
public class MapUtils {

    public static <K, V> Map<K, V> createHashMapWithExpectedSize(int expectedSize) {
        int initialCapacity = (int) Math.ceil(expectedSize / 0.75);
        return new HashMap<>(initialCapacity);
    }

    private MapUtils() {
    }
}