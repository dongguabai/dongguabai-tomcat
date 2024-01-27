package com.github.dongguabai.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongguabai
 * @date 2024-01-26 15:25
 */
public class HttpServletRequest {

    private String method;

    private String url;

    private Map<String, String> parameters = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> cookies = new HashMap<>();

    private String body;

    public HttpServletRequest(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            String[] requestLineParts = line.split("\\s");
            this.method = requestLineParts[0];
            String[] urlParts = requestLineParts[1].split("\\?");
            this.url = urlParts[0];
            if (urlParts.length > 1) {
                String[] params = urlParts[1].split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length > 1) {
                        this.parameters.put(keyValue[0], keyValue[1]);
                    }
                }
            }
            while (!(line = reader.readLine()).isEmpty()) {
                String[] headerParts = line.split(": ");
                this.headers.put(headerParts[0], headerParts[1]);
                if ("Cookie".equalsIgnoreCase(headerParts[0])) {
                    String[] cookieParts = headerParts[1].split("; ");
                    for (String cookie : cookieParts) {
                        String[] keyValue = cookie.split("=");
                        if (keyValue.length > 1) {
                            this.cookies.put(keyValue[0], keyValue[1]);
                        }
                    }
                }
            }
            if (this.method.equalsIgnoreCase("POST")) {
                StringBuilder bodyBuilder = new StringBuilder();
                while (reader.ready()) {
                    bodyBuilder.append((char) reader.read());
                }
                this.body = bodyBuilder.toString();
                String[] params = this.body.split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length > 1) {
                        this.parameters.put(keyValue[0], keyValue[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getBody() {
        return body;
    }
}