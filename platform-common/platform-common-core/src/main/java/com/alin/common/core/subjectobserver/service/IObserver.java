package com.alin.common.core.subjectobserver.service;

import java.util.Map;

/**
 * 观察者接口
 * @author maql
 */
public interface IObserver {
	
	void receive(Map<String, Object> map);
}
