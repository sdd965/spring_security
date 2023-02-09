package com.zzh.service;

import com.zzh.domain.ResponseResult;
import com.zzh.domain.User;

public interface loginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
