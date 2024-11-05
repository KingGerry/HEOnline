package com.hy.server;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() throws IOException {
//        String ss = loadcontentbypath("D:/map2.txt");
//
//        byte[] rtn = ByteBufUtil.decodeHexDump(ss);
//
//        for (int i = 0; i < rtn.length; i++) {
//            int b = rtn[i] & 0xFF;
//            if(b < 255){
//                System.out.print("88888");
//            }else{
//                System.out.print("     ");
//            }
//            if(i != 0 && i % 384 == 0){
//                System.out.println();
//            }
//        }
//
//        User n = new User();
//        n.setUserName("hy98751");
//        BackUser aa = userService.Login(n);
//        Contexts.maps.get("1601").getMapObjs();
//        String bb = "";


    }

    public static String loadcontentbypath(String path) throws IOException, FileNotFoundException {
        InputStream is = new FileInputStream(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
        return buffer.toString();

    }

}
