package com.zzh.filter;

import com.zzh.config.SecurityConfig;
import com.zzh.domain.UserDetail;
import com.zzh.utils.JwtUtil;
import com.zzh.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String userID;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userID = claims.getSubject();
            System.out.println(userID);
        } catch (Exception e) {
            throw new RuntimeException("token非法");
        }

        String redisKey = "login" + userID;
        UserDetail userDetail = redisCache.getCacheObject(redisKey);
        System.out.println(userDetail);
        if(Objects.isNull(userDetail))
        {
            throw new RuntimeException("用户未登录");
        }
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request,response);

    }
}
