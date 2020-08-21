package com.alin.common.core.constant;

/**
 * rabbitmq 常量类
 * 使用时，不同模块命名约定好 规则
 * example_channl_A
 * example_queue_A
 * example_exchange_A
 */
public class RabbitMqConstants {

    /**
     *
     */
    public static final String CENTRE_QUEUE ="centre_queue";

    //======================系统操作日志模块===============
    /**
     *  系统操作日志模块
     * OperationLogFacotry
     * containerFactory
     */
    public static final String OPERATIONLOG_FACTORY ="operationLogFactory";

    /**
     * 系统操作日志模块
     * 通道
     *   channl
     */
    public  static final String OPERATIONLOG_CHANNL="crm.log.channl";

    /**
     * 系统操作日志模块
     * key
     */
    public  static final String OPERATIONLOG_KEY="crm.log.code";
    /**
     * 系统操作日志模块
     * 队列
     *  @Queue
     */
    public static final String OPERATIONLOG_QUEUE="crm.log.queue";
    /**
     * 系统操作日志模块
     * 路由 交换机
     * @Exchange
     */
    public static final String OPERATIONLOG_EXCHANGE="crm.log.exchange";

    //=====================================================================================================

}
