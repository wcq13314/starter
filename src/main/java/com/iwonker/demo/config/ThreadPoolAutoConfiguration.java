package com.iwonker.demo.config;

/**
 * <p>
 * 线程池配置类
 * </p>
 *
 * @Author wcq
 * @Company 旺客元
 * @Date 2021-02-04 16:11
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * <p>
 * 自动配置类
 * </p>
 *
 * @author wcq
 * @return
 * @Company 旺客元
 * @Date 2021-02-04 16:26:39
 */
@Configuration
// 配置配置属性
@EnableConfigurationProperties(ThreadPoolExecutorProperties.class)
// 只有这个类才会生效
@ConditionalOnClass(ThreadPoolExecutor.class)
public class ThreadPoolAutoConfiguration {

    /**
     * 阻塞队列
     */
    private final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(10);

    /**
     * 拒绝策略
     */
    private final RejectedExecutionHandler reject = new ThreadPoolExecutor.AbortPolicy();

    /**
     * 线程池类型：CPU密集型：1；IO密集型：2
     */
    @Value("${thread.pool.scenes}")
    private Integer scenes;

    /**
     * 核心线程数大小
     */
    private Integer corePoolSize;

    /**
     * 最大线程数大小
     */
    private Integer maximumPoolSize;

    /**
     * 空闲线程存活时长
     */
    private Long keepAliveTime;

    /**
     * 存活时长单位
     */
    private TimeUnit unit;

    // 初始化数据（类是被注入的）
    @PostConstruct
    public void init() {
        // 获取系统CPU核心数
        int cpuCoreNumber = Runtime.getRuntime().availableProcessors();
        this.corePoolSize = cpuCoreNumber;
        this.maximumPoolSize = 25 * cpuCoreNumber;
        this.keepAliveTime = 60 * 3L;
        this.unit = TimeUnit.SECONDS;
    }
    /**
     * N: CPU核心数
     * CPU密集型：corePoolSize = N + 1
     * IO密集型：corePoolSize = 2 * N
     */
    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor() {
        // cpu密集型
        if (scenes == 1) {
            corePoolSize = corePoolSize + 1;
        } else {
            // io密集型
            corePoolSize = 2 * corePoolSize;
        }
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

}
