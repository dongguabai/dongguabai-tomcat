package io.github.dongguabai.test;

import io.github.dongguabai.server.config.WebServlet;
import io.github.dongguabai.server.exp.ServletException;
import io.github.dongguabai.server.servlet.HttpServlet;
import io.github.dongguabai.server.servlet.HttpServletRequest;
import io.github.dongguabai.server.servlet.HttpServletResponse;

import java.io.IOException;

/**
 * @author dongguabai
 * @date 2024-01-26 15:33
 */
@WebServlet
public class Demo2Servlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendJson("this is demo2 test：" + request.getBody());
    }
}