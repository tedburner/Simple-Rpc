package com.simple.rpc.common.codec;

import com.simple.rpc.common.util.SerializationUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 09:46
 * @description: 编码 RPC 响应
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
        if (genericClass.isInstance(o)) {
            byte[] data = SerializationUtils.serialize(o);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
