package com.zzh.service.impl;

import com.zzh.domain.ResponseResult;
import com.zzh.domain.User;
import com.zzh.domain.UserDetail;
import com.zzh.service.loginService;
import com.zzh.utils.JwtUtil;
import com.zzh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements loginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(Objects.isNull(authenticate))
        {
            throw new RuntimeException("Authentication认证失败");
        }
        else {
            UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
            String userId = userDetail.getUser().getId().toString();
            String jwt = JwtUtil.createJWT(userId);
            Map<String,String> map = new HashMap<>();
            map.put("token",jwt);
            //先tm去学redis
            redisCache.setCacheObject("login"+userId,userDetail);
            return new ResponseResult<>(200,"登陆成功", map);
        }
    }

    @Override
    public ResponseResult logout() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(usernamePasswordAuthenticationToken))
        {
            throw new RuntimeException("当前没有用户登陆");
        }
        UserDetail userDetail = (UserDetail) usernamePasswordAuthenticationToken.getPrincipal();
        String id = userDetail.getUser().getId().toString();
        redisCache.deleteObject("login"+id);
        return new ResponseResult(200,"登出成功");
    }
}
