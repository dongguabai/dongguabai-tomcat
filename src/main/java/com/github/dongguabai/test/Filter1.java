package com.github.dongguabai.test;

import com.github.dongguabai.server.exp.ServletException;
import com.github.dongguabai.server.servlet.Filter;
import com.github.dongguabai.server.servlet.FilterChain;
import com.github.dongguabai.server.servlet.HttpServletRequest;
import com.github.dongguabai.server.servlet.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author dongguabai
 * @date 2024-01-29 09:54
 */
public class Filter1 implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter1.class);

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Filter1...");
        chain.doFilter(request, response);
    }
}