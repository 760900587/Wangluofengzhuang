package com.example.httplibrary.utils;

import android.os.Looper;


public class ThreadUtils {
    //判断是否是主线程
    public static boolean isMainThrad(){
        return Looper.getMainLooper().getThread().getId()==Thread.currentThread().getId();
    }
}
