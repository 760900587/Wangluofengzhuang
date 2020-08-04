package com.example.httplibrary.Cacallback;

import android.text.TextUtils;

import com.example.httplibrary.HttpGloBalConfig;
import com.example.httplibrary.disposable.RequestMangerIml;
import com.example.httplibrary.exceptiopn.ApiException;
import com.example.httplibrary.exceptiopn.ExceptionEngine;
import com.example.httplibrary.utils.ThreadUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver implements Observer {
        String tab;
    @Override
    public void onSubscribe(Disposable d) {
        if (!TextUtils.isEmpty(tab)){
            RequestMangerIml.getInstance().add(tab,d);
        }
    }

    @Override
    public void onNext(Object o) {
            if (!TextUtils.isEmpty(tab)){
                RequestMangerIml.getInstance().remove(tab);
            }
    }

    @Override
    public void onError(Throwable e) {
            if (e instanceof ApiException){
                ApiException apiException = (ApiException) e;
                onError(apiException.getMessage(),apiException.getCode());
            }else {
                onError("网络解析错误", ExceptionEngine.UN_KNOWN_ERROR);
            }
            if (!TextUtils.isEmpty(tab)){
                RequestMangerIml.getInstance().remove(tab);
            }
    }
    //回调错误信息
    protected abstract void onError(String message, int code);


    @Override
    public void onComplete() {
        if (!RequestMangerIml.getInstance().isDispose(tab)){
            RequestMangerIml.getInstance().cancle(tab);
        }
    }
    public void canclend(){
        if (!ThreadUtils.isMainThrad()){
            HttpGloBalConfig.getInstance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    cancle();
                }
            });
        }
    }

    protected abstract void cancle();

    public void setTab(String tab) {
        this.tab = tab;
    }
}
