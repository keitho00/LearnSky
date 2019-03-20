package com.learn.sky.netty.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: JiuBuKong
 * @Date: 2019/3/20 下午4:08
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        System.out.println(response.getRequestId());//处理响应
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}