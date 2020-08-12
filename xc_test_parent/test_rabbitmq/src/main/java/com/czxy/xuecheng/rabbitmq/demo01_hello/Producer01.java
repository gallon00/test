package com.czxy.xuecheng.rabbitmq.demo01_hello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by liangtong.
 */
public class Producer01 {
    private static String queue = "helloworld";
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();

            channel = connection.createChannel();

            channel.queueDeclare(queue, true, false, false, null);

            String message = "";
            channel.basicPublish("",queue,null, message.getBytes());;

            System.out.println("send msg: " + message);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }


    }
}
