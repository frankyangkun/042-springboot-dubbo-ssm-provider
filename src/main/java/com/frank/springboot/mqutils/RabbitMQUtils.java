package com.frank.springboot.mqutils;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 2021-12-02 17:25:09
 * RabbitMQ工具类封装
 */
public class RabbitMQUtils {
    private static ConnectionFactory connectionFactory;
    static{//ConnectionFactory是重量级资源，使用静态代码块，在类加载时执行，只执行一次
        connectionFactory = new ConnectionFactory();//创建连接mq的连接工厂对象
        //下面基本信息基本不会变，也在类加载时执行一次即可
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/testhost");
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
    }
    /**
     * 获取连接对象
     * @return
     */
    public static Connection getConnection(){

        try{//为了不给后面调用制造麻烦，这里就不抛出异常，去处理
//            ConnectionFactory connectionFactory = new ConnectionFactory();//创建连接mq的连接工厂对象
//            connectionFactory.setHost("127.0.0.1");
//            connectionFactory.setPort(5672);
//            connectionFactory.setVirtualHost("/testhost");
//            connectionFactory.setUsername("test");
//            connectionFactory.setPassword("test");
            return connectionFactory.newConnection();//直接返回连接对象
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭链接和通道
     * @param channel
     * @param conn
     */
    public static void closeChannelandConnection(Channel channel, Connection conn){
        try{
            if(channel != null){
                channel.close(); //关闭通道
            }
            if(conn != null){
                conn.close();//关闭链接
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
