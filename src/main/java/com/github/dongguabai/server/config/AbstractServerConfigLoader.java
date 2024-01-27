package com.github.dongguabai.server.config;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dongguabai
 * @date 2024-01-27 23:46
 */
public abstract class AbstractServerConfigLoader implements ServerConfigLoader {

    protected int serverPort = PropertiesConstant.Default.DEFAULT_PORT;

    protected int corePoolSize = PropertiesConstant.Default.DEFAULT_COREPOOLSIZE;

    protected int maximumPoolSize = PropertiesConstant.Default.DEFAULT_MAXIMUMPOOLSIZE;

    protected int keepAliveTime = PropertiesConstant.Default.DEFAULT_KEEPALIVETIME;

    protected int queueCapacity = PropertiesConstant.Default.DEFAULT_QUEUECAPACITY;

    protected String rejectionPolicy = PropertiesConstant.Default.DEFAULT_REJECTIONPOLICY;

    protected String connectorType = PropertiesConstant.Default.DEFAULT_CONNECTORTYPE;

    protected int soTimeout = PropertiesConstant.Default.DEFAULT_SOTIMEOUT;

    protected ThreadFactory namedThreadFactory = new ThreadFactory() {
        private final AtomicInteger poolNumber = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "dongguabai-server-" + poolNumber.getAndIncrement());
        }
    };

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public int getThreadPoolCorePoolSize() {
        return corePoolSize;
    }

    @Override
    public int getThreadPoolMaximumPoolSize() {
        return maximumPoolSize;
    }

    @Override
    public int getThreadPoolKeepAliveTime() {
        return keepAliveTime;
    }

    @Override
    public int getThreadPoolQueueCapacity() {
        return queueCapacity;
    }

    @Override
    public String getThreadPoolRejectionPolicy() {
        return rejectionPolicy;
    }

    @Override
    public RejectedExecutionHandler getThreadPoolRejectionPolicyHandler() {
        String rejectionPolicy = getThreadPoolRejectionPolicy();
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

    @Override
    public void load() throws IOException {
    }

    public ThreadPoolConfig createThreadPoolConfig() {
        ThreadPoolConfig config = new ThreadPoolConfig();
        config.setCorePoolSize(getThreadPoolCorePoolSize());
        config.setMaximumPoolSize(getThreadPoolMaximumPoolSize());
        config.setKeepAliveTime(getThreadPoolKeepAliveTime());
        config.setQueueCapacity(getThreadPoolQueueCapacity());
        config.setRejectionPolicyHandler(getThreadPoolRejectionPolicyHandler());
        config.setThreadFactory(getThreadFactory());
        return config;
    }

    public ThreadFactory getThreadFactory() {
        return namedThreadFactory;
    }

    @Override
    public String getConnectorType() {
        return connectorType;
    }

    @Override
    public int getSoTimeout() {
        return soTimeout;
    }
}