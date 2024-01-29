package io.github.dongguabai.server.config;

import java.io.IOException;

/**
 * @author dongguabai
 * @date 2024-01-27 23:27
 */
public interface ServerConfigLoader {

    /**
     * 加载配置。
     */
    void load() throws IOException;

}