package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoDataExcel> {

    //一行一行读取数据
    @Override
    public void invoke(DemoDataExcel demoDataExcel, AnalysisContext analysisContext) {
        System.out.println("***"+demoDataExcel);
    }
    //读取表头
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取之后");
    }
}
