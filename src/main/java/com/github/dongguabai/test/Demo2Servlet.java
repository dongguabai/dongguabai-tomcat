package com.github.dongguabai.test;

import com.github.dongguabai.server.servlet.HttpServletRequest;
import com.github.dongguabai.server.servlet.HttpServletResponse;
import com.github.dongguabai.server.servlet.HttpServlet;

/**
 * @author dongguabai
 * @date 2024-01-26 15:33
 */
public class Demo2Servlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendOK("this is demo2 test：" + request.getBody());
    }
}