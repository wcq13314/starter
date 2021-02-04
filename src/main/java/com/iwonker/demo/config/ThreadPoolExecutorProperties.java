package com.iwonker.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *  配置类属性
 * </p>
 *
 * @Author wcq
 * @Company 旺客元
 * @Date 2021-02-04 16:20
 */

@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolExecutorProperties {

    public Integer getScenes() {
        return scenes;
    }

    public void setScenes(Integer scenes) {
        this.scenes = scenes;
    }

    private Integer scenes = 1;

}
