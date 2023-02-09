package com.zzh.service.impl;

import com.zzh.dao.UserMapper;
import com.zzh.domain.User;
import com.zzh.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
