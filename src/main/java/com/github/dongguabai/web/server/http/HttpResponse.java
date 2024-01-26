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

    public void write(String outString) throws IOException {
        os.write(outString.getBytes());
    }

}