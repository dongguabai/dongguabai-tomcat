package io.github.dongguabai.server.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongguabai
 * @date 2024-01-28 02:45
 */
public class HttpSession {

    private String id;

    private Map<String, Object> attributes = new HashMap<>();

    private long lastAccessedTime;

    private int maxInactiveInterval;

    public HttpSession(String id) {
        this.id = id;
        this.lastAccessedTime = System.currentTimeMillis();
        // 默认为30分钟
        this.maxInactiveInterval = 30 * 60;
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - lastAccessedTime > maxInactiveInterval * 1000;
    }

    public void access() {
        this.lastAccessedTime = System.currentTimeMillis();
    }

    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }
}