package com.alin.common.core.subjectobserver.service;

import java.util.Map;

/**
 * 被观察者接口
 * @author maql
 */
public interface ISubject {
	
	void register(IObserver myObserver);

    void remove(IObserver myObserver);

    void send(Map<String, Object> map);
}
