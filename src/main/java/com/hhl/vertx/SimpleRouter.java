package com.hhl.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 * @author hanlin.huang
 * @create 2020-07-09 17:56
 * @desc
 **/
public class SimpleRouter extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        // 创建HttpServer
        HttpServer server = vertx.createHttpServer();

        // 创建路由对象
        Router router = Router.router(vertx);

        // 监听/index地址
        router.route("/index.html").handler(request -> {
            request.response().end("INDEX SUCCESS");
        });

        router.post("/post").handler(request -> {
            request.response().end("post");
        });

        router.route(HttpMethod.GET, "/method").handler(request -> {
            request.response().end("method");
        });

        router.route("/index/*").handler(request -> {
            request.response().end("Index");
        });

        router.route("/index/main").handler(request -> {
            request.response().end("IndexMain");
        });



        // 把请求交给路由处理--------------------(1)
        server.requestHandler(router::accept);
        server.listen(8888);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new SimpleRouter());
    }


}
