package io.github.dongguabai.server.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author dongguabai
 * @date 2024-01-26 15:25
 */
public class HttpServletResponse {

    private OutputStream os;

    public HttpServletResponse(OutputStream os) {
        this.os = os;
    }

    public void sendJson(String json) throws IOException {
        byte[] bodyBytes = json.getBytes(StandardCharsets.UTF_8);
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json; charset=UTF-8\r\n" +
                "Content-Length: " + bodyBytes.length + "\r\n" +
                "\r\n";
        this.os.write(response.getBytes(StandardCharsets.UTF_8));
        this.os.write(bodyBytes);
        this.os.flush();
    }

    public void sendHtml(String html) throws IOException {
        byte[] bodyBytes = html.getBytes(StandardCharsets.UTF_8);
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: " + bodyBytes.length + "\r\n" +
                "\r\n";
        this.os.write(response.getBytes(StandardCharsets.UTF_8));
        this.os.write(bodyBytes);
        this.os.flush();
    }
}