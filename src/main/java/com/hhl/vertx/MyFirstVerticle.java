package com.hhl.vertx;

import io.vertx.core.AbstractVerticle;

/**
 * @author hanlin.huang
 * @create 2020-07-09 17:08
 * @desc
 **/
public class MyFirstVerticle extends AbstractVerticle {

    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello World!");
        }).listen(8080);
    }
}
