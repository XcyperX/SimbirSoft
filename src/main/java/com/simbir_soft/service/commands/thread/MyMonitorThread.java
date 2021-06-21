package com.simbir_soft.service.commands.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyMonitorThread {
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public void addTask(Runnable runnable) throws InterruptedException {
        queue.put(runnable);
        System.out.println("Поток добавлен!");
        System.out.println("Задач в очереди: " + queue.size());
    }

    public MyMonitorThread() throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, queue);
        pool.prestartAllCoreThreads();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Объект создан!");
    }
}
