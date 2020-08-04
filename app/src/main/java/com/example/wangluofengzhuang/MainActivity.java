package com.example.wangluofengzhuang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.httplibrary.Cacallback.HttpCallback;
import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.LogUtils;
import com.example.wangluofengzhuang.app.FuliBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        new HttpClient.Builder()
                .setJsonbody("",true)
                .setApiUrl("data/%E7%A6%8F%E5%88%A9/20/3")
                .get()
                .build()
                .request(new HttpCallback<FuliBean>() {

                    @Override
                    protected void onError(String message, int code) {
                        Log.i("TAG", "onError: "+message+code);
                    }

                    @Override
                    protected void cancle() {

                    }

                    @Override
                    public FuliBean convert(JsonElement date) {
                        return new Gson().fromJson(new Gson().toJson(date),FuliBean.class);
                    }

                    @Override
                    public void onSucces(FuliBean fuliBean) {
                        Log.i("TAG", "convert: "+fuliBean.toString());
                    }
                });

    }
}
