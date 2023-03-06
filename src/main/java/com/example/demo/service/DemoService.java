package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import domain.Demo;

import java.util.List;

public interface DemoService extends IService<Demo> {
    public int saveList(List<Demo> list);
}

