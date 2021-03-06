package com.czxy.xuecheng.domain.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created by liangtong.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "xc_course", type = "CoursePub" )
public class Course {
    @Id
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String name;

    @Field( type = FieldType.Keyword)
    private String grade;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String description;

    private String timestamp;//时间戳

    @Field( type = FieldType.Text,index = false)
    private String users;

    @Field( type = FieldType.Keyword)
    private String charge;

    @Field( type = FieldType.Keyword)
    private String valid;

    @Field( type = FieldType.Keyword,index = false)
    private String pic;//图片

    @Field( type = FieldType.Keyword,index = false)
    private String qq;

    @Field( type = FieldType.Float)
    private Double price;
    @Field( type = FieldType.Float)
    private Double price_old;

    @Field( type = FieldType.Keyword)
    private String mt;

    @Field( type = FieldType.Keyword)
    private String st;

    @Field( type = FieldType.Keyword)
    private String status;

    @Field( type = FieldType.Keyword)
    private String studymodel;
    @Field( type = FieldType.Keyword)
    private String teachmode;

    @Field( type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String teachplan;//课程计划

    private String expires;

    private String pub_time;//课程发布时间

    private String start_time;

    private String end_time;

}
