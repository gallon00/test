package com.czxy.xuecheng.rabbitmq.demo01_hello;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by liangtong.
 */
public class Consumer01 {
    private static String queue = "helloworld";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue,true,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("receive: " + message);
            }
        };

        channel.basicConsume(queue,true,consumer);
    }
}
