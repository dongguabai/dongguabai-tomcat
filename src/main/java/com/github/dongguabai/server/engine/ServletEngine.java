package com.github.dongguabai.server.engine;

import com.github.dongguabai.server.config.ServerConfigLoader;
import com.github.dongguabai.server.fork.spring.AntPathMatcher;
import com.github.dongguabai.server.servlet.Filter;
import com.github.dongguabai.server.servlet.HttpServlet;
import com.github.dongguabai.server.util.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongguabai
 * @date 2024-01-26 22:21
 */
public class ServletEngine implements Engine {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletEngine.class);

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    private Map<String, HttpServlet> servletMapping;

    private Map<String, Filter> filterMapping;

    public ServletEngine(ServerConfigLoader configLoader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        servletMapping = getServletMappings(configLoader);
        filterMapping = getFilterMappings(configLoader);
    }

    @Override
    public HttpServlet match(String url) {
        return servletMapping.keySet().stream()
                .filter(pattern -> MATCHER.match(pattern, url))
                .findFirst()
                .map(servletMapping::get)
                .orElseGet(() -> {
                    LOGGER.warn("No servlet matched for the given URL: {}", url);
                    return null;
                });
    }

    @Override
    public List<Filter> getFilters(String url) {
        return filterMapping.entrySet().stream()
                .filter(entry -> MATCHER.match(entry.getKey(), url))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
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

    private Map<String, Filter> getFilterMappings(ServerConfigLoader configLoader) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, String> filterMappings = configLoader.getFilterMappings();
        Map<String, Filter> mappings = MapUtils.createHashMapWithExpectedSize(filterMappings.size());
        for (Map.Entry<String, String> entry : filterMappings.entrySet()) {
            Class filterClass = Class.forName(entry.getValue());
            if (Filter.class.isAssignableFrom(filterClass)) {
                Filter filter = (Filter) filterClass.getDeclaredConstructor().newInstance();
                mappings.put(entry.getKey(), filter);
            }
        }
        return mappings;
    }
}