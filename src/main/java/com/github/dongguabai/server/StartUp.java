package com.github.dongguabai.server;

import com.github.dongguabai.server.exp.ServerException;

/**
 * @author dongguabai
 * @date 2024-01-27 23:16
 */
public class StartUp {

    public static void main(String[] args) throws ServerException {
        new TomcatServer().start();
    }
}