package com.czxy.xuecheng.search.repository;

import com.czxy.xuecheng.domain.search.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by liangtong.
 */
public interface CourseRepository extends ElasticsearchRepository<Course, String> {
}
