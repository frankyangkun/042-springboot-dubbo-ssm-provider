package com.frank.springboot.rabbitmq_routing_direct;

import com.frank.springboot.mqutils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 2021-12-13 16:44:43
 * 第四种模型：Routing路由之Direct直连
 * ps：不同类型的消息可以被不同类型的消费者消费（广播类型没法定向给消费者发消息）
 * 场景：比如错误日志既需要在控制台打印，也需要持久化到硬盘。其他类型的日志就只需要在控制台打印。
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();//获取连接对象
        Channel channel = connection.createChannel();//获取连接通道对象

        String exchangeName = "logs_direct";//交换机名

        channel.exchangeDeclare(exchangeName,"direct");//通过通道声明交换机，参数1：交换机名；参数2：direct路由模式

        //发送消息
        String routingkey = "info";
        channel.basicPublish(exchangeName,routingkey,null,("这是direct模型发布的基于routingkey[ "+routingkey+" ]发送的消息。").getBytes());

        //关闭资源
        RabbitMQUtils.closeChannelandConnection(channel,connection);
    }
}
