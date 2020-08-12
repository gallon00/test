package com.cxxy.xuecheng.controller;

import com.alibaba.fastjson.JSONObject;
import com.cxxy.xuecheng.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by liangtong.
 */
@Controller
public class FreemarkerController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/model_a")
    public ModelAndView model_a(){

        ModelAndView modelAndView = new ModelAndView();
        //设置模型（数据）
        Map<String,String> map = new HashMap<>();
        map.put("name","jack");
        map.put("age","18");
        modelAndView.addObject("user" , map);


        //设置视图（页面）
        modelAndView.setViewName("model_a");

        return modelAndView;
    }

    @GetMapping("/model_b")
    public String model_b(Model model){

        //设置模型（数据）
        Map<String,String> map = new HashMap<>();
        map.put("name","jack");
        map.put("age","19");
        model.addAttribute("user",map);

        //设置视图（页面）
        return "model_b";
    }

    @GetMapping("/model_c")
    public String model_c(Map map){

        //设置模型（数据）
        Map<String,String> userMap = new HashMap<>();
        userMap.put("name","jack");
        userMap.put("age","20");
        map.put("user",userMap);

        //设置视图（页面）
        return "model_c";
    }

    @GetMapping("/list")
    public String list(Model model){

        //设置模型（数据）
        List<User> list = new ArrayList();
        list.add(new User("jack","1234",18));
        list.add(new User("rose","5678",21));

        model.addAttribute("allUser", list);

        //设置视图（页面）
        return "list";
    }

    @GetMapping("/map")
    public String map(Model model){

        //设置模型（数据）
        Map<String,User> map = new HashMap<>();
        map.put("user1", new User("jack","1234",18));
        map.put("user2", new User("rose","5678",21));

        model.addAttribute("allUser", map);

        //设置视图（页面）
        return "map";
    }

    @GetMapping("/if")
    public String _if(Model model){

        //设置模型（数据）
        model.addAttribute("token", 1234);
        model.addAttribute("token2", "1234");

        //设置视图（页面）
        return "if";
    }

    @GetMapping("/method")
    public String method(Model model){

        //设置模型（数据）
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("123");
        model.addAttribute("list", list);

        model.addAttribute("birthday", new Date());

        model.addAttribute("money",12345678);

        model.addAttribute("text", JSONObject.toJSONString(new User("jack","1234",18)));

        //设置视图（页面）
        return "method";
    }



}
