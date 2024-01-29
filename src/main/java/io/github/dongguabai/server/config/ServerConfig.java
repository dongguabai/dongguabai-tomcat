package io.github.dongguabai.server.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dongguabai
 * @date 2024-01-29 14:20
 */
public class ServerConfig {

    private int serverPort = PropertiesConstant.Default.DEFAULT_PORT;

    private int corePoolSize = PropertiesConstant.Default.DEFAULT_COREPOOLSIZE;

    private int maximumPoolSize = PropertiesConstant.Default.DEFAULT_MAXIMUMPOOLSIZE;

    private int keepAliveTime = PropertiesConstant.Default.DEFAULT_KEEPALIVETIME;

    private int queueCapacity = PropertiesConstant.Default.DEFAULT_QUEUECAPACITY;

    private String rejectionPolicy = PropertiesConstant.Default.DEFAULT_REJECTIONPOLICY;

    private String connectorType = PropertiesConstant.Default.DEFAULT_CONNECTORTYPE;

    private int soTimeout = PropertiesConstant.Default.DEFAULT_SOTIMEOUT;

    private ThreadFactory namedThreadFactory = new ThreadFactory() {
        private final AtomicInteger poolNumber = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "dongguabai-server-" + poolNumber.getAndIncrement());
        }
    };

    private List<ComponentDefinition> servlets = new ArrayList<>();

    private List<ComponentDefinition> filters = new ArrayList<>();

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
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

    public String getRejectionPolicy() {
        return rejectionPolicy;
    }

    public void setRejectionPolicy(String rejectionPolicy) {
        this.rejectionPolicy = rejectionPolicy;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public ThreadFactory getNamedThreadFactory() {
        return namedThreadFactory;
    }

    public void setNamedThreadFactory(ThreadFactory namedThreadFactory) {
        this.namedThreadFactory = namedThreadFactory;
    }

    public void addServlets(List<ComponentDefinition> servlets) {
        this.servlets.addAll(servlets);
    }

    public void addServlet(ComponentDefinition servlet) {
        this.servlets.add(servlet);
    }

    public List<ComponentDefinition> getFilters() {
        return filters;
    }

    public void addFilters(List<ComponentDefinition> filters) {
        this.filters.addAll(filters);
    }

    public void addFilter(ComponentDefinition filter) {
        this.filters.add(filter);
    }

    public List<ComponentDefinition> getServlets() {
        return servlets;
    }
}