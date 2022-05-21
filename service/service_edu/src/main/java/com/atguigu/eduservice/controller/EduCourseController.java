package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    //添加课程
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo)
    {
        String courseId=courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",courseId);
    }
    //查询课程用id
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId)
    {
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    //修改课程信息
    @PutMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo)
    {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id)
    {
        CoursePublishVo coursePublishVo=courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布。修改课程状态
    @PutMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id)
    {
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //获取课程列表
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQueryQuery) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(current, limit);

        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();


        // 多条件组合查询
        // mybatis学过 动态sql
        String name = courseQueryQuery.getName();
        String status = courseQueryQuery.getStatus();

        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        courseService.page(pageCourse, wrapper);

        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords(); //数据list集合
        return R.ok().data("total", total).data("list", records);
    }
    //删除课程
    @DeleteMapping("removeById/{courseId}")
    public R deleteCourse(@PathVariable String courseId)
    {
        courseService.removeCourseById(courseId);
        return R.ok();
    }

}

