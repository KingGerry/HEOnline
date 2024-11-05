package com.hy.business.services.impl;

import com.hy.business.dao.RoleBagMapper;
import com.hy.business.services.IRoleBagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IRoleBagServiceImpl implements IRoleBagService {

    @Resource
    private RoleBagMapper roleBagMapper;

}
