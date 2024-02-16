package com.jun.platform.config;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * App 加载的配置接口，定义App的加载，升级和卸载方式.
 * <p>
 *     默认是采用Default
 * </p>
 */
@Component
public class AppLoadConfig {

    private final ApplicationContext context;

    public AppLoadConfig(ApplicationContext registry) {
        this.context = registry;
        System.out.println(context);
    }
}
