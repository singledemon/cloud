package cn.com.oumeng.common.log.service;

import cn.com.oumeng.common.core.constant.RabbitMqConstants;
import cn.com.oumeng.common.core.sender.impl.RabbitMqSenderImpl;
import cn.com.oumeng.common.core.util.BeanUtils;
import cn.com.oumeng.common.log.model.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 异步调用日志服务
 * 
 * @author ruoyi
 */
@Service
public class AsyncLogService {

    @Autowired
    RabbitMqSenderImpl rabbitMqSender;


    @Async
    public void  saveOperationLog(OperationLog log){

        Map<String,Object> map = BeanUtils.objectToMap(log,null);
       // rabbitMqSender.sendExchangeMsg(RabbitMqConstants.OPERATIONLOG_EXCHANGE, RabbitMqConstants.OPERATIONLOG_KEY, map);

        rabbitMqSender.sendExchangeMsg(RabbitMqConstants.OPERATIONLOG_EXCHANGE,RabbitMqConstants.OPERATIONLOG_KEY,map);

    }

}
