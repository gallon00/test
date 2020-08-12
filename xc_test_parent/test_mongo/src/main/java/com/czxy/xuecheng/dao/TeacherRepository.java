package com.czxy.xuecheng.dao;

import com.czxy.xuecheng.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by liangtong.
 */
public interface TeacherRepository extends MongoRepository<Teacher,String> {
    //通过 username 查询
    public Teacher findByUsername(String username);

    //通过 username 模糊查询
    public List<Teacher> findByUsernameLike(String username);

    // 查询 名称中含o，且年龄21岁
    public List<Teacher> findByUsernameLikeAndAge(String username, Integer age);

    // 查询 名称中含o，且年龄21岁 + 分页
    public Page<Teacher> findByUsernameLikeAndAge(String username, Integer age, Pageable pageable);
}
