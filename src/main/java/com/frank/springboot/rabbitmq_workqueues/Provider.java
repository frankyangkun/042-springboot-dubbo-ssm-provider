package com.frank.springboot.rabbitmq_workqueues;

import com.frank.springboot.mqutils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 2021-12-06 10:38:53
 * 第二种模型：工作队列
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();//获取连接对象
        Channel channel = connection.createChannel();//创建通道对象
        channel.queueDeclare("work",true,false,false,null);//通过通道声明队列

        //该模型由于生产消息的速度远大于消费的速度，可能会源源不断的往队列中发布消息，所以不能一次只发一条，要加循环
        //生产消息
        for (int i = 0; i < 20; i++) {
            channel.basicPublish("","work",null, (i + "hello work queue.").getBytes());
        }

        //关闭资源
        RabbitMQUtils.closeChannelandConnection(channel,connection);
    }
}
