package com.learn.sky.netty.jeep.handler.in;

import com.learn.sky.netty.jeep.entity.Header;
import com.learn.sky.netty.jeep.entity.JeepMessage;
import com.weibo.api.motan.codec.Serialization;
import com.weibo.api.motan.serialize.Hessian2Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/25 下午8:03
 */
public class JeepMessageDecoder extends LengthFieldBasedFrameDecoder {

    Serialization serialization = new Hessian2Serialization();

    public JeepMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        if (byteBuf == null) {
            return null;
        }
        JeepMessage message = new JeepMessage();
        Header header = new Header();
        header.setLength(byteBuf.readInt());
        header.setProtoType(byteBuf.readInt());
        Integer clazzLength = byteBuf.readInt();
        header.setClazzLength(clazzLength);
        byte[] clazzNameData = new byte[clazzLength];
        byteBuf.readBytes(clazzNameData);
        String clazzName = new String(clazzNameData,"UTF-8");
        header.setClazzName(clazzName);

        int dataLength = byteBuf.readInt();
        if (dataLength > 0) {
            byte[] data = new byte[dataLength];
            byteBuf.readBytes(data);
            Class c = Class.forName(clazzName);
            message.setBody(serialization.deserialize(data, c));
        }

        return message;
    }
}
