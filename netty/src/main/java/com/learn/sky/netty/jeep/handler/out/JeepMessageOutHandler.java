package com.learn.sky.netty.jeep.handler.out;

import com.learn.sky.netty.jeep.entity.Header;
import com.learn.sky.netty.jeep.entity.JeepMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午11:05
 */
public class JeepMessageOutHandler extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        JeepMessage jeepMessage = new JeepMessage();
        Header header = new Header();
        header.setLength(0);
        header.setProtoType(0);
        header.setClazzName(msg.getClass().getName());
        header.setClazzLength(msg.getClass().getName().getBytes().length);
        jeepMessage.setHeader(header);
        jeepMessage.setBody(msg);

        super.write(ctx, jeepMessage, promise);
    }
}
