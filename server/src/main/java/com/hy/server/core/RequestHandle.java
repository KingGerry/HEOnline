package com.hy.server.core;

import com.hy.business.controller.BaseController;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

@Component
public class RequestHandle implements ApplicationContextAware {

    private static ApplicationContext app;
    /*
    * 请求分发器 ， 分发到各个控制层，然后写给客户
    * 参数1 控制器
    * 参数2 方法
    * */
    public static void doHandle(Map<String, Method> handlerMap, Integer command, Integer method, ChannelHandlerContext ctx,Object o) throws InvocationTargetException, IllegalAccessException {
        try {
            //这里还需要对访问接口进行控制，如果没有登录成功，是不能分发的 还没做哦 ..... doing here
            System.out.println();
            System.out.println(command + "-" + method +" begin");
            Method m = handlerMap.get(command + "-" + method);

            System.out.println( "Recv->" + twoSpaceTwo(ByteBufUtil.getBytes((ByteBuf) o)));
            Object[] obj = new Object[]{ctx,o};

            BaseController ttt = (BaseController)app.getBean(m.getDeclaringClass());
            byte[] rtn = (byte[])m.invoke(ttt,obj);

            if(rtn != null){
                ctx.channel().writeAndFlush(rtn);
                System.out.println("ToClient->" + twoSpaceTwo(rtn));
            }else{
                System.out.println("ToClient-> null");
            }
            System.out.println(command + "-" + method +" end");

//            return m.invoke(m.getDeclaringClass().newInstance(), obj);
        }catch(Exception ex){
            System.out.println((ex));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.app = applicationContext;
    }

    private static String twoSpaceTwo(byte[] replace){
//        String rtn = " " + StringUtil.toHexString(replace).toUpperCase();
        String rtn = ByteBufUtil.hexDump(replace).toUpperCase(Locale.ROOT);

        String regex = "(.{2})";
        return rtn.replaceAll(regex,"$1 ");
    }
}
