package com.hy.business.utils;

import com.hy.business.backentity.Role;
import com.hy.business.context.Contexts;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Core {
    /*
     * Gerry 2022.9.28
     * Send info to my self;
     */
    public static void SendToMe(Role r, byte[] response){
        if(r.ctx != null && response != null) {
            r.ctx.channel().writeAndFlush(response);
        }
    }
    /*
     * Gerry 2022.9.26
     * Send to All roles in game;
     */
    public static void SendToAllRole(byte[] buf){
        Contexts.channelGroup.writeAndFlush(buf);
    }

    /**
     * Gerry 2022.9.26
     * Send to near me roles;
     */
    public static void SendToNearRole(byte[] buf, Role r) {
        for (Channel channel : Contexts.channelGroup) {
            Role tmp = channel.attr(Contexts.ATTR_ROLE_KEY).get();
            if(tmp == null) continue;
            if(tmp.getNowMap() == r.getNowMap()) { //if in same map
//                int radius = 3000;// 圆的半径
//                Point aPoint = new Point(r.getX(), r.getY());
//                Point bPoint = new Point(tmp.getX(), tmp.getY());
//                // 两点间距离公式
//                int result = (int) Math.sqrt(Math.abs((aPoint.getX() - bPoint.getX())* (aPoint.getX() - bPoint.getX())+(aPoint.getY() - bPoint.getY())* (aPoint.getY() - bPoint.getY())));
//
//                if (result <= radius) {
                    channel.writeAndFlush(buf);
//                }
            }
        }
    }

    /**
     * Gerry 2022.9.26
     * Send to near me roles;
     */
    public static void SendToNearRoleNotMe(byte[] buf, Role r, int newx, int newy) {
        for (Channel channel : Contexts.channelGroup) {
            Role tmp = channel.attr(Contexts.ATTR_ROLE_KEY).get();
            if(tmp == null) continue;
            if(tmp.getNowMap() == r.getNowMap() && tmp.getRoleId() != r.getRoleId()) { //if in same map
                int radius = 3000;// 圆的半径

                Point oldaPoint = new Point(r.getX(),r.getY());
                Point aPoint = new Point(newx, newy);
                Point bPoint = new Point(tmp.getX(), tmp.getY());
                // 两点间距离公式
                int result1 = (int) Math.sqrt(Math.abs((oldaPoint.getX() - bPoint.getX())* (oldaPoint.getX() - bPoint.getX())+(oldaPoint.getY() - bPoint.getY())* (oldaPoint.getY() - bPoint.getY())));
                if(result1 < radius){
                    continue;
                }
                int result = (int) Math.sqrt(Math.abs((aPoint.getX() - bPoint.getX())* (aPoint.getX() - bPoint.getX())+(aPoint.getY() - bPoint.getY())* (aPoint.getY() - bPoint.getY())));

                if (result <= radius) {
                    channel.writeAndFlush(Converts.packageByte(buf));
                }
            }
        }
    }

    /*
     * Gerry 2022.9.26
     * Get role info from ctx;
     */
    public static Role getRoleFromCtx(ChannelHandlerContext ctx){
        return ctx.channel().attr(Contexts.ATTR_ROLE_KEY).get();
    }

    public static void SendToOutRole(byte[] buf, Role r) {
        for (Channel channel : Contexts.channelGroup) {
            Role tmp = channel.attr(Contexts.ATTR_ROLE_KEY).get();
            if(tmp == null) continue;
            if(tmp.getNowMap() == r.getNowMap()) { //if in same map
                int radius = 3000;// 圆的半径
                Point aPoint = new Point(r.getX(), r.getY());
                Point bPoint = new Point(tmp.getX(), tmp.getY());
                // 两点间距离公式
                int result = (int) Math.sqrt(Math.abs((aPoint.getX() - bPoint.getX())* (aPoint.getX() - bPoint.getX())+(aPoint.getY() - bPoint.getY())* (aPoint.getY() - bPoint.getY())));
//                int result = (int) Math.sqrt((aPoint.x - bPoint.x) + (aPoint.y - bPoint.y));

                if (result > radius) {
                    channel.writeAndFlush(Converts.packageByte(buf));
                }
            }
        }
    }


    public static List<Role> GetNearMeRoles(Role r){
        List<Role> rtn = new ArrayList<Role>();
        for (Channel channel : Contexts.channelGroup) {
            Role tmp = channel.attr(Contexts.ATTR_ROLE_KEY).get();
            if(tmp == null) continue;
            if(tmp.getNowMap() == r.getNowMap()) { //if in same map
//                int radius = 3000;// 圆的半径
//                Point aPoint = new Point(r.getX(), r.getY());
//                Point bPoint = new Point(tmp.getX(), tmp.getY());
//                // 两点间距离公式
//                int result = (int) Math.sqrt(Math.abs((aPoint.getX() - bPoint.getX())* (aPoint.getX() - bPoint.getX())+(aPoint.getY() - bPoint.getY())* (aPoint.getY() - bPoint.getY())));
//                if (result < radius) {
                    rtn.add(tmp);
//                }
            }
        }
        return rtn;
    }

    public static List<Role> GetNearMeRolesNotMe(Role r) {
        List<Role> rtn = new ArrayList<Role>();
        for (Channel channel : Contexts.channelGroup) {
            Role tmp = channel.attr(Contexts.ATTR_ROLE_KEY).get();
            if(tmp == null) continue;
            if(tmp.getNowMap() == r.getNowMap() && tmp.getRoleId() != r.getRoleId()) { //if in same map
//                int radius = 3000;// 圆的半径
//                Point aPoint = new Point(r.getX(), r.getY());
//                Point bPoint = new Point(tmp.getX(), tmp.getY());
//                // 两点间距离公式
//                int result = (int) Math.sqrt(Math.abs((aPoint.getX() - bPoint.getX())* (aPoint.getX() - bPoint.getX())+(aPoint.getY() - bPoint.getY())* (aPoint.getY() - bPoint.getY())));
////                int result = (int) Math.sqrt((aPoint.x - bPoint.x) + (aPoint.y - bPoint.y));
//
//                if (result < radius) {
                    rtn.add(tmp);
                //}
            }
        }
        return rtn;
    }
}
