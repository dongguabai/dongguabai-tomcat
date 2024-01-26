package com.github.dongguabai.web.server.conf;

import com.github.dongguabai.web.server.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import static com.github.dongguabai.web.server.constant.PropertiesConstant.SERVER_PORT;

/**
 * @author dongguabai
 * @date 2024-01-26 15:39
 */
public class PropertiesLoader {

    private Properties webXml;

    public PropertiesLoader() {
        this.webXml = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("web.properties")) {
            if (input == null) {
                throw new IOException("Unable to find web.webXml");
            }
            webXml.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return webXml.getProperty(key);
    }

    public int getServerPort() {
        String port = webXml.getProperty(SERVER_PORT);
        if (StringUtils.isBlank(port)) {
            return -1;
        }
        return Integer.valueOf(port);
    }

    public Set<String> stringPropertyNames() {
        return webXml.stringPropertyNames();
    }
}