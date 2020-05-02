package com.own.midterm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Httputil {
    private static Httputil instance;

    public static synchronized Httputil getInstance() {
        if (instance == null) {
            instance = new Httputil();
        }
        return instance;
    }

    private int corePoolSize;
    private int maxPoolSize;
    private long keepAliveTime = 30;//最长排队时间
    private TimeUnit timeUnit = TimeUnit.MINUTES;
    private ThreadPoolExecutor executor; //线程池对象

    private Httputil() {
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        maxPoolSize = 30;
        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, timeUnit, new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    public void execute(final String request, final Callback callBack) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                start(request, callBack);
            }
        });
    }

    void start(String request, Callback callback) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(request);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setConnectTimeout(8000);//连接最大时间
            httpURLConnection.setReadTimeout(8000);//读取最大时间
            httpURLConnection.connect();
            InputStream in = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));//写入reader
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            if (callback != null) {
                callback.onResponse(response.toString());
            }
            reader.close();
        } catch (IOException e) {
            callback.onFailed(e);
        } finally {
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }
    }
}