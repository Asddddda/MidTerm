package com.own.midterm.contract;

import android.app.Activity;

public interface MainContract {

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
