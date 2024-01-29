package com.github.dongguabai.server.util;

import java.util.Collection;

/**
 * @author dongguabai
 * @date 2024-01-29 10:02
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    private CollectionUtils() {
    }
}