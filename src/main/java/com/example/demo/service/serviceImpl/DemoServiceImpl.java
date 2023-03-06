package com.example.demo.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.DemoMapper;
import com.example.demo.service.DemoService;
import domain.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {
    @Autowired
    private DemoMapper demoMapper;
    @Override


    public int saveList(List<Demo> list) {
        demoMapper.saveList(list);
        return 0;
    }
}
