package com.frank.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 2021-12-15 16:49:21
 * 生产者
 * SpringBoot整合RabbitMQ
 * 这里demo中provider和consumer都在Provider项目中，没有分到不同项目中。
 */
@SpringBootTest(classes = Application.class)//指定测试类（应该就是入口类）
@RunWith(SpringRunner.class)
//SpringRunner.class，指定Spring工厂，该注解的意义在于Test测试类要使用注入的类，比如@Autowired注入的类，
//有了@RunWith(SpringRunner.class)，这些类才能实例化到spring容器中，自动注入才能生效，不然直接一个NullPointerExecption
public class TestRabbitMQProvider {
    //只要与mq服务器建立连接后，会自动给我们实例化一个template，所以这里注入rabbitTemplate对象即可
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //第一种模型hello world，生产者只需要往队列发消息，消费者监听消费即可
    @Test
    public void testHello(){
        rabbitTemplate.convertAndSend("hello","hello world");//发送到名为hello的队列中，可直接发对象过去，之前纯java api是用byte发消息
    }

    //第二种模型：工作队列，默认多条消息平均分给多个消费者消费
    @Test
    public void testWork(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work","work模型" + i);
        }
    }

    //第三种模型：fanout（发布/订阅，或广播）
    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("logs2","","Fanout模型发送的消息");//fanout模型中路由key不需要
    }

    //第四种模型：Routing之订阅模型-Direct（直连）
    @Test
    public void testRoutingDirect(){
        rabbitTemplate.convertAndSend("routingDirect","info","Routing之订阅模型-Direct（直连）发送的消息");
    }
}
