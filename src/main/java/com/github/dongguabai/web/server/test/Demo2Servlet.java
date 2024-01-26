package com.github.dongguabai.web.server.test;

import com.github.dongguabai.web.server.http.HttpRequest;
import com.github.dongguabai.web.server.http.HttpResponse;
import com.github.dongguabai.web.server.http.HttpServlet;

/**
 * @author dongguabai
 * @date 2024-01-26 15:33
 */
public class Demo2Servlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        response.sendOK("this is demo2 test");
    }
}