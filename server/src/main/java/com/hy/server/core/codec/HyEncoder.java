package com.hy.server.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.List;

@ChannelHandler.Sharable
@Component
public class HyEncoder extends MessageToMessageEncoder<byte[]> {

    @Autowired
    CoderCommon coderCommon;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, byte[] bs, List<Object> list) throws Exception {
        list.add(Unpooled.wrappedBuffer(coderCommon.encode(bs)));
    }
}
