package com.own.midterm.util.Glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadManager {
    private ExecutorService threadPool;

    private LinkedBlockingQueue<BitmapRequest> requests;

    private MemoryCache memoryCache;

    private Handler mHandler;

    private static ThreadManager threadManager;

    private ThreadManager(){
        this.memoryCache = new MemoryCache();
        this.requests = new LinkedBlockingQueue<>();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.threadPool = Executors.newCachedThreadPool();
        memoryCache.initImageCache();
    }

    //改static方法
    public static ThreadManager getExecutorService (){
        if (threadManager == null){
            synchronized (ThreadManager.class){
                if(threadManager == null){
                    threadManager = new ThreadManager();
                }
            }
        }
        return threadManager;
    }

    public void addBitmapRequest(BitmapRequest request){
        if(!requests.contains(request)){
            requests.add(request);
            reading();//不确定会不会出现没读到的问题
        }
    }

    private void reading() {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (!requests.isEmpty()){
                    try {
                        Log.d("!!!!!!",Thread.currentThread().toString());
                        BitmapRequest bitmapRequest = requests.take();
                        process(bitmapRequest);//交给线程池分配线程
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void process(final BitmapRequest bitmapRequest){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                showLoadingImage(bitmapRequest);
                Bitmap bitmap = findBitmap(bitmapRequest);
                showImage(bitmapRequest,bitmap);
            }
        });
    }

    private void showImage(final BitmapRequest bitmapRequest, final Bitmap bitmap) {
        if(bitmap!=null&&bitmapRequest.getImageViewSoftReference()!=null
                &&bitmapRequest.getImageViewSoftReference().getTag().equals(bitmapRequest.getUrlMd5())) {//防止图片错位
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    bitmapRequest.getImageViewSoftReference().setImageBitmap(bitmap);
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest bitmapRequest) {
        //内存查找，磁盘查找，网络获取
//        return downloadFromInternet(bitmapRequest);//for test
        Bitmap bitmap = getInRam(bitmapRequest.getUrlMd5());
        String path = bitmapRequest.getContext().getCacheDir().getPath()+"/"+bitmapRequest.getUrlMd5();
        if(bitmap != null){
            return bitmap;
        } else if(getInRom(path)!= null){
            bitmap=getInRom(path);
            memoryCache.put(bitmapRequest.getUrlMd5(),bitmap);
            return bitmap;
        } else {
            bitmap = downloadFromInternet(bitmapRequest);
            return bitmap;
        }
    }

    private Bitmap getInRam(String md5) {
        return memoryCache.get(md5);
    }

    private Bitmap getInRom(String path) {
        Bitmap bitmap = null;
        try{
            FileInputStream fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap downloadFromInternet(BitmapRequest bitmapRequest) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try {
            URL link = new URL(bitmapRequest.getUrl());
            connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            //裁剪处理
            if(bitmapRequest.getCropRate() != 100)
                bitmap = Crop.compressByQuality(bitmapRequest.getContext(),bitmap,
                        bitmapRequest.getCropRate(),bitmapRequest.getUrlMd5());
            if(bitmapRequest.getRatio() > 0)
                bitmap = Crop.cropCenterByPercentage(bitmapRequest.getContext(),bitmap,
                        bitmapRequest.getRatio(),bitmapRequest.getUrlMd5());
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        if(bitmap != null){//加入内存
            memoryCache.put(bitmapRequest.getUrlMd5(),bitmap);
        }
        return bitmap;
    }

    private void showLoadingImage(BitmapRequest bitmap) {
        if (bitmap.getId()>0) {//占位符不为空
            final ImageView imageView = bitmap.getImageViewSoftReference();
            final int id = bitmap.getId();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(id);
                }
            });
        }
    }
}

