package cn.com.oumeng.common.core.config;

import cn.com.oumeng.common.core.constant.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.consumers}")
    private Integer consumers;

    @Value("${spring.rabbitmq.maxConsumers}")
    private Integer maxConsumers;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String url;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean
    public SimpleRabbitListenerContainerFactory operationLogFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        Connection connection = connectionFactory.createConnection();

        //创建一个通道
        Channel channel = connection.createChannel(true);

        try {
            // 为通道声明队列
            channel.queueDeclare(RabbitMqConstants.CENTRE_QUEUE, true, false, false, null);
            channel.queueDeclare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        factory.setConcurrentConsumers(consumers);  //设置线程数
        factory.setMaxConcurrentConsumers(maxConsumers); //最大线程数
        configurer.configure(factory, connectionFactory);
        return factory;
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(url, port);
        cachingConnectionFactory.setUsername(userName);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

}

