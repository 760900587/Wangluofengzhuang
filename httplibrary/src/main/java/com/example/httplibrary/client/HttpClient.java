package com.example.httplibrary.client;

import android.text.TextUtils;


import com.example.httplibrary.Cacallback.BaseCallback;

import com.example.httplibrary.HttpConstant;
import com.example.httplibrary.HttpGloBalConfig;

import com.example.httplibrary.HttpManger;
import com.example.httplibrary.service.ApiService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;


public class HttpClient {
    //请求方式
    Metod method;
    //请求参数
    Map<String,Object>paramer;
    //q请求头
    Map<String,Object>headres;
    //Rxjava绑定生命周期
    LifecycleProvider lifecycleProvider;
    //绑定Activity的生命周期
    ActivityEvent activityEvent;
    //fragment 的生命周期
    FragmentEvent fragmentEvent;
    String baseUrl;
    //拼接url
    String apiUrl;
    //是否是json上传
    boolean   isJson;
    //json字符串
    String Jsonbody;
    //超时时间
    long time;
    //时间定为
    TimeUnit timeUnit;
    //回调接口
    BaseCallback baseCallback;
    //订阅关系
    String tab;


    public HttpClient(Builder builder) {
        this.method =builder.metod;
        this.paramer = builder.paramser;
        this.headres = builder.headres;
        this.lifecycleProvider = builder.lifecycleProvider;
        this.activityEvent = builder.activityEvent;
        this.fragmentEvent = builder.fragmentEvent;
        this.baseUrl = builder.baseUrl;
        this.apiUrl = builder.apiUrl;
        this.isJson = builder.isJson;
        this. Jsonbody =builder.jsonbody;
        this.time = builder.time;
        this.timeUnit = builder.timeUnit;
        this.tab = builder.tag;
    }

    public void request(BaseCallback baseCallback){
        if (baseCallback==null){
            new RuntimeException("no have callback,must do Observer");
        }
        this.baseCallback=baseCallback;
            doRequest();

    }

    private void doRequest() {
        if (TextUtils.isEmpty(tab)){
            tab=System.currentTimeMillis()+"";
        }
        baseCallback.setTab(tab);
        //添加参数信息
        addPramaers();
        //添加头信息
        addHeadrs();
        if (HttpGloBalConfig.getInstance().getBase_url()!=null){
            this.baseUrl=HttpGloBalConfig.getInstance().getBase_url();
        }
        Observable observer=createObservable();
        HttpObserable build = new HttpObserable.Buidler(observer)
                .setActivityEvent(activityEvent)
                .setBaseObserver(baseCallback)
                .setFragmentEvent(fragmentEvent)
                .setLifecycleProvider(lifecycleProvider)
                .build();
        build.observer().subscribe(baseCallback);
    }

    private Observable createObservable() {
        Observable observable =null;
        boolean b = !TextUtils.isEmpty(Jsonbody);
        RequestBody requestBody = null;
        if (b){
            String mediaType = isJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType),Jsonbody);
        }
        if (method==null){
            method=Metod.GET;
        }
        if(HttpGloBalConfig.getInstance().getTimeout()!=0){
            this.time=HttpGloBalConfig.getInstance().getTimeout();
        }
        if(this.time==0){
            this.time= HttpConstant.TIME_OUT;
        }
        if(HttpGloBalConfig.getInstance().getTimeUnit()!=null){
            this.timeUnit=HttpGloBalConfig.getInstance().getTimeUnit();
        }

        if(this.timeUnit==null){
            this.timeUnit=HttpConstant.TIME_UNIT;
        }
        ApiService apiService = HttpManger.getHttpManger().getretrofit(baseUrl, time, timeUnit).create(ApiService.class);
        switch (method){
            case GET:
                apiService.get(apiUrl,paramer,headres);
                break;
            case POST:
                if (isJson){
                    apiService.post(apiUrl,requestBody,headres);
                }else {
                    apiService.postjson(apiUrl,paramer,headres);
                }
                break;
            case DELETE:
                observable = apiService.delete(apiUrl, paramer, headres);
                break;
            case PUT:
                observable = apiService.put(apiUrl, paramer, headres);
                break;
        }
        return observable;
    }

    private void addHeadrs() {
        if (headres == null) {
            headres = new HashMap<>();
        }
        if (HttpGloBalConfig.getInstance().getBasehread() != null) {
            headres.putAll(HttpGloBalConfig.getInstance().getBasehread());
        }
    }

    private void addPramaers() {
            if (paramer==null){
                paramer =new HashMap<>();
            }
            if (HttpGloBalConfig.getInstance().getBaseparms()!=null){
                this.paramer=HttpGloBalConfig.getInstance().getBaseparms();
            }
    }
    public static final class Builder{
        //请求方式
        Metod metod;
        //请求参数
        Map<String, Object> paramser;
        //请求头信息
        Map<String, Object> headres;
        //Rxjava绑定生命周期
        LifecycleProvider lifecycleProvider;
        //绑定Activity具体的生命周的
        ActivityEvent activityEvent;
        //绑定Fragment的具体的生命周期的
        FragmentEvent fragmentEvent;
        String baseUrl;
        //拼接的url
        String apiUrl;
        //是否是json上传表示
        boolean isJson;
        //json字符串
        String jsonbody;
        //超时时间
        long time;
        //时间单位
        TimeUnit timeUnit;
        //订阅的标签
        String tag;

        public Builder get() {
            this.metod = Metod.GET;
            return this;
        }

        public Builder post() {
            this.metod = Metod.POST;
            return this;
        }

        public Builder delete() {
            this.metod = Metod.DELETE;
            return this;
        }

        public Builder put() {
            this.metod = Metod.PUT;
            return this;
        }

        public Builder setMetod(Metod metod) {
            this.metod = metod;
            return this;
        }

        public Builder setParamser(Map<String, Object> paramser) {
            this.paramser = paramser;
            return this;
        }

        public Builder setHeadres(Map<String, Object> headres) {
            this.headres = headres;
            return this;
        }

        public Builder setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }

        public Builder setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public Builder setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;

        }

        public Builder setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public Builder setJson(boolean json) {
            isJson = json;
            return this;
        }

        public Builder setJsonbody(String jsonbody,boolean isJson) {
            this.jsonbody = jsonbody;
            this.isJson=isJson;
            return this;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }
        public HttpClient build(){
            return new HttpClient(this);
        }
    }
}
