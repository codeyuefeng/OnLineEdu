package com.atguigu.eduservice.client;


import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="service-vod",fallback=VodFileDegradeFeignClient.class)//调用服务名称
@Component
public interface VodClient {
    //定义调用的方法路径
    //根据视频id,删除阿里云视频
    //@PathVariable注解一定要指定参数名称，否则出错
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    //删除多个视频
    //参数多个视频id
    @DeleteMapping("/eduvod/video/delete-bach")
    public R deleteBach(@RequestParam("videoIdList") List<String> videoList);
}
