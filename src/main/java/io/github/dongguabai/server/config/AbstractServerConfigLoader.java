package io.github.dongguabai.server.config;

import java.io.IOException;

/**
 * @author dongguabai
 * @date 2024-01-27 23:46
 */
public abstract class AbstractServerConfigLoader implements ServerConfigLoader {

    protected final ServerConfig serverConfig;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    protected AbstractServerConfigLoader() throws IOException {
        serverConfig = new ServerConfig();
        load();
    }

}