package com.own.midterm.util.Glide;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCache  {
    private LruCache<String, Bitmap> ImageCache;
    public void initImageCache(){//初始化
        int maxMenroy=(int)Runtime.getRuntime().maxMemory();//得到当前可使用内存
        int cacheSize = maxMenroy/8;//当前缓存的八分之一作为使用
        ImageCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }//为ImageCache设置大小
        };
    }
    public void put(String md5,Bitmap bitmap){
        ImageCache.put(md5,bitmap);
    }
    public Bitmap get(String md5){
        return ImageCache.get(md5);
    }
}
