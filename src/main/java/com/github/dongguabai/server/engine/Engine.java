package com.github.dongguabai.server.engine;

import com.github.dongguabai.server.exp.ServerException;
import com.github.dongguabai.server.http.HttpServlet;

/**
 * @author Dongguabai
 * @description
 * @date 2024-01-28 00:43
 */
public interface Engine {

    HttpServlet match(String url) throws ServerException;

}