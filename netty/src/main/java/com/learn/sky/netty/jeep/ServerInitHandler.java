package com.learn.sky.netty.jeep;

import com.learn.sky.netty.jeep.handler.in.CatInHandler;
import com.learn.sky.netty.jeep.handler.in.DogInHandler;
import com.learn.sky.netty.jeep.handler.in.JeepMessageDecoder;
import com.learn.sky.netty.jeep.handler.in.JeepMessageHanler;
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
        ch.pipeline().addLast(new JeepMessageEncoder());
        ch.pipeline().addLast(new JeepMessageOutHandler());
        ch.pipeline().addLast(new JeepMessageDecoder(1024, 0, 4));
        ch.pipeline().addLast(new JeepMessageHanler());
        ch.pipeline().addLast(new DogInHandler());
        ch.pipeline().addLast(new CatInHandler());

    }


}
