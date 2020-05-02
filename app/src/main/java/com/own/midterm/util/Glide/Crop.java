package com.own.midterm.util.Glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Crop {
    /**
     * 由质量百分比压缩图片
     * @param context
     * @param bitmap
     * @param quality
     * @param md5
     * @return
     */
    public static Bitmap compressByQuality(Context context,Bitmap bitmap, int quality, String md5){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
        String path = context.getCacheDir().getPath()+"/"+md5;
        try{
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(baos.toByteArray());//覆盖
            fos.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            return BitmapFactory.decodeByteArray(baos.toByteArray(),0,baos.toByteArray().length);
        }
    }

    /**
     *由y比x比例裁剪图片
     * @param context
     * @param bitmap
     * @param yByXRatio
     * @param md5
     * @return
     */
    public static Bitmap cropCenterByPercentage(Context context,Bitmap bitmap, float yByXRatio,String md5){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String path = context.getCacheDir().getPath()+"/"+md5;
        int startX,endX,startY,endY;
        float ratio = ((float)bitmap.getHeight())/bitmap.getWidth();
        if(ratio > yByXRatio){//y过长
            startX = 0;
            endX = bitmap.getWidth();
            startY = (bitmap.getHeight() - (int)(bitmap.getWidth() * yByXRatio))/2;
            endY = bitmap.getHeight() - startY * 2;
        } else {
            startY = 0;
            endY = bitmap.getHeight();
            startX = (bitmap.getWidth() - (int)(bitmap.getHeight() / yByXRatio))/2;
            endX = bitmap.getWidth() - startX * 2;
        }
        bitmap = Bitmap.createBitmap(bitmap, startX, startY, endX, endY);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        try{
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(baos.toByteArray());//覆盖
            fos.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            return bitmap;
        }
    }

}
