package io.github.dongguabai.server.core;

/**
 * @author dongguabai
 * @date 2024-01-29 23:13
 */
public abstract class AbstractOrdered implements Ordered {

    protected int order;

    @Override
    public int getOrder() {
        return this.order;
    }
}