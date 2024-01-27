package com.github.dongguabai.web.server.startup;

import com.github.dongguabai.web.server.conf.PropertiesLoader;
import com.github.dongguabai.web.server.exp.ServerException;
import com.github.dongguabai.web.server.threadpool.ThreadPoolManager;
import com.github.dongguabai.web.server.http.HttpRequest;
import com.github.dongguabai.web.server.http.HttpResponse;
import com.github.dongguabai.web.server.servlet.ServletManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dongguabai
 * @date 2024-01-26 15:34
 */
public class Tomcat {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tomcat.class);

    private ServerSocket server;

    private PropertiesLoader propertiesLoader;

    private ServletManager servletManager;

    private ThreadPoolManager threadPoolManager;

    private Tomcat() {
    }

    public static void main(String[] args) throws ServerException {
        new Tomcat().start();
    }

    private void start() throws ServerException {
        try {
            init();
        } catch (ClassNotFoundException e) {
            throw new ServerException("Failed to initialize Tomcat: Class not found.", e);
        } catch (NoSuchMethodException e) {
            throw new ServerException("Failed to initialize Tomcat: No such method.", e);
        } catch (InvocationTargetException e) {
            throw new ServerException("Failed to initialize Tomcat: Error invoking method.", e);
        } catch (InstantiationException e) {
            throw new ServerException("Failed to initialize Tomcat: Error instantiating class.", e);
        } catch (IllegalAccessException e) {
            throw new ServerException("Failed to initialize Tomcat: Illegal access.", e);
        }
        int serverPort = propertiesLoader.getServerPort();
        try {
            server = new ServerSocket(serverPort);
            LOGGER.info("Tomcat has started, listening on port: {}", serverPort);
            while (true) {
                Socket socket = server.accept();
                threadPoolManager.execute(() -> process(socket));
            }
        } catch (IOException e) {
            throw new ServerException("Failed to execute Tomcat: I/O error.", e);
        }
    }

    private void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        propertiesLoader = new PropertiesLoader();
        servletManager = new ServletManager(propertiesLoader);
        threadPoolManager = new ThreadPoolManager(propertiesLoader);
    }

    private void process(Socket client) {
        //获取Socket对象，将Socket.getInputStream()封装成Request，Socket.getOuputStream()封装成Response
        try {
            try (OutputStream outputStream = client.getOutputStream(); InputStream inputStream = client.getInputStream()) {
                HttpRequest request = new HttpRequest(inputStream);
                HttpResponse response = new HttpResponse(outputStream);
                //实现动态调用doGet/doPost方法，并且能够自定义返回结果
                //拿到用户请求的url
                String url = request.getUrl();
                if (servletManager.containsServlet(url)) {
                    servletManager.getServlet(url).service(request, response);
                } else {
                    sendNotFound(outputStream);
                }
                //因为http请求是短链接，这里要关闭
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNotFound(OutputStream outputStream) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}