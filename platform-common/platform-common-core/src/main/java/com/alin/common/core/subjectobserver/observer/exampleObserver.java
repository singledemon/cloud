package com.alin.common.core.subjectobserver.observer;

import com.alin.common.core.subjectobserver.ObserverConstant;
import com.alin.common.core.subjectobserver.annotation.SubjectObserver;
import com.alin.common.core.subjectobserver.service.IObserver;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 观察者示例实体类
 * @author maql
 */
@Component
@SubjectObserver(subject = ObserverConstant.Example_SUBJECT)
@Log
public class exampleObserver implements IObserver {

/*
    @Autowired
    private RabbitMqSenderImpl rabbitMqSender;*/

    @Override
    public void receive(Map<String, Object> map) {
      log.info("to do something...");
    }

}
