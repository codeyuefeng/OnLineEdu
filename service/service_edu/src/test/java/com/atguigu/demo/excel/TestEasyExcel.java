package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {


    private static List<DemoDataExcel> getData()
    {
        List<DemoDataExcel> list=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            DemoDataExcel data=new DemoDataExcel();
            data.setSno(i);
            data.setSname("tom "+i);
            list.add(data);

        }
        return list;
    }
    @Test
    public void testWrite() {
        //实现写操作
        //设置写入文件路径和文件
        String filename="E:\\write.xlsx";

        EasyExcel.write(filename, DemoDataExcel.class).sheet("学生列表").doWrite(getData());
    }
    @Test
    public  void testRead()
    {
        //实现读操作

        String filename="E:\\write.xlsx";

        EasyExcel.read(filename,DemoDataExcel.class,new ExcelListener()).sheet().doRead();
    }
}
