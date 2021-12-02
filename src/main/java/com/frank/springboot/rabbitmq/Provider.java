package com.frank.springboot.rabbitmq;
import com.frank.springboot.mqutils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 2021-12-01 17:24:06
 * 生产者
 * 1、与rabbitmq建立连接，获取一个连接对象
 * 2、生产消息，发给队列（连接通过通道传递消息）
 * 3、后续处理（关闭通道连接）
 */
public class Provider {
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        //1、与rabbitmq建立连接，获取一个连接对象
//        ConnectionFactory connectionFactory = new ConnectionFactory();//创建连接mq的连接工厂对象
//        connectionFactory.setHost("127.0.0.1");//设置连接rabbitmq主机
//        connectionFactory.setPort(5672); //设置端口
//        connectionFactory.setVirtualHost("/testhost"); //设置连接哪个虚拟主机
//        connectionFactory.setUsername("test"); //设置访问虚拟主机的帐号密码
//        connectionFactory.setPassword("test"); //设置访问虚拟主机的帐号密码
//        Connection connection = connectionFactory.newConnection();//获取连接对象

        //通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();

        //2、生产消息，发给队列（连接通过通道传递消息）
        Channel channel = connection.createChannel();//获取连接中的通道
        //通道和对应的消息队列需要绑定（通道才能知道要把消息发给哪个消息队列）
        //参数1：队列名，若不存在则新建
        //参数2：定义队列特性是否需要持久化
        //参数3：是否是独占队列，true表示当前队列只能这个链接用
        //参数4：是否在消费完后自动删除队列
        //参数5：其他额外参数
        channel.queueDeclare("hellomq",false,false,false,null);

        //发布消息到队列中
        //参数1：交换机名，参数2：队列名，参数3：消息传递额外设置，参数4：消息具体内容（要求将字符串转化为字节数组byte[]）
        channel.basicPublish("","hellomq",null,"hello rabbitmq!".getBytes());

        //3、后续处理
//        channel.close(); //关闭通道
//        connection.close();//关闭链接
        RabbitMQUtils.closeChannelandConnection(channel,connection);
    }
}
