package com.github.dongguabai.server.connector;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dongguabai
 * @date 2024-01-28 01:44
 */
public abstract class AbstractConnector implements Connector {

    protected void sendNotFound(OutputStream outputStream) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}