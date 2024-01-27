package com.github.dongguabai.server.config;

import com.github.dongguabai.server.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.github.dongguabai.server.config.PropertiesConstant.Config.SERVER_MAPPING_PREFFIX;
import static com.github.dongguabai.server.config.PropertiesConstant.Config.SERVER_PORT;

/**
 * @author dongguabai
 * @date 2024-01-26 15:39
 */
public class TomcatConfigLoader extends AbstractServerConfigLoader {

    private Properties properties;

    public TomcatConfigLoader() throws IOException {
        load();
    }

    @Override
    public void load() throws IOException {
        this.properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("web.properties")) {
            if (input == null) {
                throw new IOException("Unable to find web.properties");
            }
            properties.load(input);
        }

        String port = properties.getProperty(SERVER_PORT);
        serverPort = StringUtils.isNotBlank(port) ? Integer.valueOf(port) : serverPort;

        String coreSize = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_COREPOOLSIZE);
        corePoolSize = StringUtils.isNotBlank(coreSize) ? Integer.valueOf(coreSize) : corePoolSize;

        String maximumSize = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_MAXIMUMPOOLSIZE);
        maximumPoolSize = StringUtils.isNotBlank(maximumSize) ? Integer.valueOf(maximumSize) : maximumPoolSize;

        String time = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_KEEPALIVETIME);
        keepAliveTime = StringUtils.isNotBlank(time) ? Integer.valueOf(time) : keepAliveTime;

        String capacity = properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_QUEUECAPACITY);
        queueCapacity = StringUtils.isNotBlank(capacity) ? Integer.valueOf(capacity) : queueCapacity;

        rejectionPolicy = StringUtils.defaultIfBlank(properties.getProperty(PropertiesConstant.Config.SERVER_THREADPOOL_REJECTIONPOLICY), rejectionPolicy);

        connectorType = StringUtils.defaultIfBlank(properties.getProperty(PropertiesConstant.Config.SERVER_CONNECTORTYPE), connectorType);
    }

    @Override
    public Map<String, String> getServletMappings() {
        Map<String, String> mappings = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(SERVER_MAPPING_PREFFIX)) {
                String path = key.substring(SERVER_MAPPING_PREFFIX.length());
                String className = properties.getProperty(key);
                mappings.put(path, className);
            }
        }
        return mappings;
    }

}