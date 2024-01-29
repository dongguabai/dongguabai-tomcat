package io.github.dongguabai.server.engine;

import io.github.dongguabai.server.config.ComponentDefinition;
import io.github.dongguabai.server.config.ServerConfig;
import io.github.dongguabai.server.core.Ordered;
import io.github.dongguabai.server.fork.spring.AntPathMatcher;
import io.github.dongguabai.server.servlet.Filter;
import io.github.dongguabai.server.servlet.HttpServlet;
import io.github.dongguabai.server.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private HashMap<String, HttpServlet> servletMapping = new LinkedHashMap<>();

    private HashMap<String, Filter> filterMapping = new LinkedHashMap<>();

    public ServletEngine(ServerConfig serverConfig) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        processComponentDefinitions(serverConfig.getServlets(), HttpServlet.class, servletMapping);
        processComponentDefinitions(serverConfig.getFilters(), Filter.class, filterMapping);
    }

    private <T> void processComponentDefinitions(List<ComponentDefinition> definitions, Class<T> type, Map<String, T> mapping) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (CollectionUtils.isNotEmpty(definitions)) {
            definitions.sort(Comparator.comparingInt(Ordered::getOrder));
            for (ComponentDefinition definition : definitions) {
                Class<?> clazz = Class.forName(definition.getClassName());
                if (type.isAssignableFrom(clazz)) {
                    T instance = (T) clazz.getDeclaredConstructor().newInstance();
                    mapping.put(definition.getUrlPattern(), instance);
                }
            }
        }
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
}