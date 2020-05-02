package com.own.midterm.util.Glide;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

public class BitmapRequest {
    private Context context;
    private String url;
    private String urlMd5;
    private SoftReference<ImageView> imageViewSoftReference;
    private int id;//占位符
    private int compressRate = 100;
    private float ratio = -1f;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest loading(int id){
        this.id = id;
        return this;
    }

    public void into(ImageView imageView){
        this.imageViewSoftReference = new SoftReference<>(imageView);
        imageView.setTag(urlMd5);
//        RequestManager.getInstance().addBitmapRequest(this);//已弃用的方法
        ThreadManager.getExecutorService().addBitmapRequest(this);
    }

    public BitmapRequest setRatio (float ratio){
        this.ratio = ratio;
        return this;
    }

    public BitmapRequest load(String Url){
        StringBuilder builder = new StringBuilder(Url);
        if(Url.indexOf(":") == 4)
            builder.insert(4,"s");
        Url = builder.toString();
        this.url = Url;
        this.urlMd5 = MD5Util.getMD5(Url);
        return this;
    }

    public BitmapRequest setCompress(int rate){
        this.compressRate = rate;
        return this;
    }

    public int getCropRate(){
        return this.compressRate;
    }

    public float getRatio (){
        return this.ratio;
    }


    public String getUrl() {
        return url;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public ImageView getImageViewSoftReference() {
        return imageViewSoftReference.get();
    }

    public int getId() {
        return id;
    }

    public Context getContext(){
        return this.context;
    }
}
