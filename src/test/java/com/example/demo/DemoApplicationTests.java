package com.example.demo;

import com.alibaba.excel.EasyExcel;
import domain.Demo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.listener.DemoDataListener;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        String fileName = "C:\\Users\\15497\\Desktop\\需要拆分的网段.xlsx";
        EasyExcel.read(fileName, Demo.class, new DemoDataListener()).sheet().doRead();
    }

}
