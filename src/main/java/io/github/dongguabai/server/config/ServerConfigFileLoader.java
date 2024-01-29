package io.github.dongguabai.server.config;

import io.github.dongguabai.server.core.LinkedProperties;
import io.github.dongguabai.server.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.github.dongguabai.server.config.PropertiesConstant.Config.SERVER_FILTER_PREFFIX;
import static io.github.dongguabai.server.config.PropertiesConstant.Config.SERVER_MAPPING_PREFFIX;
import static io.github.dongguabai.server.config.PropertiesConstant.Config.SERVER_PORT;

/**
 * @author dongguabai
 * @date 2024-01-26 15:39
 */
public class ServerConfigFileLoader extends AbstractServerConfigLoader {

    public ServerConfigFileLoader() throws IOException {
    }

    @Override
    public void load() throws IOException {
        Properties properties = new LinkedProperties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("web.properties")) {
            if (input == null) {
                throw new IOException("Unable to find web.properties");
            }
            properties.load(input);
        }

        String port = properties.getProperty(SERVER_PORT);
        if (StringUtils.isNotBlank(port)) {
            serverConfig.setServerPort(Integer.valueOf(port));
        }

        String coreSize = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_COREPOOLSIZE);
        if (StringUtils.isNotBlank(coreSize)) {
            serverConfig.setCorePoolSize(Integer.valueOf(coreSize));
        }

        String maximumSize = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_MAXIMUMPOOLSIZE);
        if (StringUtils.isNotBlank(maximumSize)) {
            serverConfig.setMaximumPoolSize(Integer.valueOf(maximumSize));
        }

        String time = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_KEEPALIVETIME);
        if (StringUtils.isNotBlank(time)) {
            serverConfig.setKeepAliveTime(Integer.valueOf(time));
        }

        String capacity = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_QUEUECAPACITY);
        if (StringUtils.isNotBlank(capacity)) {
            serverConfig.setQueueCapacity(Integer.valueOf(capacity));
        }

        String rejectionPolicy = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_REJECTIONPOLICY);
        if (StringUtils.isNotBlank(rejectionPolicy)) {
            serverConfig.setRejectionPolicy(rejectionPolicy);
        }

        String connectorType = properties.getProperty(PropertiesConstant.Config.SERVER_CONNECTORTYPE);
        if (StringUtils.isNotBlank(connectorType)) {
            serverConfig.setConnectorType(connectorType);
        }

        serverConfig.addServlets(getMappings(properties, SERVER_MAPPING_PREFFIX));

        serverConfig.addFilters(getMappings(properties, SERVER_FILTER_PREFFIX));
    }

    private List<ComponentDefinition> getMappings(Properties properties, String prefix) {
        List<ComponentDefinition> definitions = new ArrayList<>();
        int order = 0;
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                String path = key.substring(prefix.length());
                String className = properties.getProperty(key);
                definitions.add(new ComponentDefinition.Builder()
                        .withName(className)
                        .withUrlPattern(path)
                        .withOrder(order++)
                        .build());
            }
        }
        return definitions;
    }
}