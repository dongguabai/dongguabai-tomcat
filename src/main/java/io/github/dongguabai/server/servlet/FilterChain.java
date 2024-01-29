package io.github.dongguabai.server.servlet;

import io.github.dongguabai.server.exp.ServletException;
import io.github.dongguabai.server.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongguabai
 * @date 2024-01-29 08:43
 */
public class FilterChain {

    private List<Filter> filters = new ArrayList<>();
    private int index = 0;
    private final HttpServlet servlet;

    public FilterChain(HttpServlet servlet) {
        this.servlet = servlet;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void addFilters(List<Filter> filters) {
        if (CollectionUtils.isEmpty(filters)) {
            return;
        }
        this.filters.addAll(filters);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (index < filters.size()) {
            Filter filter = filters.get(index++);
            filter.doFilter(request, response, this);
        } else {
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                servlet.doGet(request, response);
            } else if ("POST".equalsIgnoreCase(request.getMethod())) {
                servlet.doPost(request, response);
            }
        }
    }
}