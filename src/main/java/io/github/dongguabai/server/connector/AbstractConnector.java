package io.github.dongguabai.server.connector;

import io.github.dongguabai.server.engine.Engine;

/**
 * @author dongguabai
 * @date 2024-01-28 01:44
 */
public abstract class AbstractConnector implements Connector {

    protected final Engine servletEngine;

    protected final int serverPort;

    protected final int soTimeout;

    protected AbstractConnector(Engine servletEngine, int serverPort, int soTimeout) {
        this.servletEngine = servletEngine;
        this.serverPort = serverPort;
        this.soTimeout = soTimeout;
    }
}