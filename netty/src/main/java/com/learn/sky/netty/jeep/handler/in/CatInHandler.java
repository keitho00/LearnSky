package com.learn.sky.netty.jeep.handler.in;

import com.learn.sky.netty.jeep.entity.Cat;
import com.learn.sky.netty.jeep.entity.Dog;
import com.learn.tool.util.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * cat的读
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午11:03
 */
public class CatInHandler extends SimpleChannelInboundHandler<Cat> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Cat msg) throws Exception {
        System.out.println("cat:" + JsonUtils.toJson(msg));
        Dog dog = new Dog();
        dog.setAge(msg.getAge() + 1);
        dog.setName("dog");
        ctx.writeAndFlush(dog);
    }
}
