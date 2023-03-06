package com.example.demo.controller;

import com.example.demo.dao.DemoMapper;
import domain.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoMapper demoDAO;

    @PostMapping("/tableImport")
    @Transactional
    public Boolean tableImport(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        //读取上传文件数据转换成
        List<Demo> resExcelDTOList = EasyExcelUtil.readExcel(multipartFile.getInputStream(), ExportExcelDTO.class, new EasyExcelUtil.ExcelListener<>());
        log.info(resExcelDTOList.toString());
        return true;
    }


}
