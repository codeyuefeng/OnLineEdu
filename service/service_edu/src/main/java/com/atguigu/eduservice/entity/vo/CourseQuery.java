package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程名称,模糊查询")
    private String name;
    @ApiModelProperty(value = "课程状态,模糊查询")
    private String status;
}
