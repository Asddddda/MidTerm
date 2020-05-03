package com.own.midterm.contract;

import android.app.Activity;
import android.content.Context;


public interface ShowContract {
    //网络请求contract
    interface M {
        void getInfo(Context context);

        void informP(String info);
    }

    interface V{
        void askP();

        void show(String info);
    }

    interface P{
        //通知M更新数据
        void askM(Activity activity);

        //让V更新view
        void informV(String info);
    }
}
