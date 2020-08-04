package com.example.httplibrary.Cacallback;

import com.example.httplibrary.exceptiopn.ExceptionEngine;
import com.google.gson.JsonElement;

public abstract  class BaseCallback<T> extends BaseObserver{
        boolean callSucces=true;
    @Override
    public void onNext(Object t) {
        super.onNext(t);
        T parse =parse((String)t);
        if (callSucces&&isCodeSuccess()){
            onSucces(parse);
        }
    }
    public abstract T onConvert(String result);
    public  T parse(String t){
        T data =null;
        try {
            data=onConvert(t);
            callSucces=true;
        } catch (Exception e) {
            e.printStackTrace();
            callSucces=false;
            onError("解析网络错误", ExceptionEngine.UN_KNOWN_ERROR);
        }
        return data;
    }
    //将我们需要的数据解析出来
    public abstract T convert(JsonElement date);
    //返回获取的泛型数据
    public abstract void onSucces(T t);
    //数据返回状态成功
    public abstract boolean isCodeSuccess();


}
