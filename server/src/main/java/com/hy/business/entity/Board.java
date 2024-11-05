package com.hy.business.entity;

import com.hy.business.context.Commands;
import com.hy.business.utils.Converts;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Data
public class Board {
    String content;

    public byte[] getContentB() throws IOException {
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        tmp.write(Commands.B2_11);
        tmp.write(this.content.getBytes());
        return Converts.packageByte(tmp.toByteArray());//Get board
    }
}
