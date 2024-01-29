package io.github.dongguabai.server.core;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

/**
 * @author dongguabai
 * @date 2024-01-30 09:42
 */
public class LinkedProperties extends Properties {

    private final LinkedList<Object> keys = new LinkedList<>();

    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> names = new LinkedHashSet<>();
        for (Object key : keys) {
            names.add((String) key);
        }
        return names;
    }
}