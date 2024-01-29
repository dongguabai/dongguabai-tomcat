package com.github.dongguabai.server.exp;

/**
 * @author dongguabai
 * @date 2024-01-26 16:02
 */
public class ServletException extends Exception {

    public ServletException(String message) {
        super(message);
    }

    public ServletException(String message, Throwable cause) {
        super(message, cause);
    }
}