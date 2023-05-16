/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * 此版权个人开发者保留最终归属权与解释权，非版权所有者授权禁止商用与演绎.
 */

package net.totime.mail.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/05
 * @description Orika配置类
 * @since 1.0.0
 */
@Configuration
public class OrikaConfig {
    /**
     * MapperFactory是Orika的核心对象，它是线程安全的，可以在应用中共享
     *
     * @return {@link MapperFactory}
     */
    @Bean
    public MapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    /**
     * MapperFacade接口是Orika的核心接口，提供了对象之间相互转换的方法
     *
     * @return {@link MapperFacade}
     */
    @Bean
    public MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }
}
