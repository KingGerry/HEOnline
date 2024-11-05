package com.hy.server.core.codec;

import io.netty.buffer.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.annotation.Resource;
import java.util.List;

@ChannelHandler.Sharable
@Component
public class HyDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Autowired
    private CoderCommon coderCommon;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
//        UnpooledByteBufAllocator.DEFAULT.heapBuffer();
//        byte[] recv = ByteBufUtil.getBytes(byteBuf);
        //byteBuf.release();
        while(byteBuf.isReadable()) {
            int head1 = coderCommon.encode(byteBuf.readByte());
            int head2 = coderCommon.encode(byteBuf.readByte());

            if (head1 == 244 && head2 == 68) {
                int length = coderCommon.encode(byteBuf.readByte());
                byte[] content = coderCommon.encode(ByteBufUtil.getBytes(byteBuf.readBytes(length - 3)));
                ByteBuf rtn = PooledByteBufAllocator.DEFAULT.heapBuffer(length - 3);
                rtn.writeBytes(content);
                list.add(rtn);
            }
        }
        //byteBuf.release();



//        while(byteBuf.readerIndex() < byteBuf.maxCapacity()) {
//            byte[] bb = new byte[]{0x11,0X06};
//
//            int head1 = coderCommon.encode(byteBuf.getByte(0));
//            int head2 = coderCommon.encode(byteBuf.getByte(1));
//
//            if(head1 == 244 && head2 == 68){
//                int length = coderCommon.encode(byteBuf.getByte(2));
//                int[] content = coderCommon.encode(ByteBufUtil.getBytes(byteBuf,0,length));
//                list.add(content);
//            }
//        }
    }
}
