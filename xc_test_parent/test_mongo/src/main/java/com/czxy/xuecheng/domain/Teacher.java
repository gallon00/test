package com.czxy.xuecheng.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by liangtong.
 */
@Document(collection = "teacher")
@Data
public class Teacher {
    @Id
    private String id;
    private String username;
    private String password;
    private Integer age;
}
