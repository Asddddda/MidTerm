package com.own.midterm.util.MyJSON;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private ThreadPool(){ }

    public static ExecutorService getExecutorService (){
        if (cachedThreadPool == null){
            synchronized (ThreadPool.class){
                if(cachedThreadPool == null){
                    cachedThreadPool = Executors.newCachedThreadPool();
                }
            }
        }
        return cachedThreadPool;
    }
}
