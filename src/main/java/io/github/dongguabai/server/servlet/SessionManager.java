package io.github.dongguabai.server.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author dongguabai
 * @date 2024-01-28 02:49
 */
public class SessionManager {

    private static final Map<String, HttpSession> SESSIONMAP = new HashMap<>();

    public static HttpSession getSession(String id) {
        return SESSIONMAP.get(id);
    }

    public static HttpSession createSession() {
        String id = "dongguabai_" + UUID.randomUUID().toString();
        HttpSession session = new HttpSession(id);
        SESSIONMAP.put(id, session);
        return session;
    }

    public static void invalidate(String id) {
        SESSIONMAP.remove(id);
    }

    public static void checkExpiredSessions() {
        SESSIONMAP.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}