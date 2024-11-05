package com.hy.business.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.Data;

@Data
public class User {
    public User(){}
    String userName;
    String passWord;

    public User(ByteBuf o){
        o.readUnsignedByte();
        short nameLeng = o.readUnsignedByte();
        String name = new String(ByteBufUtil.getBytes(o.readBytes(nameLeng)));

        short passLeng = o.readUnsignedByte();
        String pass = new String(ByteBufUtil.getBytes(o.retainedSlice()));

        this.userName = name;
        this.passWord = pass;
        o.release();
    }

}
