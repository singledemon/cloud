package com.alin.common.core.subjectobserver;

import com.alin.common.core.subjectobserver.annotation.SubjectObserver;
import com.alin.common.core.subjectobserver.service.IObserver;
import com.alin.common.core.subjectobserver.service.ISubject;
import com.alin.common.core.util.SpringContextHolder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * 观察者注册服务 自启动类
 *
 * @Author maql
 * @create 2019/6/18 11:49
 */


@Component
@Log
public class SubjectObserverStartHandler implements CommandLineRunner {

    /*@Value("${switch.on}")
    private Boolean switchOn;*/

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        ApplicationContext context = SpringContextHolder.getApplicationContext();
        log.info("Initing SubjectObserver on starting ! [SubjectObserver]");
        this.registSubjectObserverHandler(context);
    }

    /**
     * 注册数据处理事件
     *
     * @param context
     */
    private void registSubjectObserverHandler(ApplicationContext context) {
        //获取所有观察者列表bean
        Map<String, Object> observerMaps = context.getBeansWithAnnotation(SubjectObserver.class);

        if (observerMaps == null || observerMaps.size() < 1) {
            return;
        }
        Collection<Object> observers = observerMaps.values();
        for (Object obj : observers) {
            if (obj instanceof IObserver) {
                IObserver hd = (IObserver) obj;
                SubjectObserver subjectObserver = hd.getClass().getAnnotation(SubjectObserver.class);

                log.info("Regist Subject to Observer ---" + subjectObserver.subject());
                ISubject tmpSubject = (ISubject) SpringContextHolder.getBean(subjectObserver.subject());// context.getBean(subjectObserver.subject());//

                tmpSubject.register(hd);
            }
        }

    }
}
