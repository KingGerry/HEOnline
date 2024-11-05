package com.hy.business.utils;

import java.nio.ByteBuffer;

public class Converts {

    /**
     * int到byte[]
     *
     * @param i
     * @return
     */
    public static byte[] intToByteArray(int i,int length) {
        byte[] result = new byte[length];
        //由低位到高位
        if(length == 4) {
            result[3] = (byte) ((i >> 24) & 0xFF);
            result[2] = (byte) ((i >> 16) & 0xFF);
            result[1] = (byte) ((i >> 8) & 0xFF);
            result[0] = (byte) (i & 0xFF);
        }
        else if(length == 2) {
//            result[3] = (byte) ((i >> 24) & 0xFF);
//            result[2] = (byte) ((i >> 16) & 0xFF);
            result[1] = (byte) ((i >> 8) & 0xFF);
            result[0] = (byte) (i & 0xFF);
        }

        return result;
    }

    /*
    * 获取一个指定长度的byte[] 并填充inarray, 如果inaary大于length,将截取掉
    * */
    public static byte[] getFixedArray(byte[] inarray,int length){
        byte[] rtn = new byte[length];
        System.arraycopy(inarray,0,rtn,0,inarray.length > length ? length : inarray.length);
        return rtn;
    }

    /*
     * Gerry: 2022.9.24
     * N个数组拼接成一个大数组
     * 报文结构说明： byte[]  X1,X2,X3,X4,X5,[XX...]
     * X1:报文头 固定0xF4
     * X2:报文头 固定0x44
     * X3:报文整体长度， 包含报文头，自己，及后面所有的总长度
     * X4:命令Command
     * X5:方法Method
     * [XX]:报文内容
     * */
    public static byte[] packageByte(byte[]... bytes){

        byte[] rtn = new byte[0];//最终返回的数组

        for (int i = 0; i < bytes.length; i++) {
            byte[] b = bytes[i];
            if(b == null) continue;
            byte[] tmp = new byte[b.length + (b.length > 252 ? 4 : 3)];//拼上报文头和长度后的单个整报文
            if(tmp.length > 255){
                tmp[0] = (byte) 0xF5;//报文头
                tmp[1] = (byte) 0xB3;//报文头
                tmp[2] = (byte) (tmp.length & 0xFF);//报文整体长度
                tmp[3] = (byte) ((tmp.length >> 8) & 0xFF);//报文整体长度
                System.arraycopy(b,0,tmp,4,b.length);//copy内容部分
            }else {
                tmp[0] = (byte) 0xF4;//报文头
                tmp[1] = (byte) 0x44;//报文头
                tmp[2] = (byte) tmp.length;//报文整体长度
                System.arraycopy(b,0,tmp,3,b.length);//copy内容部分
            }
            rtn = ArrayConcat(rtn,tmp);
        }
        return rtn;
    }

    private static byte[] ArrayConcat(byte[] rtn, byte[] tmp) {
        return ByteBuffer.allocate(rtn.length + tmp.length)
                .put(rtn)
                .put(tmp)
                .array();
    }
}
