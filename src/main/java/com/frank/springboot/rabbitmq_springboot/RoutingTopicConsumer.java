package com.frank.springboot.rabbitmq_springboot;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 2021-12-20 15:35:50
 * 消费者
 * SpringBoot整合RabbitMQ
 * 第四种模型：Routing路由之订阅模型-Topic（动态路由）
 */
@Component
public class RoutingTopicConsumer {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic",name = "routingTopics"),
                    key = {"user.save","user.*"}
            )
    })
    public void getMsg1(String message){
        System.out.println("message1: " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic",name = "routingTopics"),
                    key = {"order.#","product.#","user.*"} //匹配多个单词用#
            )
    })
    public void getMsg2(String message){
        System.out.println("message2: " + message);
    }
}
