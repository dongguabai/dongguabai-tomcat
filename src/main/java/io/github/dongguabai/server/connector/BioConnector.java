package io.github.dongguabai.server.connector;

import io.github.dongguabai.server.engine.Engine;
import io.github.dongguabai.server.servlet.Filter;
import io.github.dongguabai.server.servlet.FilterChain;
import io.github.dongguabai.server.servlet.HttpServlet;
import io.github.dongguabai.server.servlet.HttpServletRequest;
import io.github.dongguabai.server.servlet.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author dongguabai
 * @date 2024-01-27 23:22
 */
public class BioConnector extends AbstractConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioConnector.class);

    private ServerSocket server;

    public BioConnector(Engine servletEngine, int serverPort, int soTimeout) throws IOException {
        super(servletEngine, serverPort, soTimeout);
        this.server = new ServerSocket(serverPort);
        this.server.setSoTimeout(soTimeout);
    }

    @Override
    public void process(ExecutorService executorService) {
        while (true) {
            try {
                Socket socket = server.accept();
                executorService.submit(() -> handleRequest(socket));
            } catch (IOException e) {
                LOGGER.error("Error accepting connection", e);
            }
        }
    }

    private void handleRequest(Socket socket) {
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
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotFound(OutputStream outputStream) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}