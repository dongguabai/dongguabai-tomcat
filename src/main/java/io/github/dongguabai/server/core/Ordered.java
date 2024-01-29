package io.github.dongguabai.server.core;

/**
 * @author dongguabai
 * @date 2024-01-29 19:10
 */
public interface Ordered {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int getOrder();
}