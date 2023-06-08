/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description Spring上下文工具类
 * @see ApplicationContextAware
 * @since 1.0.0
 */
@Component
public class SpringBeanContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        SpringBeanContext.context = context;
    }

    /**
     * 获取上下文
     *
     * @return 上下文对象
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 根据beanName获取bean
     *
     * @param beanName bean名称
     * @return bean对象
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 根据beanName和类型获取bean
     *
     * @param beanName bean名称
     * @param clazz    bean类型
     * @param <T>      bean类型
     * @return bean对象
     */
    public static  <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz bean类型
     * @param <T>   bean类型
     * @return bean对象
     */
    public static  <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
