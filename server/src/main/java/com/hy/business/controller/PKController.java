package com.hy.business.controller;

import com.hy.business.backentity.Role;
import com.hy.business.context.Commands;
import com.hy.business.context.Contexts;
import com.hy.business.annotation.NettyController;
import com.hy.business.annotation.NettyMethod;
import com.hy.business.utils.Converts;
import com.hy.business.utils.Core;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@NettyController
@Component
public class PKController extends BaseController {

    /*
    *Open PK mode
    * */
    @NettyMethod(value = {"53-7"})
    public byte[] OpenPK(ChannelHandlerContext ctx, ByteBuf o) throws IOException {
        Role r = ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get();
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B53_24);
        out.writeBytes(Converts.intToByteArray(r.getRoleId(),4));
        out.writeByte(r.getPkMode() == 0 ? 1 : 0);
        r.setPkMode(r.getPkMode() == 0 ? (byte)1 : 0);
        Core.SendToNearRole(Converts.packageByte(ByteBufUtil.getBytes(out)),r);
        return null;
        //F4 44 0A 35 18 87 4B FE 05 01
    }

    /*
    * role to role PK
    * */
    @NettyMethod(value = {"53-2"})
    public byte[] RoleKillRole(ChannelHandlerContext ctx, ByteBuf o) throws IOException {
        int who = o.readIntLE();
        Role r = ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get();

        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B53_2);
        out.writeBytes(Converts.intToByteArray(r.getRoleId(),4));
        out.writeBytes(Converts.intToByteArray(who,4));
        Core.SendToNearRole(Converts.packageByte(ByteBufUtil.getBytes(out)),r);

        ctx.channel().eventLoop().scheduleAtFixedRate(() -> {
            //F4 44 0A 05 01 05 57 03 5C 03 //这是掉内力的
            roleAPkRoleB(r,who);
        },0,1000, TimeUnit.MILLISECONDS);

        return null;
    }

    private void roleAPkRoleB(Role role1, int who){
        ////F4 44 18  36 06   82 9F 1D 06  87 4B FE 05    3D 00 00 01 00 00 00 00 00 40 06
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B54_6);
        out.writeBytes(Converts.intToByteArray(role1.getRoleId(),4));
        out.writeBytes(Converts.intToByteArray(who,4));
        out.writeByte(0x6D);
        out.writeByte(10);
        out.writeByte(0);
        out.writeByte(0x11);
        out.writeByte(0x11);
        out.writeByte(0x11);
        out.writeByte(10);
        out.writeByte(10);
        out.writeByte(5);
        out.writeByte(200);
        out.writeByte(16);
        Core.SendToNearRole(Converts.packageByte(ByteBufUtil.getBytes(out)),role1);
    }
}
