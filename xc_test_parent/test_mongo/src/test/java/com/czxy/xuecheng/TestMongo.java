package com.czxy.xuecheng;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangtong.
 */
public class TestMongo {

    @Test
    public void testConnection() {
        //创建mongodb 客户端
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        System.out.println(mongoClient);
    }

    @Test
    public void testConnection2() {
        //创建mongodb 客户端
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        System.out.println(mongoClient);
    }

    @Test
    public void testFind(){
        //采用连接字符串
         MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
         MongoClient mongoClient = new MongoClient(connectionString);
        // 连接数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 连接collection
        MongoCollection<Document> collection = database.getCollection("student");
        //查询第一个文档
        Document myDoc = collection.find().first();
        //得到文件内容 json串
        String json = myDoc.toJson();
        System.out.println(json);
    }

    @Test
    public void testCollection(){
        // 采用连接字符串
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        MongoClient mongoClient = new MongoClient(connectionString);
        // 连接数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 创建集合
        database.createCollection("teacher");
    }

    @Test
    public void testDocument(){
        // 采用连接字符串
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        MongoClient mongoClient = new MongoClient(connectionString);
        // 连接数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");

        // 插入文档
        Document document = new Document();
        document.append("name","C老师");
        document.append("age",18);
        document.append("marry","new...");
        collection.insertOne(document);

    }

    @Test
    public void testAllDocument(){
        // 采用连接字符串
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        MongoClient mongoClient = new MongoClient(connectionString);
        // 连接数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");

        // 插入文档
        Document doc1 = new Document();
        doc1.append("name","L老师");
        doc1.append("age",21);
        doc1.append("marry","new new ...");

        Document doc2 = new Document();
        doc2.append("name","Y老师");
        doc2.append("age",20);
        doc2.append("marry","Done ...");


        List<Document> documentList = new ArrayList<>();
        documentList.add(doc1);
        documentList.add(doc2);

        collection.insertMany(documentList);

    }

    @Test
    public void testSelectAll() {
        // 采用连接字符串
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        MongoClient mongoClient = new MongoClient(connectionString);
        // 连接数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");

        //查询所有
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            Document document = mongoCursor.next();
            String name = document.get("name",String.class);
            Integer age = document.get("age",Integer.class);
            String marry = document.get("marry",String.class);

            System.out.println(name + "_" + age + "_" + marry);
        }


    }

    @Test
    public void testUpdate() {
        // 采用连接字符串
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        MongoClient mongoClient = new MongoClient(connectionString);
        // 连接数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");

        // 更新
        collection.updateOne(Filters.eq("age",20), new Document("$set", new Document("name","YY老师")));

    }

    @Test
    public void testDelete() {
        // 采用连接字符串
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        MongoClient mongoClient = new MongoClient(connectionString);
        // 连接数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        // 获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");

        // 删除
        collection.deleteOne(Filters.eq("age",20));

    }

}
