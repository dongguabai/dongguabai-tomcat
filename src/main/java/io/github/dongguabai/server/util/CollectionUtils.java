package io.github.dongguabai.server.util;

import java.util.Collection;

/**
 * @author dongguabai
 * @date 2024-01-29 10:02
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    private CollectionUtils() {
    }
}