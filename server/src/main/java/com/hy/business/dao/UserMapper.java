package com.hy.business.dao;

import com.hy.business.backentity.BackUser;
import com.hy.business.entity.RoleBag;
import com.hy.business.entity.RoleTasks;
import com.hy.business.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public BackUser Login(User u);

    public List<RoleBag> GetBages(@Param("roleId") int roleId, @Param("btype") int btype);

    public List<RoleTasks> GetTasks(@Param("roleId") int roleId);
}
