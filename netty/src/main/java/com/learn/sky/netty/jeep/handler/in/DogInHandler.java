package com.learn.sky.netty.jeep.handler.in;

import com.learn.sky.netty.jeep.entity.Cat;
import com.learn.sky.netty.jeep.entity.Dog;
import com.learn.tool.util.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午10:53
 */
public class DogInHandler extends SimpleChannelInboundHandler<Dog> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Dog msg) throws Exception {
        System.out.println("dog:" + JsonUtils.toJson(msg));
        Cat cat = new Cat();
        cat.setAge(msg.getAge() + 1);
        cat.setName("cat");
        ctx.writeAndFlush(cat);
    }

}
