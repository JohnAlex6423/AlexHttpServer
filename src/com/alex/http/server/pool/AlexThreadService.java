package com.alex.http.server.pool;

import java.util.concurrent.*;

public class AlexThreadService implements AlexThreadPool {

    private ExecutorService executorService;

    @Override
    public void init(int corePoolSize,
                     int maximumPoolSize,
                     long keepAliveTime,
                     TimeUnit unit,
                     int capacity) {
        ThreadFactory threadFactory = new AlexThreadFactory("com/alex/http/server/pool");
        this.executorService = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,
                keepAliveTime, unit,new LinkedBlockingQueue<>(capacity),threadFactory,new RejectedPloy());
    }

    @Override
    public void destroy() {
        this.executorService.shutdown();
    }

    @Override
    public  ExecutorService getThreadPool() {
        return this.executorService;
    }
}
