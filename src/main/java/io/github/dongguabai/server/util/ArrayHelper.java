package io.github.dongguabai.server.util;

/**
 * @author dongguabai
 * @date 2024-01-30 11:45
 */
public class ArrayHelper {

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    private ArrayHelper() {
    }
}