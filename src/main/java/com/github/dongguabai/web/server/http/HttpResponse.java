package com.github.dongguabai.web.server.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dongguabai
 * @date 2024-01-26 15:25
 */
public class HttpResponse {

    private OutputStream os;

    public HttpResponse(OutputStream os) {
        this.os = os;
    }

    public void sendOK(String body) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        this.os.write(response.getBytes());
        this.os.flush();
    }
}