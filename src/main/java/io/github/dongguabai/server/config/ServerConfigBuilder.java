package io.github.dongguabai.server.config;

import io.github.dongguabai.server.servlet.Filter;
import io.github.dongguabai.server.servlet.HttpServlet;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @author dongguabai
 * @date 2024-01-30 13:29
 */
public class ServerConfigBuilder {
    private ServerConfig serverConfig;

    public ServerConfigBuilder() {
        this.serverConfig = new ServerConfig();
    }

    public ServerConfigBuilder withServerPort(int serverPort) {
        serverConfig.setServerPort(serverPort);
        return this;
    }

    public ServerConfigBuilder withCorePoolSize(int corePoolSize) {
        serverConfig.setCorePoolSize(corePoolSize);
        return this;
    }

    public ServerConfigBuilder withMaximumPoolSize(int maximumPoolSize) {
        serverConfig.setMaximumPoolSize(maximumPoolSize);
        return this;
    }

    public ServerConfigBuilder withKeepAliveTime(int keepAliveTime) {
        serverConfig.setKeepAliveTime(keepAliveTime);
        return this;
    }

    public ServerConfigBuilder withQueueCapacity(int queueCapacity) {
        serverConfig.setQueueCapacity(queueCapacity);
        return this;
    }

    public ServerConfigBuilder withRejectionPolicy(String rejectionPolicy) {
        serverConfig.setRejectionPolicy(rejectionPolicy);
        return this;
    }

    public ServerConfigBuilder withConnectorType(String connectorType) {
        serverConfig.setConnectorType(connectorType);
        return this;
    }

    public ServerConfigBuilder withSoTimeout(int soTimeout) {
        serverConfig.setSoTimeout(soTimeout);
        return this;
    }

    public ServerConfigBuilder withNamedThreadFactory(ThreadFactory namedThreadFactory) {
        serverConfig.setNamedThreadFactory(namedThreadFactory);
        return this;
    }

    public ServerConfigBuilder addServlets(List<ComponentDefinition> servlets) {
        serverConfig.addServlets(servlets);
        return this;
    }

    public ServerConfigBuilder addFilters(List<ComponentDefinition> filters) {
        serverConfig.addFilters(filters);
        return this;
    }

    public ServerConfigBuilder addServlet(ComponentDefinition servlets) {
        serverConfig.addServlet(servlets);
        return this;
    }

    public ServerConfigBuilder addFilter(List<ComponentDefinition> filters) {
        serverConfig.addFilters(filters);
        return this;
    }

    public ServerConfigBuilder withServlets(List<Class<? extends HttpServlet>> servletClasses) {
        List<ComponentDefinition> servlets = servletClasses.stream().filter(servletClass -> servletClass.isAnnotationPresent(WebServlet.class))
                .map(clazz -> {
                    WebServlet webServlet = clazz.getAnnotation(WebServlet.class);
                    return new ComponentDefinition.Builder()
                            .withName(clazz.getName())
                            .withUrlPattern(webServlet.urlPattern())
                            .withOrder(webServlet.order())
                            .build();
                })
                .collect(Collectors.toList());
        serverConfig.addServlets(servlets);
        return this;
    }

    public ServerConfigBuilder withFilters(List<Class<? extends Filter>> filterClasses) {
        List<ComponentDefinition> filters = filterClasses.stream().filter(filterClass -> filterClass.isAnnotationPresent(WebFilter.class))
                .map(clazz -> {
                    WebFilter webFilter = clazz.getAnnotation(WebFilter.class);
                    return new ComponentDefinition.Builder()
                            .withName(clazz.getName())
                            .withUrlPattern(webFilter.urlPattern())
                            .withOrder(webFilter.order())
                            .build();
                })
                .collect(Collectors.toList());
        serverConfig.addFilters(filters);
        return this;
    }

    public ServerConfigBuilder withServlet(Class<? extends HttpServlet> servletClass) {
        return withServlets(Collections.singletonList(servletClass));
    }

    public ServerConfigBuilder withFilter(Class<? extends Filter> filterClass) {
        return withFilters(Collections.singletonList(filterClass));
    }

    public ServerConfig build() {
        return serverConfig;
    }

    private void processClass(Class<?> clazz) {
        if (clazz.isAnnotationPresent(WebServlet.class)) {
            WebServlet webServlet = clazz.getAnnotation(WebServlet.class);
            ComponentDefinition componentDefinition = new ComponentDefinition.Builder()
                    .withName(clazz.getName())
                    .withUrlPattern(webServlet.urlPattern())
                    .withOrder(webServlet.order())
                    .build();
            this.serverConfig.getServlets().add(componentDefinition);
        } else if (clazz.isAnnotationPresent(WebFilter.class)) {
            WebFilter webFilter = clazz.getAnnotation(WebFilter.class);
            ComponentDefinition componentDefinition = new ComponentDefinition.Builder()
                    .withName(clazz.getName())
                    .withUrlPattern(webFilter.urlPattern())
                    .withOrder(webFilter.order())
                    .build();
            this.serverConfig.getFilters().add(componentDefinition);
        }
    }
}