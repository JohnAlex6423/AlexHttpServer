package com.alex.http.server.pool;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class AlexThreadFactory implements ThreadFactory {

    private int counter;
    private final String name;
    private final List<String> stats;

    public AlexThreadFactory(String name) {
        counter = 1;
        this.name = name;
        stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r,name + "-Thread-"+counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on%s\n",t.getId(), t.getName(), new Date()));
        return t;
    }

    public String getStats(){
        StringBuffer buffer = new StringBuffer();
        Iterator<String> iterator = stats.iterator();
        while (iterator.hasNext()){
            buffer.append(iterator.next());
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
