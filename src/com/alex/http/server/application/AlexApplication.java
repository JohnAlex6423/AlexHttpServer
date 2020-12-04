package com.alex.http.server.application;

import com.alex.http.server.handel.ScoketHandel;
import com.alex.http.server.pool.AlexThreadService;

import java.util.concurrent.TimeUnit;

public class AlexApplication {

    public static AlexThreadService alexThreadPool = new AlexThreadService();

    public static final String staticPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/static/";

    public static void run(String[] args) {
        alexThreadPool.init(1024,1024,0, TimeUnit.MINUTES,1024);
        start();
    }

    public static void start() {
        alexThreadPool.getThreadPool().execute(new ScoketHandel());
    }
}
