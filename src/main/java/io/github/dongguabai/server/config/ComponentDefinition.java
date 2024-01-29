package io.github.dongguabai.server.config;

import io.github.dongguabai.server.core.AbstractOrdered;
import io.github.dongguabai.server.util.StringUtils;
import io.github.dongguabai.server.core.Constant;

/**
 * @author dongguabai
 * @date 2024-01-29 19:55
 */
public class ComponentDefinition extends AbstractOrdered {

    private String className;

    private String urlPattern;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = StringUtils.defaultIfBlank(urlPattern, Constant.MATCH_ALL_PATHS);
    }

    public static class Builder {
        private String name;
        private int order;
        private String urlPattern;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withOrder(int order) {
            this.order = order;
            return this;
        }

        public Builder withUrlPattern(String urlPattern) {
            this.urlPattern = StringUtils.defaultIfBlank(urlPattern, Constant.MATCH_ALL_PATHS);
            return this;
        }

        public ComponentDefinition build() {
            return new ComponentDefinition(this);
        }
    }

    private ComponentDefinition(Builder builder) {
        this.className = builder.name;
        this.order = builder.order;
        this.urlPattern = builder.urlPattern;
    }

    public ComponentDefinition() {
    }
}