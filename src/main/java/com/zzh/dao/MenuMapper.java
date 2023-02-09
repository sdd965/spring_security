package com.zzh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper <Menu>{

    List<String> selectPermsById(@Param("id") Long id);
}
