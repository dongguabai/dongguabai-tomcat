package com.github.dongguabai.web.server.http;

/**
 * @author dongguabai
 * @date 2024-01-26 15:25
 */
public abstract class HttpServlet {

    public HttpServlet() {
        // TODO
    }

    public void service(HttpRequest request, HttpResponse response) throws Exception {
        //由Servie方法觉得是执行post还是get方法
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        }
    }

    public void destroy(HttpRequest request, HttpResponse response) throws Exception {

    }

    public abstract void doGet(HttpRequest request, HttpResponse response) throws Exception;

    public abstract void doPost(HttpRequest request, HttpResponse response) throws Exception;

}