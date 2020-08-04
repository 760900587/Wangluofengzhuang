package com.example.httplibrary.Cacallback;

import com.example.httplibrary.Bean.Repones;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public abstract class HttpCallback<T> extends BaseCallback<T> {
    Repones repones;


    @Override
    public T onConvert(String result) {
        T t=null;
        Repones repones = new Gson().fromJson(result, Repones.class);
        JsonElement data = repones.getData();
        int errorCode = repones.getErrorCode();
        String errorMsg = repones.getErrorMsg();
        switch (errorCode){
            case -1:
                onError(errorMsg,errorCode);
                break;
            default:
                if (isCodeSuccess()){
                    convert(data);
                }
                break;
        }
        return t;
    }



    @Override
    public boolean isCodeSuccess() {
        if (repones!=null){
                return repones.getErrorCode()==0;
        }
        return false;
    }
}
