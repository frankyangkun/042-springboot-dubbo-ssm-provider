package com.frank.springboot.rabbitmq_springboot;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 2021-12-17 16:12:37
 * 消费者
 * SpringBoot整合RabbitMQ
 * 第三种模型：fanout（发布/订阅，或广播）
 */
@Component
public class FanoutConsumer {

    //消费者1
    //由于每个消费者都有个临时队列，都要绑定交换机，所以不需要直接声明队列了，而是用bindings属性将队列（临时队列）和交换机绑定
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列，如果写成@Queue("xxx")就是指定生成的队列名
                    exchange = @Exchange(value = "logs2",type = "fanout"))//绑定的交换机，指定交换机名为logs
    })
    public void getMsg1(String message){
        System.out.println("message1= " + message);
    }
    
    //消费者2
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "logs2",type = "fanout"))
    })
    public void getMsg2(String message){
        System.out.println("message2= " + message);
    }
}
