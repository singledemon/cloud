package com.alin.common.core.sender;

import java.util.Map;

/**
 * @author maql
 * mq消息发送类接口
 */
public interface RabbitMqSender {

    /**
     * 向mq发送消息
     *
     * @param msg
     * @param
     * @throws Throwable
     */
    void sendMsg(String msg, String key) throws Throwable;


    /**
     * 向mq发送消息  包括Exchange、queue字段。
     * @param exchange
     * @param msg
     * @param key
     * @throws Throwable
     */
    void sendExchangeMsg(String exchange, String key, Map<String, Object> msg) throws Throwable;


    /**
     * 向mq发送消息并确认
     * @param exchange
     * @param msg
     * @param key
     * @throws Throwable
     */
    Object sendMsgAndReceiver(String exchange, String key,Map<String, Object> msg) throws Throwable;




}
