package io.github.dongguabai.server.servlet;

import io.github.dongguabai.server.exp.ServletException;

import java.io.IOException;

/**
 * @author dongguabai
 * @date 2024-01-26 15:25
 */
public abstract class HttpServlet {

    public HttpServlet() {
        // TODO
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                doGet(request, response);
            } else if ("POST".equalsIgnoreCase(request.getMethod())) {
                doPost(request, response);
            }
        } finally {
            destroy();
        }
    }

    public void destroy() {
    }

    public abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}