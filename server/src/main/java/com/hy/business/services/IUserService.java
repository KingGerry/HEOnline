package com.hy.business.services;

import com.hy.business.backentity.BackUser;
import com.hy.business.entity.User;

public interface IUserService {
    public BackUser Login(User user);
}
