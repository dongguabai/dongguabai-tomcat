package com.github.dongguabai.server.exp;

/**
 * @author dongguabai
 * @date 2024-01-26 16:02
 */
public class ServerException extends Exception {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}