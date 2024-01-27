package com.github.dongguabai.server.config;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * @author dongguabai
 * @date 2024-01-28 00:27
 */
public class ThreadPoolConfig {

    private int corePoolSize;

    private int maximumPoolSize;

    private int keepAliveTime;

    private int queueCapacity;

    private RejectedExecutionHandler rejectionPolicyHandler;

    private ThreadFactory threadFactory;

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public RejectedExecutionHandler getRejectionPolicyHandler() {
        return rejectionPolicyHandler;
    }

    public void setRejectionPolicyHandler(RejectedExecutionHandler rejectionPolicyHandler) {
        this.rejectionPolicyHandler = rejectionPolicyHandler;
    }
}