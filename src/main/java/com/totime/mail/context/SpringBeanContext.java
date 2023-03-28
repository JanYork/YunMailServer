package com.totime.mail.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author JanYork
 * @date 2023/3/8 9:33
 * @description SpringBean上下文
 */
@Component
public class SpringBeanContext implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.context = context;
    }

    /**
     * 获取上下文
     *
     * @return 上下文对象
     */
    public ApplicationContext getContext() {
        return context;
    }

    /**
     * 根据beanName获取bean
     *
     * @param beanName bean名称
     * @return bean对象
     */
    public Object getBean(String beanName) {
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
    public <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz bean类型
     * @param <T>   bean类型
     * @return bean对象
     */
    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}