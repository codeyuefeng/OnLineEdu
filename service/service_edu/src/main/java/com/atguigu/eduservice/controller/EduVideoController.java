package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    //根据小节id查询
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video", eduVideo);
    }

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //需要修改同时删除视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id得到视频id
        EduVideo eduVideo = videoService.getById(id);
        //远程调用
        String sourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(sourceId))
        {

            R result = vodClient.removeAlyVideo(sourceId);
            if(result.getCode()==20001)
                throw new GuliException(20001, "删除视频失败，熔断器.....");
        }
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PutMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

