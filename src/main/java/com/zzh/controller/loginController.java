package com.zzh.controller;

import com.zzh.domain.ResponseResult;
import com.zzh.domain.User;
import com.zzh.service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {

    @Autowired
    private loginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user)
    {
        return loginService.login(user);
    }

    @GetMapping("/out")
    public ResponseResult logout()
    {
        return loginService.logout();
    }
}
