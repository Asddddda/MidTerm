package com.own.midterm.util.Glide;

import android.content.Context;

public class MyGlide {

    public static BitmapRequest with (Context context){
        return new BitmapRequest(context);
    }
}
