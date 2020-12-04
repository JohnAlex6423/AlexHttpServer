package com.alex.http.server.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * http服务核心线程池
 * @version 1.0.0
 * @author alex
 */
public interface AlexThreadPool {

    /**
     * 初始化线程池
     * @param corePoolSize 核心线程数，不会被销毁
     * @param maximumPoolSize 最大可存在线程数量
     * @param keepAliveTime 当前空闲程数大于核心线程数，多余的空闲线程等待任务的最长存在时间。
     * @param unit keepAliveTime的时间单位
     * @param capacity 最大线程容量
     * @author alex
     */
    void init(int corePoolSize,
              int maximumPoolSize,
              long keepAliveTime,
              TimeUnit unit,
              int capacity);

    /**
     * 销毁线程池并会导致退出程序
     * @author alex
     */
    void destroy();

    /**
     * 获取当前线程池
     * @return 当前线程池
     * @author alex
     */
    ExecutorService getThreadPool();
}
