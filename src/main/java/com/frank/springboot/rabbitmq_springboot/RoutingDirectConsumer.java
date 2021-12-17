package com.frank.springboot.rabbitmq_springboot;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 2021-12-17 17:18:36
 * 消费者
 * SpringBoot整合RabbitMQ
 * 第四种模型：Routing路由之订阅模型-Direct（直连）
 */
@Component
public class RoutingDirectConsumer {

    //消费者1，接收info，error，warn三类信息
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "routingDirect",type = "direct"),
                    key = {"info","error","warn"}
            )
    })
    public void getMsg1(String message){
        System.out.println("message1: " + message);
    }

    //消费者2，只接收info信息
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "routingDirect",type = "direct"),
                    key = {"info"}
            )
    })
    public void getMsg2(String message){
        System.out.println("message2: " + message);
    }
}
