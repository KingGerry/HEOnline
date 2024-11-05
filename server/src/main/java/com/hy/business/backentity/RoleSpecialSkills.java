package com.hy.business.backentity;

import lombok.Data;

@Data
public class RoleSpecialSkills {
    public RoleSpecialSkills(Integer _id,Integer _lv){
        this.id = _id;
        this.lv = _lv;
    }
    //技能ID
    Integer id;
    //技能等级
    Integer lv;
}









