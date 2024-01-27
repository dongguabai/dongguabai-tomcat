package com.github.dongguabai.server.servlet;

/**
 * @author dongguabai
 * @date 2024-01-26 15:25
 */
public abstract class HttpServlet {

    public HttpServlet() {
        // TODO
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                doGet(request, response);
            } else if ("POST".equalsIgnoreCase(request.getMethod())) {
                doPost(request, response);
            }
        } finally {
            destroy(request, response);
        }
    }

    public void destroy(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }

    public abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception;

}