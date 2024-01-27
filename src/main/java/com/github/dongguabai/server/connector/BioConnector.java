package com.github.dongguabai.server.connector;

import com.github.dongguabai.server.engine.Engine;
import com.github.dongguabai.server.servlet.HttpServletRequest;
import com.github.dongguabai.server.servlet.HttpServletResponse;
import com.github.dongguabai.server.servlet.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
        //获取Socket对象，将Socket.getInputStream()封装成Request，Socket.getOuputStream()封装成Response
        try {
            try (OutputStream outputStream = socket.getOutputStream(); InputStream inputStream = socket.getInputStream()) {
                HttpServletRequest request = new HttpServletRequest(inputStream);
                HttpServletResponse response = new HttpServletResponse(outputStream);
                String url = request.getUrl();
                HttpServlet servlet = servletEngine.match(url);
                if (servlet != null) {
                    servlet.service(request, response);
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