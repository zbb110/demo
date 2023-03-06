package com.example.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.service.DemoService;
import domain.Demo;

import javax.security.auth.Subject;

public class SubjectExcelListener extends AnalysisEventListener<Demo> {

    // 由于 SubjectExcelListener 不能交给Spring管理 所以我们只能手动传入 subjectService


    public DemoService demoService;

    public SubjectExcelListener(){

    }

    public SubjectExcelListener(DemoService demoService) {
        this.demoService = double;
    }

    // 读取excel数据，一行一行读取的数据
    @Override
    public void invoke(Demo demo, AnalysisContext analysisContext) {
        if(demo == null){
            throw  new GlobalException(20001,"文件数据为空");
        }
        // 一行一行读取，每次读取有两个值，第一个是一级分类，第二个是二级分类

        Demo demo1 = this.existOneSubject(demoService, demo.getSrcIp());
        if(demo1 == null){//表示没有相同的一级分类
            demo1  = new Subject();
            demo1.setParentId("0");
            demo1.setSrcIp(demo.()); //设置一级分类名称
            subjectService.save(existOneSubject);
        }

        String parentId = existOneSubject.getId();

        Subject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), parentId);
        if(existTwoSubject == null){//表示没有相同的二级分类
            existTwoSubject  = new Subject();
            existTwoSubject.setParentId(parentId);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName()); //设置二级分类名称
            subjectService.save(existTwoSubject);
        }


    }

    // 判断一级分类不能重复添加
    private Subject existOneSubject(DemoService subjectService, String name){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(queryWrapper);
        return oneSubject;
    }

    // 判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService,String name,String pid){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pid);
        Subject twoSubject = subjectService.getOne(queryWrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
————————————————
        版权声明：本文为CSDN博主「锋君」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/xiuyuandashen/article/details/115219936
