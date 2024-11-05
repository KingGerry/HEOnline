package com.hy.business.context;

public class Commands {
    //R: recv 接收客户端的命令
    public static final byte[] R1_1 = new byte[]{1, 1};


    //B: back 返回给客户端的命令
    public static final byte[] B1_9 = new byte[]{1, 9};
    public static final byte[] B1_10 = new byte[]{1, 10};
    public static final byte[] B4_1 = new byte[]{4, 1};
    public static final byte[] B4_2 = new byte[]{4, 2};
    public static final byte[] B4_3 = new byte[]{4, 3};
    public static final byte[] B5_1_4 = new byte[]{5, 1, 4};
    public static final byte[] B5_1_5 = new byte[]{5, 1, 5};
    public static final byte[] B2_11 = new byte[]{2, 11};
    public static final byte[] B53_2 = new byte[]{53, 2};//A干B
    public static final byte[] B53_24 = new byte[]{53, 24};//PK开启成功
    public static final byte[] B54_6 = new byte[]{54, 6};//PK别人掉血
}
