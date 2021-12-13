package com.frank.springboot.rabbitmq_fanout;

import com.frank.springboot.mqutils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;


/**
 * 2021-12-10 16:28:30
 * 第三种模型：广播（或发布/订阅）
 * 注意这个和work queue的区别，这个是产生的消息需要被多个消费者同时消费，work queue是不能重复消费
 * 广播：凡是绑定这个交换机的所有队列，都能收到这个交换机的消息
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //这里将通道声明为指定交换机而不是队列了 参数1：交换机名；参数2：交换机类型，fanout广播类型
        channel.exchangeDeclare("testExchange","fanout");

        //发送消息，发给交换机而不是队列了
        //参数1：交换机名；参数2：路由key，在广播模型中没有意义；参数3：消息持久化；参数4：消息具体内容（要求将字符串转化为字节数组byte[]）
        channel.basicPublish("testExchange","",null,"fanout type msg2.".getBytes());

        //释放资源
        RabbitMQUtils.closeChannelandConnection(channel,connection);
    }
}
