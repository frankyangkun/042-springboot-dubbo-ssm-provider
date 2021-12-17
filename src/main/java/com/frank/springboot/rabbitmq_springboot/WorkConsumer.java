package com.frank.springboot.rabbitmq_springboot;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 2021-12-17 10:39:47
 * 第二种模型：工作队列
 * 消费者
 */
@Component
public class WorkConsumer {

    //消费者1，@RabbitListener注解不仅可以加在类上，也可加在方法上
    //加在方法上代表这个方法会处理当前RabbitListener监听的队列的回调，就不再需要@RabbitHandler了
    @RabbitListener(queuesToDeclare = @Queue("work"))//work模型消费者还是需要绑定队列，所以这里要声明一个队列
    public void getMsg(String message){//message就是消息队列取出来的消息
        System.out.println("message1= " + message);
    }

    //消费者2
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void getMsg2(String message){
        System.out.println("messsage2= " + message);
    }
}
