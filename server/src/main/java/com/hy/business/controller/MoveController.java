package com.hy.business.controller;

import com.hy.business.backentity.Role;
import com.hy.business.context.Contexts;
import com.hy.business.annotation.NettyController;
import com.hy.business.annotation.NettyMethod;
import com.hy.business.utils.Converts;
import com.hy.business.utils.Core;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@NettyController
@Component
public class MoveController extends BaseController {
    @NettyMethod(value = "51-1")
    public Object MoveTo(ChannelHandlerContext ctx, ByteBuf o) throws IOException {
        Role r = ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get();
        int a1 = o.readByte();
        int newx = o.readShortLE();
        int newy = o.readShortLE();
        int oldx = o.readShortLE();
        int oldy = o.readShortLE();

//        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
//        tmp.write(4);
//        tmp.write(3);
//        tmp.write(1);
//        tmp.write(0);
//
//        tmp.write(Converts.intToByteArray(r.getRoleId(),4));
//        tmp.write(Converts.intToByteArray(r.getX(),2));
//        tmp.write(Converts.intToByteArray(r.getY(),2));
//        tmp.write(StringUtil.decodeHexDump("00 01 07 04 00 04 DE 2E 2E F2 A6 61 16 F6 00 00 46 71 00 00 00 00 00 00 10 00 03 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 F5 B3 48 01 34 01 10 00 9B 39 00 00 34 12 00 68 1A 67 38 00 A0 09 02 00 00 00 00 00 C2 39 00 00 6A 12 00 E2 18 8E 3B 00 10 FE 02 00 00 00 00 00 FD 39 00 00 AD 12 00 C9 1A C8 3B 00 20 C5 02 00 00 00 00 00 0B 3A 00 00 BF 12 00 87 1B 77 38 00 FC 4E 02 00 00 00 00 00 BC 39 00 00 5F 12 00 A9 1B 88 39 00 00 00 01 00 00 00 00 00 B1 39 00 00 4E 12 00 A8 1A D7 37 00 20 0F 02 00 00 00 00 00 EB 39 00 00 96 12 00 34 1A B7 33 00 20 40 02 00 00 00 00 00 16 3A 00 00 CA 12 00 C8 1B 37 38 00 F8 F8 02 00 00 00 00 00 9F 39 00 00 38 12 00 F8 1E 97 36 00 48 52 02 00 00 00 00 00 A0 39 00 00 39 12 00 58 1F 07 36 00 F8 56 02 00 00 00 00 00 A1 39 00 00 3A 12 00 58 20 07 36 00 00 71 02 00 00 00 00 00 A2 39 00 00 3B 12 00 98 1C C7 37 00 18 31 02 00 00 00 00 00 A3 39 00 00 3C 12 00 D8 1E C7 35 00 68 55 02 00 00 00 00 00 A4 39 00 00 3D 12 00 B8 20 E7 36 00 30 69 02 00 00 00 00 00 A5 39 00 00 3E 12 00 B8 1F 17 37 00 20 61 02 00 00 00 00 00 FF 39 00 00 AF 12 00 E9 1D E8 38 00 C4 40 02 00 00 00 00 00".replace(" ","")));
//        Core.SendToNearRoleNotMe(tmp.toByteArray(),r,newx,newy);//发给我周边的人,告诉他们我进入到你们边儿上啦~！




        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(new byte[]{0x33,1});
        out.write(Converts.intToByteArray(r.getRoleId(),4));
        out.write(19);
        out.write(Converts.intToByteArray(oldx,2));
        out.write(Converts.intToByteArray(oldy,2));
        int aa = (int)(Math.random() * 999);
        out.write(Converts.intToByteArray(Contexts.getNowTimeSeconds() * 1000 + aa,4));
        out.write(1);
        out.write(Converts.intToByteArray(newx,2));
        out.write(Converts.intToByteArray(newy,2));
        out.write(Converts.intToByteArray(Contexts.getNowTimeSeconds() * 1000 + aa + 500,4));

        Core.SendToNearRole(Converts.packageByte(out.toByteArray()),r);//发给我周边的人,告诉他们我在移动


//        Contexts.channelGroup.writeAndFlush(Converts.packageByte(out.toByteArray()));
        return null;



    }

    @NettyMethod(value = "51-3")
    public Object MoveOk(ChannelHandlerContext ctx, ByteBuf o) throws IOException {
        Role r = ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get();
        int newx = o.readShortLE();
        int newy = o.readShortLE();

        //F4 44 0A 33 03 48 17 78 38 00
        //F4 44 0E 33 03 1B 9D 11 06 48 17 78 38 00
        //F4 44 0E 33 03 01 E1 F5 05 E8 17 87 38 00

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(new byte[]{0x33,3});
        out.write(Converts.intToByteArray(ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get().getRoleId(),4));
        out.write(Converts.intToByteArray(newx,2));
        out.write(Converts.intToByteArray(newy,2));
        out.write(new byte[]{0});

        r.setX(newx);
        r.setY(newy);

//        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
//        out1.write(4);
//        out1.write(2);
//        out1.write(Converts.intToByteArray(r.getRoleId(),4));
//        Core.SendToOutRole(out1.toByteArray(),r);//发给我周边大于3000码的人,告诉他们我离开你们的视线范围了



        return Converts.packageByte(out.toByteArray());


//        ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get().setX(newx);
//        ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get().setY(newy);
    }
}
