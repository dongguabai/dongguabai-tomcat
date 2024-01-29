package io.github.dongguabai.server;

import io.github.dongguabai.server.config.ServerConfigBuilder;
import io.github.dongguabai.server.exp.ServletException;
import io.github.dongguabai.test.Demo2Servlet;
import io.github.dongguabai.test.Filter2;

/**
 * @author dongguabai
 * @date 2024-01-27 23:16
 */
public class StartUp {

    public static void main(String[] args) throws ServletException {
        //new TomcatServer().start();
        new TomcatServer().start(new ServerConfigBuilder().withFilter(Filter2.class).withServlet(Demo2Servlet.class));
    }
}