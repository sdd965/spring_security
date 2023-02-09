package com.zzh.service.impl;

import com.zzh.dao.MenuMapper;
import com.zzh.service.getPremService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class getPremServiceImpl implements getPremService {

    @Autowired
    private MenuMapper mapper;
    @Override
    public List<String> getPrems(Long id) {
        return mapper.selectPermsById(id);
    }
}
