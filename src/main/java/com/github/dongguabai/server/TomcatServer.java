package com.github.dongguabai.server;

import com.github.dongguabai.server.config.AbstractServerConfigLoader;
import com.github.dongguabai.server.config.ThreadPoolConfig;
import com.github.dongguabai.server.config.TomcatConfigLoader;
import com.github.dongguabai.server.connector.BioConnector;
import com.github.dongguabai.server.connector.Connector;
import com.github.dongguabai.server.connector.NioConnector;
import com.github.dongguabai.server.engine.Engine;
import com.github.dongguabai.server.engine.ServletEngine;
import com.github.dongguabai.server.exp.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.github.dongguabai.server.config.PropertiesConstant.ConnectorType.BIO;
import static com.github.dongguabai.server.config.PropertiesConstant.ConnectorType.NIO;

/**
 * @author dongguabai
 * @date 2024-01-26 15:34
 */
public class TomcatServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServer.class);

    private ServerSocket server;

    private AbstractServerConfigLoader configLoader;

    private Engine servletEngine;

    private Connector connector;

    private ExecutorService executorService;

    @Override
    public void start() throws ServerException {
        try {
            init();
        } catch (IOException e) {
            throw new ServerException("Failed to execute TomcatServer: I/O error.", e);
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
        try {
            int serverPort = configLoader.getServerPort();
            int soTimeout = configLoader.getSoTimeout();
            String connectorType = configLoader.getConnectorType().toUpperCase();
            switch (connectorType) {
                case BIO:
                    connector = new BioConnector(servletEngine, serverPort, soTimeout);
                    break;
                case NIO:
                    connector = new NioConnector(servletEngine, serverPort, soTimeout);
                    break;
                default:
                    connector = new BioConnector(servletEngine, serverPort, soTimeout);
            }
            LOGGER.info("TomcatServer has started with {}, listening on port: {}", connectorType, serverPort);
        } catch (IOException e) {
            throw new ServerException("Failed to initialize Connector: I/O error.", e);
        }
        while (true) {
            try {
                Object connection = connector.acceptConnection();
                executorService.execute(() -> connector.process(connection));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        configLoader = new TomcatConfigLoader();
        ThreadPoolConfig threadPoolConfig = configLoader.createThreadPoolConfig();
        servletEngine = new ServletEngine(configLoader);
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(threadPoolConfig.getQueueCapacity());
        executorService = new ThreadPoolExecutor(threadPoolConfig.getCorePoolSize(), threadPoolConfig.getMaximumPoolSize(), threadPoolConfig.getKeepAliveTime(), TimeUnit.SECONDS, workQueue, threadPoolConfig.getThreadFactory(), threadPoolConfig.getRejectionPolicyHandler());
    }
}