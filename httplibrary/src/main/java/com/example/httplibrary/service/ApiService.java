package com.example.httplibrary.service;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

    public interface ApiService {

        //get请求
        @GET
        Observable<JsonElement> get(@Url String url, @QueryMap Map<String, Object> parms, @HeaderMap Map<String, Object> hreads);

        //[post 请求
        @POST
        java.util.Observable post(@Url String url,RequestBody body,Map<String,Object>hreads);

        //删除
        @DELETE
        Observable<JsonElement> delete(@Url String url, @QueryMap Map<String, Object> parms, @HeaderMap Map<String, Object> hreads);
        @POST
        @FormUrlEncoded
        Observable<JsonElement>postjson(@Url String url, @FieldMap Map<String ,Object>params,@HeaderMap Map<String,Object>heards);

        //put请求
        @PUT
        @FormUrlEncoded
        Observable<JsonElement> put(@Url String url, @FieldMap Map<String, Object> parms, @HeaderMap Map<String, Object> hreads);

        //post上传文件
        @Multipart
        @POST
        Observable<JsonElement> Muitipart(@Url String url, @PartMap Map<String, Object> parms, List<MultipartBody.Part> list);
        //下载文件
        @Streaming
        Observable<JsonElement>Streaming(@Url String url,@QueryMap Map<String,Object>parms,@HeaderMap Map<String,Object>hreads);
    }