package io.github.dongguabai.server.engine;

import io.github.dongguabai.server.servlet.Filter;
import io.github.dongguabai.server.servlet.HttpServlet;

import java.util.List;

/**
 * @author Dongguabai
 * @description
 * @date 2024-01-28 00:43
 */
public interface Engine {

    HttpServlet match(String url);

    List<Filter> getFilters(String url);
}