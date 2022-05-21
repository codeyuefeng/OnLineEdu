package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService subjectService)
    {
        this.subjectService=subjectService;
    }

    //读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null)
        {
            System.out.println("文件数据为空");
            throw new GuliException(20001,"文件数据为空");
        }
        else
        {
            //判断一级分类是否重复
            EduSubject existOneSubject=this.existOneSubject(subjectService, subjectData.getOneSubjectName());
            //没有相同一级分类，进行添加
            if(existOneSubject==null)
            {
                existOneSubject=new EduSubject();
                existOneSubject.setParentId("0");
                existOneSubject.setTitle(subjectData.getOneSubjectName());
                subjectService.save(existOneSubject);

            }
            String pid=existOneSubject.getId();
            //判断二级分类是否重复
            EduSubject existTwoSubject=this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
            //没有相同二级分类，进行添加
            if(existTwoSubject==null)
            {
                existTwoSubject=new EduSubject();
                existTwoSubject.setParentId(pid);
                existTwoSubject.setTitle(subjectData.getTwoSubjectName());
                subjectService.save(existTwoSubject);

            }
        }
    }
    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name)
    {
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid)
    {
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void invokeHead(Map headMap, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
