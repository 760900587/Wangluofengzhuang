package com.example.httplibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

public class HttpGloBalConfig {

    //uri网址
    private String Base_url;
    private long timeout;
    private TimeUnit timeUnit;
    //请求公共参数
    private Map<String,Object>baseparms;
    //请求头信息
    private Map<String,Object>basehread;
    //公有的拦截器
    private ArrayList<Interceptor>interceptors;
    //日志开关
    private boolean isShowLog;
    private Context context;
    private Handler handler;
    public HttpGloBalConfig() {
    }
    //静态内部单利
    private static final   class HttpGloBalConfHander{
    private static    HttpGloBalConfig instance =new HttpGloBalConfig();

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public static HttpGloBalConfig getInstance() {
        return HttpGloBalConfHander.instance;
    }

    public String getBase_url() {
        return Base_url;
    }

    public HttpGloBalConfig setBase_url(String base_url) {
        Base_url = base_url;
        return HttpGloBalConfig.getInstance();
    }

    public long getTimeout() {
        return timeout;
    }

    public HttpGloBalConfig setTimeout(long timeout) {
        this.timeout = timeout;
        return HttpGloBalConfig.getInstance();
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public HttpGloBalConfig setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return HttpGloBalConfig.getInstance();
    }

    public Map<String, Object> getBaseparms() {
        return baseparms;
    }

    public HttpGloBalConfig setBaseparms(Map<String, Object> baseparms) {
        this.baseparms = baseparms;
        return HttpGloBalConfig.getInstance();
    }

    public Map<String, Object> getBasehread() {
        return basehread;
    }

    public HttpGloBalConfig setBasehread(Map<String, Object> basehread) {
        this.basehread = basehread;
        return HttpGloBalConfig.getInstance();
    }

    public ArrayList<Interceptor> getInterceptors() {
        return interceptors;
    }

    public HttpGloBalConfig setInterceptors(ArrayList<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return HttpGloBalConfig.getInstance();
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public HttpGloBalConfig setShowLog(boolean showLog) {
        isShowLog = showLog;
        return HttpGloBalConfig.getInstance();
    }

    public HttpGloBalConfig(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public HttpGloBalConfig initReady(Context context){
        this.context=context.getApplicationContext();
        handler=new Handler(Looper.getMainLooper());
        return HttpGloBalConfig.getInstance();
    }
}
