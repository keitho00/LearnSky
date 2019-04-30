package com.learn.sky.netty.jeep.handler.out;

import com.learn.sky.netty.jeep.entity.JeepMessage;
import com.weibo.api.motan.codec.Serialization;
import com.weibo.api.motan.serialize.Hessian2Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码
 * @Author: JiuBuKong
 * @Date: 2019/4/25 下午8:36
 */
public class JeepMessageEncoder extends MessageToByteEncoder<JeepMessage> {

    Serialization serialization = new Hessian2Serialization();


    @Override
    protected void encode(ChannelHandlerContext ctx, JeepMessage msg, ByteBuf out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        out.writeInt(msg.getHeader().getLength());
        out.writeInt(msg.getHeader().getProtoType());
        out.writeInt(msg.getHeader().getClazzLength());
        out.writeBytes(msg.getHeader().getClazzName().getBytes());

        byte[] data = serialization.serialize(msg.getBody());
        out.writeInt(data.length);
        out.writeBytes(data);

        out.setInt(0, out.readableBytes() - 4);
    }


}
