package com.hy.business.backentity;

import com.hy.business.context.Contexts;
import com.hy.business.entity.GameMap;
import com.hy.business.entity.MapObj;
import com.hy.business.entity.RoleBag;
import com.hy.business.entity.RoleTasks;
import com.hy.business.utils.Core;
import com.hy.business.utils.RolePackageBytes;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Role extends MapObj {
    public ChannelHandlerContext ctx = null;

    private int roleMoveSpeed = 100;//当前Role的Speed
    private int fightingNpcId;//正在战斗的NPCID
    private int fightingRoleId;//正在战斗的角色ID
    private int fightingSpeed = 100;//实时战斗的速度


    private int nowMap;
    private byte roleIndex;
    private int roleId;
    private int userId;
    private int lv;
    private String name;
    private String nickName;
    private byte r1;
    private byte r2;
    private byte r3;
    private byte r4;
    private byte roleSex;
    private int nowExp;
    private int nowHp;
    private int maxHp;
    private int nowMp;
    private int maxMp;
    private int nowSp;
    private int maxSp;
    private int shengYu;
    private int L;
    private int M;
    private int T;
    private int Z;
    private int J;
    private int weiWang;
    private int nowMoney;
    private int onlineTime;
    private int propPoint;
    private int kongfuPoint;
    private int none1;
    private int nowFz;
    private int maxFz;
    private int x;
    private int y;
    private byte pkMode;
    private List<RoleBag> zhuangBeis = new ArrayList<>();
    private List<RoleBag> bags;
    private List<Integer> tasks = new ArrayList<Integer>();
    private List<RoleTasks> completedTasks = new ArrayList<>();
    private List<RoleSpecialSkills> specialSkills = new ArrayList<>();

    private Boolean isOnline = false;

    public void setNowHp(int nowHp) {
        if(nowHp != this.nowHp){
            this.nowHp = nowHp;
            Core.SendToMe(this, RolePackageBytes.packageHP(this));
        }
    }

    public void setMaxHp(int maxHp) {
        if(maxHp != this.maxHp){
            this.maxHp = maxHp;
            Core.SendToMe(this, RolePackageBytes.packageHP(this));
        }
    }

    public void setNowMap(int nowMap){
        if(nowMap != this.nowMap){
            changeMyMap(nowMap,this.nowMap);
            this.nowMap = nowMap;
        }
    }
    /**
     * change my mapid.
     * */
    private void changeMyMap(int newMap,int oldMap){
        //if change map, delete me from the old map;
        if(Contexts.maps.containsKey(Integer.toString(oldMap))) {
            GameMap gm = Contexts.maps.get(Integer.toString(oldMap));
            gm.getMapObjs().remove(this);
        }
        //if change map, add me to the new map;
        if(Contexts.maps.containsKey(Integer.toString(newMap))) {
            GameMap gm = Contexts.maps.get(Integer.toString(newMap));
            gm.getMapObjs().add(this);
        }
    }

    public void setIsOnline(boolean isOnline) throws IOException {
        this.isOnline = isOnline;
        if(isOnline){// I am online, send me to all near roles
            Core.SendToAllRole(RolePackageBytes.packageOnline(this)); //Tell all people , I am online~!
            changeMyMap(this.nowMap,0);
            Core.SendToNearRole(RolePackageBytes.packageStyle(this,true),this);//send me style to near role;
        }else{// I am offline ? what to do??
            Core.SendToNearRole(RolePackageBytes.packageOfflineDeleteMe(this),this); //Tell near people , I am offline and delete me from map.
            Core.SendToAllRole(RolePackageBytes.packageOffline(this)); //Tell all people , I am offline~!
            changeMyMap(0,this.nowMap);
        }
    }



    /**
     * 计算最大血量
     * */
    public int caluMaxHp(){
        setMaxHp(nowHp + 10000);
        return maxHp;
    }
    /*..other properties  */
}









