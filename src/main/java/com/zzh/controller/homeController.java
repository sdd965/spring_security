package com.zzh.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class homeController {

    @PreAuthorize("hasAuthority('system:dept:list')")
    @GetMapping("/hello")
    public String holle()
    {
        return "hello";
    }
}
