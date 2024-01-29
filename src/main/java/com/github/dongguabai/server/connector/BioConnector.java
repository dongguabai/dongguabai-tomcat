package com.github.dongguabai.server.connector;

import com.github.dongguabai.server.engine.Engine;
import com.github.dongguabai.server.servlet.Filter;
import com.github.dongguabai.server.servlet.FilterChain;
import com.github.dongguabai.server.servlet.HttpServlet;
import com.github.dongguabai.server.servlet.HttpServletRequest;
import com.github.dongguabai.server.servlet.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @author dongguabai
 * @date 2024-01-27 23:22
 */
public class BioConnector extends AbstractConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioConnector.class);

    private final Engine servletEngine;

    private ServerSocket server;

    public BioConnector(Engine servletEngine, int serverPort, int soTimeout) throws IOException {
        this.servletEngine = servletEngine;
        this.server = new ServerSocket(serverPort);
        this.server.setSoTimeout(soTimeout);
    }

    @Override
    public void process(Object connection) {
        Socket socket = (Socket) connection;
        try {
            try (OutputStream outputStream = socket.getOutputStream(); InputStream inputStream = socket.getInputStream()) {
                HttpServletRequest request = new HttpServletRequest(inputStream);
                HttpServletResponse response = new HttpServletResponse(outputStream);
                String url = request.getUrl();
                HttpServlet servlet = servletEngine.match(url);
                if (servlet != null) {
                    List<Filter> filters = servletEngine.getFilters(url);
                    FilterChain filterChain = new FilterChain(servlet);
                    filterChain.addFilters(filters);
                    filterChain.doFilter(request, response);
                } else {
                    sendNotFound(outputStream);
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object acceptConnection() throws IOException {
        return server.accept();
    }
}