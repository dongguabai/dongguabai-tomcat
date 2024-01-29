package com.github.dongguabai.server.engine;

import com.github.dongguabai.server.servlet.Filter;
import com.github.dongguabai.server.servlet.HttpServlet;

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