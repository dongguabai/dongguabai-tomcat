package com.github.dongguabai.server.connector;

import java.io.IOException;

/**
 * @author dongguabai
 * @date 2024-01-27 23:19
 */
public interface Connector {

    void process(Object connection);

    Object acceptConnection() throws IOException;

}