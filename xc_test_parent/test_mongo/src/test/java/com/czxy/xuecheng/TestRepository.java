package com.czxy.xuecheng;

import com.czxy.xuecheng.dao.TeacherRepository;
import com.czxy.xuecheng.domain.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liangtong.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TestRepository {

    @Resource
    private TeacherRepository teacherRepository;

    @Test
    public void testSort(){
        //排序查询
        // 默认：升序
//        List<Teacher> list = teacherRepository.findAll(Sort.by("age"));
        // 降序
        List<Teacher> list = teacherRepository.findAll(Sort.by(Sort.Order.desc("age")));

        //遍历数据 (JDK8特性)
        list.forEach(System.out::println);

        list.forEach( teacher -> {
            System.out.println(teacher);
        });
    }

    @Test
    public void testFindByUsername(){
        Teacher teacher = teacherRepository.findByUsername("张三");
        System.out.println(teacher);
    }

    @Test
    public void testFindByUsernameLike(){
        List<Teacher> list = teacherRepository.findByUsernameLike("o");
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }

        list.forEach(System.out::println);
    }

    @Test
    public void testFindByUsernameLikeAndAge(){
        List<Teacher> list = teacherRepository.findByUsernameLikeAndAge("o", 21);
        list.forEach(System.out::println);
    }


    @Test
    public void testFindByUsernameLikeAndAgePage(){
        int pageNum = 0;
        int pageSize = 2;
        Page<Teacher> page = teacherRepository.findByUsernameLikeAndAge("张", 21, PageRequest.of(pageNum,pageSize));
        System.out.println(page.getTotalElements());
        page.getContent().forEach(System.out::println);
    }

    @Test
    public void testExample() {
        Teacher teacher = new Teacher();
        //teacher.setAge(21);
        teacher.setUsername("o");

        /* 匹配器
            contains()  模糊匹配
            startsWith() 以开头模糊匹配
            endsWith() 以结尾模糊匹配
         */
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains() )
                ;
        // 实例，Example.of( 实体对象 , 匹配器)
        Example<Teacher> example = Example.of(teacher,matcher);

        List<Teacher> list = teacherRepository.findAll(example);
        list.forEach(System.out::println);
    }



}
