package com.frank.springboot.rabbitmq_routing_topic;

import com.frank.springboot.mqutils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 2021-12-14 11:01:58
 * 第五种模型：Routing路由之Topic（动态路由）
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        //获取连接对象和通道对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机及其类型
        channel.exchangeDeclare("topics","topic");

        //发布消息
        String routekey = "user.save";
        channel.basicPublish("topics", routekey,null,("这里是topic动态路由模型，routekey：[ " + routekey + " ]").getBytes());

        //释放资源
        RabbitMQUtils.closeChannelandConnection(channel,connection);
    }
}
