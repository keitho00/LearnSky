package com.learn.sky.netty.jeep.handler.in;

import com.learn.sky.netty.jeep.entity.JeepMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午10:47
 */
public class JeepMessageHandlerAgain extends SimpleChannelInboundHandler<JeepMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JeepMessage msg) throws Exception {
        System.out.println("receive message");

    }
}
