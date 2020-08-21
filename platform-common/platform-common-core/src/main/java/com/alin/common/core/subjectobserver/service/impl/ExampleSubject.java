package com.alin.common.core.subjectobserver.service.impl;

import com.alin.common.core.subjectobserver.ObserverConstant;
import com.alin.common.core.subjectobserver.service.IObserver;
import com.alin.common.core.subjectobserver.service.ISubject;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 被观察者实现类例子
 * @author maql
 */
@Component(ObserverConstant.Example_SUBJECT)
public class ExampleSubject implements ISubject {

    private Set<IObserver> set; // 用一个List来维护所有的观察者对象
    ExecutorService executorService = Executors.newSingleThreadExecutor(); // 单个线程池

    public ExampleSubject() {
        set = new HashSet<>();
    }

    @Override
    public void register(IObserver myObserver) {
        if (myObserver == null) {
            return;
        }
        set.add((IObserver) myObserver);
    }

    @Override
    public void remove(IObserver myObserver) {
        set.remove(myObserver);

    }

    @Override
    public void send(Map<String, Object> map) {
        executorService.execute(new Worker(map));
    }

    class Worker implements Runnable {

        private Map<String, Object> map;

        public Worker(Map<String, Object> map) {
            this.map = map;
        }

        @Override
        public void run() {
            for (IObserver observer : set) {
                observer.receive(map);
            }
        }

    }

}
