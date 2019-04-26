package com.learn.sky.netty.jeep;

import com.learn.sky.netty.jeep.entity.Dog;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.PropertyConfigurator;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午11:24
 */
public class JeepClient {

    public static void main(String[] args) throws InterruptedException {
        PropertyConfigurator.configure("/Users/keitho00/Documents/project/github/LearnSky/netty/src/main/resources/log4j.properties");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
//            b.option(ChannelOption.TCP_NODELAY, true);
            b.handler(new ServerInitHandler());
            // 启动客户端
            ChannelFuture future = b.connect("localhost", 8090).sync(); // (5)

            // 写入 RPC 请求数据并关闭连接
            Channel channel = future.channel();

            Dog dog = new Dog();
            dog.setName("dog");
            dog.setAge(0);

            channel.writeAndFlush(dog).sync();
            // 等待连接关闭
            channel.closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
