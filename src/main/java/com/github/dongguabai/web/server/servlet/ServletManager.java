package com.github.dongguabai.web.server.servlet;

import com.github.dongguabai.web.server.conf.PropertiesLoader;
import com.github.dongguabai.web.server.http.HttpServlet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.github.dongguabai.web.server.constant.PropertiesConstant.SERVER_MAPPING_PREFFIX;

/**
 * @author dongguabai
 * @date 2024-01-26 22:21
 */
public class ServletManager {

    private Map<String, HttpServlet> servletMapping = new HashMap<>();

    public ServletManager(PropertiesLoader propertiesLoader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        servletMapping = getServletMappings(propertiesLoader);
    }

    public boolean containsServlet(String url) {
        return servletMapping.containsKey(url);
    }

    public HttpServlet getServlet(String url) {
        return servletMapping.get(url);
    }

    public Map<String, HttpServlet> getServletMappings(PropertiesLoader propertiesLoader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
}