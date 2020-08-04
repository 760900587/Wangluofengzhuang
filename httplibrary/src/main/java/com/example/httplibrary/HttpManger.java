package com.example.httplibrary;

import com.example.httplibrary.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManger {
    private static volatile HttpManger httpManger;

    private HttpManger() {
    }

    //单利模式 返回对象
    public static HttpManger getHttpManger() {
        if (httpManger == null) {
            synchronized (HttpManger.class) {
                if (httpManger == null) {
                    httpManger = new HttpManger();
                }
            }
        }
        return httpManger;
    }

    //定义一个 封装Retrofit的 方法  参数是URl 请求时间 和接收时间
    public Retrofit getretrofit(String BaseUrl, long Tiemout, TimeUnit timeUnit) {
        return new Retrofit.Builder()
                .client(getBaseOkhttpclient(Tiemout, timeUnit))
                .baseUrl(BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //封装OkHttp
    private OkHttpClient getBaseOkhttpclient(long tiemout, TimeUnit timeUnit) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("Okkhttp" + message);
            }
        });
        //创建拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response proceed = chain.proceed(request);

                return proceed;
            }
        };
        Interceptor[] interceptors = {httpLoggingInterceptor, interceptor};
        return getClient(tiemout, timeUnit, interceptors);

    }

    //封装OkHttp;
    private OkHttpClient getClient(long tiemout, TimeUnit timeUnit, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(tiemout, timeUnit);
        builder.writeTimeout(tiemout, timeUnit);
        builder.readTimeout(tiemout, timeUnit);
        for (Interceptor interceptor:interceptors ) {
            builder.addInterceptor(interceptor);
        }
        ArrayList<Interceptor> interceptors1 = HttpGloBalConfig.getInstance().getInterceptors();
        for (Interceptor interceptor:interceptors) {
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }
}
