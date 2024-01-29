package com.github.dongguabai.server.connector;

import java.util.concurrent.ExecutorService;

/**
 * @author dongguabai
 * @date 2024-01-27 23:19
 */
public interface Connector {

    void process(ExecutorService executorService);

}