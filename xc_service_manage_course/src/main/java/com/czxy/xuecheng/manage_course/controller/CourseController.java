package com.czxy.xuecheng.manage_course.controller;

import com.czxy.xuecheng.api.course.CourseControllerApi;
import com.czxy.xuecheng.common.model.response.QueryResponseResult;
import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.common.web.BaseController;
import com.czxy.xuecheng.domain.course.CoursePic;
import com.czxy.xuecheng.domain.course.Teachplan;
import com.czxy.xuecheng.domain.course.ext.CourseInfo;
import com.czxy.xuecheng.domain.course.ext.CourseView;
import com.czxy.xuecheng.domain.course.ext.TeachplanNode;
import com.czxy.xuecheng.domain.course.request.CourseListRequest;
import com.czxy.xuecheng.domain.course.response.CoursePicResult;
import com.czxy.xuecheng.domain.course.response.CoursePublishResult;
import com.czxy.xuecheng.manage_course.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController implements CourseControllerApi {

    @Resource
    private CourseService courseService;

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        // 暂时固定值，应该为登录公司
        String company_id = "1";

        return courseService.findCourseList(company_id, page,size,courseListRequest);
    }

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic")String pic) {
        return courseService.addCoursePic(courseId,pic);
    }

    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePicResult findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }

    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView courseview(@PathVariable("courseId") String courseId) {
        return courseService.getCoruseView(courseId);
    }

    @Override
    @PostMapping("/preview/{courseId}")
    public CoursePublishResult preview(@PathVariable("courseId") String courseId) {
        return courseService.preview(courseId);
    }

    @Override
    @PostMapping("/publish/{courseId}")
    public CoursePublishResult publish(@PathVariable("courseId") String courseId) {
        return courseService.publish(courseId);
    }
}
