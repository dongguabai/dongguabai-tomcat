package com.github.dongguabai.server.engine;

import com.github.dongguabai.server.config.ServerConfigLoader;
import com.github.dongguabai.server.exp.ServerException;
import com.github.dongguabai.server.servlet.HttpServlet;
import com.github.dongguabai.server.util.MapUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author dongguabai
 * @date 2024-01-26 22:21
 */
public class ServletEngine implements Engine {

    private Map<String, HttpServlet> servletMapping;

    public ServletEngine(ServerConfigLoader tomcatConfigLoader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        servletMapping = getServletMappings(tomcatConfigLoader);
    }

    @Override
    public HttpServlet match(String url) throws ServerException {
        for (String pattern : servletMapping.keySet()) {
            if (url.matches(pattern)) {
                return servletMapping.get(pattern);
            }
        }
        throw new ServerException("No servlet matched for the given URL: " + url);
    }


    public Map<String, HttpServlet> getServletMappings(ServerConfigLoader serverConfigLoader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, String> servletMappings = serverConfigLoader.getServletMappings();
        Map<String, HttpServlet> mappings = MapUtils.createHashMapWithExpectedSize(servletMappings.size());
        for (Map.Entry<String, String> entry : servletMappings.entrySet()) {
            Class servletClass = Class.forName(entry.getValue());
            if (HttpServlet.class.isAssignableFrom(servletClass)) {
                HttpServlet servlet = (HttpServlet) servletClass.getDeclaredConstructor().newInstance();
                mappings.put(entry.getKey(), servlet);
            }
        }
        return mappings;
    }
}