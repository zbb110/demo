package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import domain.Demo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

    public int saveList(List<Demo> list);
}
