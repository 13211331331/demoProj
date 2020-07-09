package com.hhl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;


/**
 * @author hanlin.huang
 * @create 2020-07-09 11:33
 * @desc
 **/
public class HelloWorldServer {
    private int port;

    public static void main(String[] args) throws InterruptedException {
        new HelloWorldServer(18080).start();
    }

    public HelloWorldServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        /**
         * bossGroup, 父类的事件循环组只是负责连接，获取到连接后交给 workergroup子的事件循环组，
         * 参数的获取，业务的处理等工作均是由workergroup这个子事件循环组来完成，一个事件循环组一样
         * 可以完成所有的工作，但是Netty推荐的方式是使用两个事件循环组。
         */
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            /**
             * 实例化一个服务端启动类，
             * group（）指定线程组
             * channel（）指定用于接收客户端连接的类，对应java.nio.ServerSocketChannel
             * childHandler（）设置编码解码及处理连接的类
             */
            ServerBootstrap server = new ServerBootstrap();

            server.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new HelloWorldServerHandler());
                        }
                    });
            //绑定端口
            ChannelFuture future = server.bind().sync();
            System.out.println("server started and listen " + port);
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("HelloWorldServerHandler active");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("server channelRead..");
            System.out.println(ctx.channel().remoteAddress()+"->Server :"+ msg.toString());
            ctx.write("server write"+msg);
            ctx.flush();
        }
    }

}
