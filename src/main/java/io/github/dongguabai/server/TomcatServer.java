package io.github.dongguabai.server;

import io.github.dongguabai.server.config.PropertiesConstant;
import io.github.dongguabai.server.config.ServerConfig;
import io.github.dongguabai.server.config.ServerConfigBuilder;
import io.github.dongguabai.server.config.ServerConfigFileLoader;
import io.github.dongguabai.server.connector.BioConnector;
import io.github.dongguabai.server.connector.Connector;
import io.github.dongguabai.server.connector.NioConnector;
import io.github.dongguabai.server.engine.Engine;
import io.github.dongguabai.server.engine.ServletEngine;
import io.github.dongguabai.server.exp.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static io.github.dongguabai.server.config.PropertiesConstant.ConnectorType.BIO;
import static io.github.dongguabai.server.config.PropertiesConstant.ConnectorType.NIO;

/**
 * @author dongguabai
 * @date 2024-01-26 15:34
 */
public class TomcatServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServer.class);

    private ServerConfig serverConfig;

    private Engine servletEngine;

    private ExecutorService executorService;

    public void start() throws ServletException {
        try {
            initializeFromPropertiesFile();
        } catch (IOException e) {
            throw new ServletException("Failed to execute TomcatServer: I/O error.", e);
        }
        run();
    }

    private void run() throws ServletException {
        initializeServletEngine();
        Connector connector;
        try {
            int serverPort = serverConfig.getServerPort();
            int soTimeout = serverConfig.getSoTimeout();
            String connectorType = serverConfig.getConnectorType().toUpperCase();
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
            throw new ServletException("Failed to initialize Connector: I/O error.", e);
        }
        connector.process(executorService);
    }

    private void initializeServletEngine() throws ServletException {
        try {
            servletEngine = new ServletEngine(serverConfig);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to initialize Tomcat Engine: Class not found.", e);
        } catch (NoSuchMethodException e) {
            throw new ServletException("Failed to initialize Tomcat Engine: No such method.", e);
        } catch (InvocationTargetException e) {
            throw new ServletException("Failed to initialize Tomcat Engine: Error invoking method.", e);
        } catch (InstantiationException e) {
            throw new ServletException("Failed to initialize Tomcat Engine: Error instantiating class.", e);
        } catch (IllegalAccessException e) {
            throw new ServletException("Failed to initialize Tomcat Engine: Illegal access.", e);
        }
    }

    private void initializeFromPropertiesFile() throws IOException {
        ServerConfigFileLoader serverConfigFileLoader = new ServerConfigFileLoader();
        serverConfig = serverConfigFileLoader.getServerConfig();
        createThreadPoolExecutor();
    }

    private RejectedExecutionHandler getThreadPoolRejectionPolicyHandler() {
        String rejectionPolicy = serverConfig.getRejectionPolicy();
        RejectedExecutionHandler handler;
        switch (rejectionPolicy) {
            case PropertiesConstant.ThreadPool.REJECTION_POLICY_CALLER_RUNS:
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case PropertiesConstant.ThreadPool.REJECTION_POLICY_ABORT:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case PropertiesConstant.ThreadPool.REJECTION_POLICY_DISCARD_OLDEST:
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case PropertiesConstant.ThreadPool.REJECTION_POLICY_DISCARD:
                handler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            default:
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
        }
        return handler;
    }

    /**
     * This method is not recommended for use.
     *
     * @deprecated This method is deprecated and may be removed in the future.
     */
    @Deprecated
    private void start(ServerConfig serverConfig) throws ServletException {
        initializeFromConfigurationClass(serverConfig);
        run();
    }

    public void start(ServerConfigBuilder builder) throws ServletException {
        start(builder.build());
    }

    public void initializeFromConfigurationClass(ServerConfig serverConfig) throws ServletException {
        if (serverConfig == null) {
            throw new ServletException("Failed to initialize Tomcat: Necessary configuration is missing. Please check your configuration file or configuration class, and ensure all necessary configurations have been provided.");
        }
        this.serverConfig = serverConfig;
        createThreadPoolExecutor();
    }

    private void createThreadPoolExecutor() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(serverConfig.getQueueCapacity());
        executorService = new ThreadPoolExecutor(serverConfig.getCorePoolSize(), serverConfig.getMaximumPoolSize(), serverConfig.getKeepAliveTime(), TimeUnit.SECONDS, workQueue, serverConfig.getNamedThreadFactory(), getThreadPoolRejectionPolicyHandler());
    }
}