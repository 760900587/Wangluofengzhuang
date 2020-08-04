package com.example.wangluofengzhuang.app;

import android.app.Application;

import com.example.httplibrary.HttpConstant;
import com.example.httplibrary.HttpGloBalConfig;

public class ShopApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpGloBalConfig.getInstance()
                .setBase_url("http://gank.io/api/")
                .setTimeout(HttpConstant.TIME_OUT)
                .setShowLog(true)
                .setTimeUnit(HttpConstant.TIME_UNIT)
                .initReady(this);
    }
}
