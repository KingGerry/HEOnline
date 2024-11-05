package com.hy.business.context;

import com.hy.business.backentity.BackUser;
import com.hy.business.backentity.Role;
import com.hy.business.entity.Board;
import com.hy.business.entity.GameMap;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contexts {
    private static int nowTimeSeconds = 0;//走路时的秒累加器

    /**
     * System board
     * */
    public static List<Board> boardLists = new ArrayList<Board>();

    /**
     * All Roles rtxGroup
     * */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static Map<String, GameMap> maps = new HashMap<>();

    public static AttributeKey<BackUser> ATTR_USER_KEY = AttributeKey.valueOf("user");
    public static AttributeKey<Role> ATTR_ROLE_KEY = AttributeKey.valueOf("role");




    public static int getNowTimeSeconds() {
        return nowTimeSeconds;
    }

    public static void setNowTimeSeconds(int nowTimeSeconds) {
        Contexts.nowTimeSeconds = nowTimeSeconds;
    }
}