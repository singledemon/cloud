package com.alin.common.core.subjectobserver.annotation;

import java.lang.annotation.*;

/**
 * 观察者注解
 * @author maql
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SubjectObserver {

    /**
     * 观察对象
     *
     * @return
     */
    String subject() default "";

    /**
     *  是否启动
     *
     * @return
     */
    boolean on() default true;

    /**
     *  参数配置－是否启动
     *
     * @return
     */
    String sysparam_on() default "";

}
