package com.hy.server.core.codec;

import io.netty.buffer.ByteBufUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CoderCommon {
    @Value("${hy.core.key}")
    private Integer coreKey;

    public byte[] encode(byte[] in){
        byte[] bytes = in;

        byte[] rtn = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            rtn[i] = (byte)(bytes[i] & 0xFF ^ coreKey);
        }

        return rtn;
    }

    public int encode(int in){
        return in & 0xFF ^ coreKey;
    }
}
