package com.hy.business.controller;

import com.hy.business.context.Contexts;
import com.hy.business.annotation.NettyController;
import com.hy.business.annotation.NettyMethod;
import com.hy.business.utils.Converts;
import com.hy.business.utils.Core;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@NettyController
@Component
public class TalkController extends BaseController {

    @NettyMethod(value = {"3-1"})
    public byte[] AAA(ChannelHandlerContext ctx, ByteBuf o) throws IOException {
        int t = o.readByte();//轻频 6 ， 队频道4 ，帮2
        int a2 = o.readByte();
        int length = o.readUnsignedByte();
        byte[] words_byte  = ByteBufUtil.getBytes(o.readBytes(length));
        String words = new String(words_byte);

        o.release();

        ByteArrayOutputStream rtn = new ByteArrayOutputStream();
        rtn.write(6);
        rtn.write(1);
        if(t == 1) {
            rtn.write(2);
        }else if(t == 6) {
            rtn.write(7);//轻
        }else if(t == 4){
            rtn.write(5);//队
        }
        rtn.write(Converts.intToByteArray(ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get().getRoleId(),4));
        rtn.write(length);
        rtn.write(words_byte);
        rtn.write(0);
        //06 01 02 1B 9D 11 06 01 31 00
        if(t == 1)
            Core.SendToAllRole(Converts.packageByte(rtn.toByteArray()));
        else if (t == 6)
            Core.SendToNearRole(Converts.packageByte(rtn.toByteArray()),ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get());
        return null;
    }
}
