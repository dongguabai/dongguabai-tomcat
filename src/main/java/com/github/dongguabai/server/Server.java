package com.github.dongguabai.server;

import com.github.dongguabai.server.exp.ServletException;

/**
 * @author dongguabai
 * @date 2024-01-27 23:15
 */
public interface Server {

    void start() throws ServletException;
}