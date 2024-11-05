package com.hy.business.utils;

import com.hy.business.backentity.Role;
import com.hy.business.context.Commands;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.StringUtil;

/*
* About role info package bytes
* */
public class RolePackageBytes {
    //气血
    public static byte[] packageHP(Role r) {
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B5_1_4);
        out.writeBytes(Converts.intToByteArray(r.getNowHp(), 4));//method
        out.writeBytes(Converts.intToByteArray(r.getMaxHp(), 4));//method
        return Converts.packageByte(ByteBufUtil.getBytes(out));
    }

    //内力
    public static byte[] packageMP(Role r) {
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B5_1_5);
        out.writeBytes(Converts.intToByteArray(r.getNowMp(), 2));//method
        out.writeBytes(Converts.intToByteArray(r.getMaxMp(), 2));//method
        return Converts.packageByte(ByteBufUtil.getBytes(out));
    }

    public static byte[] packageOnline(Role r){
        //F4 44 1B 01 09 1F CE 14 06 08 33 33 34 34 35 35 36 36 00 00 00 00 00 00 00 00 00
        //F4 44 1D 01 09 1F AF 0A 0C 0A 35 35 36 36 37 37 38 38 39 39 00 00 00 00 00 00 00 00 00
        //F4 44 1C 01 09 03 E1 F5 05 00 00 00 06 C4 E3 B0 D6 B0 D6 00 03 00 09 3D 06 00 00 00
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B1_9);
        out.writeBytes(Converts.intToByteArray(r.getRoleId(),4));
        out.writeByte(r.getName().getBytes().length);
        out.writeBytes(r.getName().getBytes());
        out.writeBytes(new byte[]{00 ,03 ,00 ,9, 0x3D, 06, 00, 00, 00});
        return Converts.packageByte(ByteBufUtil.getBytes(out));
    }

    public static byte[] packageOffline(Role r){
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B1_10);
        out.writeBytes(Converts.intToByteArray(r.getRoleId(),4));
        return Converts.packageByte(ByteBufUtil.getBytes(out));
    }

    public static byte[] packageOfflineDeleteMe(Role r){
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        out.writeBytes(Commands.B4_2);
        out.writeBytes(Converts.intToByteArray(r.getRoleId(),4));
        return Converts.packageByte(ByteBufUtil.getBytes(out));
    }

    public static byte[] packageStyle(Role r, boolean isMe){
        ByteBuf out = PooledByteBufAllocator.DEFAULT.heapBuffer();
        if(isMe) {
            out.writeBytes(Commands.B4_3);
            out.writeByte(1);
            out.writeByte(0);
        }else{
            out.writeBytes(Commands.B4_1);
        }

        out.writeBytes(Converts.intToByteArray(r.getRoleId(),4));
        out.writeBytes(Converts.intToByteArray(r.getX(),2));
        out.writeBytes(Converts.intToByteArray(r.getY(),2));
        out.writeByte(0);//这个好像是层级，它在第几层？
        out.writeByte(r.getRoleSex());
        out.writeByte(r.getR1());
        out.writeByte(r.getR2());
        out.writeByte(r.getR3());
        out.writeByte(r.getR4());
        for (int i = 0; i < 7; i++) {
            //1武器 2帽子 3护腕 4衣服 5面具 6披风 7鞋子 8戒指 9项链(8和9不能返回)
            out.writeBytes(Converts.intToByteArray(r.getZhuangBeis().get(i).getObjId(),2));
        }
        out.writeByte(0);
        out.writeByte(0);
        out.writeByte(0);
        out.writeByte(0);
        for (int i = 0; i < 7; i++) {
            out.writeBytes(Converts.intToByteArray(r.getZhuangBeis().get(i).getObjValue(),2));
        }
        out.writeByte(0);
        out.writeByte(0);
        out.writeByte(0);
        out.writeByte(0);

        out.writeBytes(StringUtil.decodeHexDump("01 01 01 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 ".replace(" ","")));
        return Converts.packageByte(ByteBufUtil.getBytes(out));
        // F4 44 4F 04 03 01 00 13 A3 FF 05 C8 1C 07 34 00 00 02 02 02 02 00 00 00 00 00 00 D7 59 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
        // F5 B3 10 02 34 01 1A 00 47 3C 00 00 E8 13 00 F8 1E 97 36 00 48 52 02 00 00 00 00 00 48 3C 00 00 E9 13 00 58 1F 07 36 00 F8 56 02 00 00 00 00 00 49 3C 00 00 EA 13 00 58 20 07 36 00 00 71 02 00 00 00 00 00 4A 3C 00 00 EB 13 00 98 1C C7 37 00 18 31 02 00 00 00 00 00 4B 3C 00 00 EC 13 00 D8 1E C7 35 00 68 55 02 00 00 00 00 00 4C 3C 00 00 ED 13 00 B8 20 E7 36 00 30 69 02 00 00 00 00 00 4D 3C 00 00 EE 13 00 B8 1F 17 37 00 20 61 02 00 00 00 00 00 FB 3B 00 00 9A 13 00 C9 18 48 31 00 E0 D0 02 00 00 00 00 00 59 3C 00 00 FE 13 00 A8 1A D7 37 00 20 0F 02 00 00 00 00 00 90 3C 00 00 48 14 00 34 1A B7 33 00 20 40 02 00 00 00 00 00 B2 3C 00 00 6E 14 00 C8 1B 37 38 00 F8 F8 02 00 00 00 00 00 43 3C 00 00 E4 13 00 68 1A 67 38 00 A0 09 02 00 00 00 00 00 64 3C 00 00 0F 14 00 A9 1B 88 39 00 00 00 01 00 00 00 00 00 6A 3C 00 00 1A 14 00 E2 18 8E 3B 00 10 FE 02 00 00 00 00 00 9E 3C 00 00 58 14 00 C9 1A C8 3B 00 20 C5 02 00 00 00 00 00 A9 3C 00 00 65 14 00 87 1B 77 38 00 FC 4E 02 00 00 00 00 00 91 3C 00 00 49 14 00 3D 1F C0 32 00 C0 44 02 00 00 00 00 00 A0 3C 00 00 5A 14 00 E9 1D E8 38 00 C4 40 02 00 00 00 00 00 E7 3B 00 00 86 13 00 49 23 78 2F 00 30 C0 02 00 00 00 00 00 1A 3C 00 00 BB 13 00 5E 25 47 31 00 00 00 02 00 00 00 00 00 1B 3C 00 00 BC 13 00 5E 25 34 2F 00 00 00 02 00 00 00 00 00 50 3C 00 00 F3 13 00 F8 20 57 31 00 30 B3 02 00 00 00 00 00 7E 3C 00 00 30 14 00 E8 20 27 36 00 30 69 02 00 00 00 00 00 A1 3C 00 00 5C 14 00 69 21 B6 3B 00 C4 40 02 00 00 00 00 00 A2 3C 00 00 5D 14 00 69 21 52 3B 00 C4 40 02 00 00 00 00 00 BD 3C 00 00 7A 14 00 69 24 A8 3B 00 00 80 02 00 00 00 00 00
        // F4 44 1B 34 01 01 00 0E 3C 00 00 AF 13 01 C0 1C B0 38 00 00 00 02 00 00 00 00 00 F4 44 07 05 01 01 1C F4 44 2F 16 03 01 B0 05 1C 06 01 05 48 4F 4D 45 31 00 00 00 00 00 00 00 00 00 00 00 D8 09 67 2D 00 00 00 00 27 23 00 0F 00 00 00 05 00 01 01 F4 44 07 05 01 31 00 F4 44 9B 08 11 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 F4 44 19 3F 07 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 F4 44 06 38 13 00 F4 44 06 38 16 00
    }


}
