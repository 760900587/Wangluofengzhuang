package com.example.httplibrary.disposable;

import android.text.TextUtils;

import com.example.httplibrary.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;

public class RequestMangerIml implements RequestManager {
    private static volatile RequestMangerIml Instance;
    private static Map<String, Disposable> mMap = new HashMap<>();

    public static RequestMangerIml getInstance() {
        if (Instance == null) {
            synchronized (RequestMangerIml.class) {
                if (Instance == null) {
                    Instance = new RequestMangerIml();
                }
            }
        }
        return Instance;
    }

    @Override
    public void add(String tag, Disposable disposable) {
        if (!TextUtils.isEmpty(tag)) {
            mMap.put(tag, disposable);
        }
    }

    @Override
    public void remove(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            if (mMap.isEmpty()) {
                return;
            }
            mMap.remove(tag);
        }
    }

    @Override
    public void cancle(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            if (mMap.isEmpty()) {
                if (mMap.get(tag) != null) {
                    Disposable disposable = mMap.get(tag);
                    if (!disposable.isDisposed()) {
                            disposable.dispose();
                    }
                    mMap.remove(tag);
                }
            }

        }
    }

    @Override
    public void cancleAll() {
            Disposable disposable=null;
            if (!mMap.isEmpty()){
                Set<String> keySet = mMap.keySet();
                for (String key:keySet) {
                    if (mMap.get(key)!=null){
                        disposable=mMap.get(key);
                    }
                    if(disposable!=null&&!disposable.isDisposed()){
                        disposable.dispose();
                    }
                }

            }
            mMap.clear();
    }
    public boolean isDispose(String tag){
        if(!mMap.isEmpty()&&mMap.get(tag)!=null){
            return mMap.get(tag).isDisposed();
        }
        return false;
    }
}
