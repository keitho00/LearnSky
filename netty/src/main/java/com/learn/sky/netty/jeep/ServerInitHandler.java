package com.learn.sky.netty.jeep;

import com.learn.sky.netty.jeep.handler.in.*;
import com.learn.sky.netty.jeep.handler.out.JeepMessageEncoder;
import com.learn.sky.netty.jeep.handler.out.JeepMessageOutHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午11:14
 */
public class ServerInitHandler extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new JeepMessageEncoder()); //1
        ch.pipeline().addLast(new JeepMessageOutHandler());//2
        ch.pipeline().addLast(new JeepMessageDecoder(1024, 0, 4)); //3
        ch.pipeline().addLast(new JeepMessageHandler());//4
        ch.pipeline().addLast(new DogInHandler());//5
        ch.pipeline().addLast(new CatInHandler());//6
        ch.pipeline().addLast(new JeepMessageHandlerAgain());//7

    }


}
