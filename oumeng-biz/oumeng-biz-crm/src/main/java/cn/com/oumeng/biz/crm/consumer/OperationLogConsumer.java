package cn.com.oumeng.biz.crm.consumer;

import cn.com.oumeng.biz.crm.service.OperationLogService;
import cn.com.oumeng.common.core.constant.RabbitMqConstants;
import cn.com.oumeng.common.core.util.BeanUtils;
import cn.com.oumeng.common.log.model.OperationLog;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 日志记录异步存储 mq消费者
 *
 */
@Component
public class OperationLogConsumer {

    @Resource
    OperationLogService operationLogService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMqConstants.CENTRE_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitMqConstants.OPERATIONLOG_EXCHANGE,
                    ignoreDeclarationExceptions = "true"),
            key = {RabbitMqConstants.OPERATIONLOG_KEY}))
    public void operationLogReceiver(Map<String,Object> map) {
        if(map==null){
            return;
        }
        OperationLog logs = null;
        try {
            logs = (OperationLog) BeanUtils.mapToObject(map, OperationLog.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        operationLogService.save(logs);
    }

}
