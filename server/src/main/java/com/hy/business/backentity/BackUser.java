package com.hy.business.backentity;

import com.hy.business.utils.Converts;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import org.springframework.context.event.EventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Data
public class BackUser {

    private int id;
    private String userName;
    private String passWord;
    private List<Role> roles;

    @EventListener
    public void eventListener(){

    }

    public byte[] returnMethod() throws IOException {
        return this.getByte();

    }

    public byte[] getByte() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(1);//command
        out.write(7);//method
        out.write(roles.size());//共几个角色

//        01 //共几个角色
//        01
//        1B 9D 11 06
//        04
//        D4 DA D8 AD 00 00 00 00 00 00
//        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
//        00 00
//        32 00 00 00 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 07 04 00 04 DE 2E 2E F2 A6 61 16 F6 00 00 46 71 00 00 00 00 00 00 10 00 08 00 06 00 0B 00 06 00 08 00 01 00 00 00 00 00 00
//
//        00 00 0D 32 32 33 2E 37 32 2E 38 30 2E 32 33 35 E6 07 09 18 0C 00
        for (int i = 0; i < roles.size(); i++) {
            Role r = roles.get(i);
            out.write(r.getRoleIndex());//当前roleIndex
            out.write(Converts.intToByteArray(r.getRoleId(),4));//9位roleId
//            out.write(StringUtil.decodeHexDump("1B9D1106"));
            out.write(r.getName().getBytes().length);//角色名称长度
            out.write(Converts.getFixedArray(r.getName().getBytes(),10));//角色名字

            out.write(new byte[21]);

            out.write(Converts.intToByteArray(r.getLv(),2));//level?
//            out.write(new byte[67]);
            out.write(StringUtil.decodeHexDump("32 00 00 00 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 ".replace(" ","")));


            out.write(r.getRoleSex());
            out.write(r.getR1());
            out.write(r.getR2());
            out.write(r.getR3());
            out.write(r.getR4());
            for (int j = 0; j < 7; j++) {
                //1武器 2帽子 3护腕 4衣服 5面具 6披风 7鞋子 8戒指 9项链(8和9不能返回)
                out.write(Converts.intToByteArray(r.getZhuangBeis().get(j).getObjId(),2));
            }
            out.write(0);
            out.write(0);
            out.write(0);
            out.write(0);
            for (int j = 0; j < 7; j++) {
                out.write(Converts.intToByteArray(r.getZhuangBeis().get(j).getObjValue(),2));
            }
            out.write(0);
            out.write(0);
            out.write(0);
            out.write(0);

            out.write(0);


        }

        //out.write(StringUtil.decodeHexDump("00 00 0D 32 32 33 2E 37 32 2E 38 30 2E 32 33 35 E6 07 09 18 0C 00".replace(" ","")));//服务器当前时间。 上次登录日期  登录IP之类的数据，先不加
        out.write(0);
        out.write(0);

        String ip = "127.0.0.1";
        out.write(ip.getBytes().length);
        out.write(ip.getBytes());
        out.write(Converts.intToByteArray(Calendar.getInstance().get(Calendar.YEAR),2));
        out.write(Calendar.getInstance().get(Calendar.MONTH) + 1);
        out.write(Calendar.getInstance().get(Calendar.DATE));
        out.write(Calendar.getInstance().get(Calendar.HOUR));
        out.write(Calendar.getInstance().get(Calendar.MINUTE));
        return out.toByteArray();
    }
}
