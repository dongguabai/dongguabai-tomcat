package com.github.dongguabai.server.connector;

import com.github.dongguabai.server.engine.Engine;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author dongguabai
 * @date 2024-01-27 23:22
 */
public class NioConnector extends AbstractConnector {

    private ServerSocketChannel serverSocketChannel;

    public NioConnector(Engine servletEngine, int serverPort, int soTimeout) {

    }

    @Override
    public void process(Object connection) {
        SocketChannel channel = (SocketChannel) connection;
        // ... 处理 NIO 连接 ...
    }

    @Override
    public Object acceptConnection() throws IOException {
        return serverSocketChannel.accept();
    }
}