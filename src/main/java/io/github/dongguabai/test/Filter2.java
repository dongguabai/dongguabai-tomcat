package io.github.dongguabai.test;

import io.github.dongguabai.server.config.WebFilter;
import io.github.dongguabai.server.exp.ServletException;
import io.github.dongguabai.server.servlet.Filter;
import io.github.dongguabai.server.servlet.FilterChain;
import io.github.dongguabai.server.servlet.HttpServletRequest;
import io.github.dongguabai.server.servlet.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author dongguabai
 * @date 2024-01-30 10:03
 */
@WebFilter
public class Filter2 implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter2.class);

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Filter2...");
        chain.doFilter(request, response);
    }
}