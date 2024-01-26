package com.github.dongguabai.web.server.startup;

import com.github.dongguabai.web.server.conf.PropertiesLoader;
import com.github.dongguabai.web.server.http.HttpRequest;
import com.github.dongguabai.web.server.http.HttpResponse;
import com.github.dongguabai.web.server.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.github.dongguabai.web.server.constant.PropertiesConstant.SERVER_MAPPING_PREFFIX;

/**
 * @author dongguabai
 * @date 2024-01-26 15:34
 */
public class Tomcat {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tomcat.class);

    private int defaultPort = 8888;

    private ServerSocket server;

    private PropertiesLoader propertiesLoader;

    private Map<String, HttpServlet> servletMapping = new HashMap<>();

    private Tomcat() {
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        new Tomcat().start();
    }

    private void start() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        init();
        int serverPort = propertiesLoader.getServerPort();
        serverPort = serverPort == -1 ? defaultPort : serverPort;
        server = new ServerSocket(serverPort);
        LOGGER.info("Tomcat has started, listening on port: {}", serverPort);
        while (true) {
            Socket socket = server.accept();
            process(socket);
        }
    }

    private void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        propertiesLoader = new PropertiesLoader();
        servletMapping = getServletMappings();
    }

    private void process(Socket client) {
        //获取Socket对象，将Socket.getInputStream()封装成Request，Socket.getOuputStream()封装成Response
        try {
            try (OutputStream outputStream = client.getOutputStream(); InputStream inputStream = client.getInputStream();) {
                HttpRequest request = new HttpRequest(inputStream);
                HttpResponse response = new HttpResponse(outputStream);
                //实现动态调用doGet/doPost方法，并且能够自定义返回结果
                //拿到用户请求的url
                String url = request.getUrl();
                if (servletMapping.containsKey(url)) {
                    servletMapping.get(request.getUrl()).service(request, response);
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

    public Map<String, HttpServlet> getServletMappings() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, HttpServlet> mappings = new HashMap<>();
        for (String key : propertiesLoader.stringPropertyNames()) {
            if (key.startsWith(SERVER_MAPPING_PREFFIX)) {
                String path = key.substring(SERVER_MAPPING_PREFFIX.length());
                String servletClassName = propertiesLoader.getProperty(key);
                Class servletClass = Class.forName(servletClassName);
                HttpServlet servlet = (HttpServlet) servletClass.getDeclaredConstructor().newInstance();
                mappings.put(path, servlet);
            }
        }
        return mappings;
    }

    public void sendNotFound(OutputStream outputStream) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}