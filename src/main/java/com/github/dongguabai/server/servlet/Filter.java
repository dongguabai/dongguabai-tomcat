package com.github.dongguabai.server.servlet;

import com.github.dongguabai.server.exp.ServletException;

import java.io.IOException;

/**
 * @author Dongguabai
 * @description
 * @date 2024-01-29 08:45
 */
public interface Filter {

    void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
}
