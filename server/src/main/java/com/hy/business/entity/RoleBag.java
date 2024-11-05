package com.hy.business.entity;

import lombok.Data;

@Data
public class RoleBag {
    private Integer id;

    private Integer roleid;
    private Integer btype;

    /**
    * 序号
    */
    private Integer no;

    /**
    * 物品ID
    */
    private Integer objId;

    /**
    * 数量
    */
    private Integer count;

    /**
    * 物品值 武防攻击防御值  龙加几 宝石加几 战骑加几
    */
    private Integer objValue;

    /**
    * 特殊属性1
    */
    private Integer add1;

    /**
    * 特殊属性2
    */
    private Integer add2;

    /**
    * 特殊属性3
    */
    private Integer add3;

    /**
    * 镶嵌的宝石1
    */
    private Integer stone1;

    /**
    * 镶嵌的宝石2
    */
    private Integer stone2;

    /**
    * 镶嵌的宝石3
    */
    private Integer stone3;

    /**
    * 随机效果1
    */
    private Integer random1;
    /**
     * 当前耐久
     */
    private Integer nowDurability;
    /**
     * 最大耐久
     */
    private Integer maxDurability;
}