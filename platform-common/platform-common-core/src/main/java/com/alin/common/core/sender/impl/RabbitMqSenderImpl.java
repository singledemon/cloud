package com.alin.common.core.sender.impl;

import com.alin.common.core.sender.RabbitMqSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author maql
 */
@Component
public class RabbitMqSenderImpl implements RabbitMqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(String msg, String key) {
        byte[] bytes = msg.getBytes();
        amqpTemplate.convertAndSend("IotDeviceNetwork", key, bytes);
    }

    @Override
    public void sendExchangeMsg(String exchange, String key, Map<String, Object> msg) {
        rabbitTemplate.convertAndSend(exchange, key, msg);
    }


    @Override
    public Object sendMsgAndReceiver(String exchange, String key, Map<String, Object> msg) {
        Object iver = amqpTemplate.convertSendAndReceive(exchange, key, msg);
        return  iver;
    }
}
