package com.learn.sky.netty.rpc;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: JiuBuKong
 * @Date: 2019/3/19 下午2:29
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        RpcResponse rpcResponse = invoke(request);
        // 写入 RPC 响应对象并自动关闭连接
        ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private RpcResponse invoke(RpcRequest rpcRequest) {
        //模拟反射调用
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        System.out.println("request:"+rpcRequest.getRequestId());
        //... some operation
        return rpcResponse;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
