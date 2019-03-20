package com.learn.sky.netty.rpc;

import com.weibo.api.motan.codec.Serialization;
import com.weibo.api.motan.serialize.Hessian2Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: JiuBuKong
 * @Date: 2019/3/20 下午4:07
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    Serialization serialization = new Hessian2Serialization();

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = serialization.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}