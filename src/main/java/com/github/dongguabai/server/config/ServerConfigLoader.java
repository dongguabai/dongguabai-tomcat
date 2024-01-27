package com.github.dongguabai.server.config;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author dongguabai
 * @date 2024-01-27 23:27
 */
public interface ServerConfigLoader {
    /**
     * 加载配置。
     */
    void load() throws IOException;

    /**
     * 获取服务器端口。
     *
     * @return 服务器端口
     */
    int getServerPort();

    int getThreadPoolCorePoolSize();

    int getThreadPoolMaximumPoolSize();

    int getThreadPoolKeepAliveTime();


    int getThreadPoolQueueCapacity();

    String getThreadPoolRejectionPolicy();

    RejectedExecutionHandler getThreadPoolRejectionPolicyHandler();

    Map<String, String> getServletMappings();

    int getSoTimeout();

    /**
     * 获取服务器主机名。
     * @return 服务器主机名
     */
    //String getServerHost();

    /**
     * 获取 Connector 类型（BIO 或 NIO）。
     *
     * @return Connector 类型
     */
    String getConnectorType();


}