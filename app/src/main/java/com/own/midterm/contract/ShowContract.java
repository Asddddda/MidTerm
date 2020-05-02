package com.own.midterm.contract;

import android.content.Context;


public interface ShowContract {
    //网络请求contract
    interface M {
        void getInfo(Context context);

        void informP();
    }

    interface V {
        void askP();

        void showAccount();
    }

    interface P<V> {

        //通知M更新数据
        void askM(Context context);

        //让V更新view
        void informV();
    }
}
