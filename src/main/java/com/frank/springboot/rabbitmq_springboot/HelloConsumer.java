package com.frank.springboot.rabbitmq_springboot;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

/**
 * 2021-12-15 11:07:49
 * 消费者
 * SpringBoot整合RabbitMQ（第一种模型：直连）
 * 这里demo中provider和consumer都在Provider项目中，没有分到不同项目中。
 */
@Component //首先要被工厂扫描到
@RabbitListener(queuesToDeclare = @Queue(value = "hello",durable = "true",autoDelete = "false")) //代表是RabbitMQ消费者，并指定监听哪个队列的消息，queuesToDeclare没有队列就声明一个
//默认是持久化，非独占，不自动删除的队列，@Queue参数2，3都可省略
public class HelloConsumer {

    @RabbitHandler //代表从消息队列取出消息的回调方法，上面拿到队列中的消息后，在这里处理消息
    public void getMSG(String message){//具体处理消息的方法
        System.out.println("message= " + message);
    }
}
