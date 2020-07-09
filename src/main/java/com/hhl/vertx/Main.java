package com.hhl.vertx;

import io.vertx.core.Vertx;

/**
 * @author hanlin.huang
 * @create 2020-07-09 17:08
 * @desc
 **/
public class Main {
    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(MyFirstVerticle.class.getName());
    }
}
