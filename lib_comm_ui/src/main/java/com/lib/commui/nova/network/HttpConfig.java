package com.lib.commui.nova.network;

import android.util.Log;

import com.lib.commui.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Http请求配置
 * Created on 2016/11/11.
 */
public class HttpConfig {

    private static final String TAG = "HttpConfig";

    //读超时长，单位：秒
    public static final int READ_TIME_OUT = 10;
    //连接时长，单位：秒
    public static final int CONNECT_TIME_OUT = 10;

    private static HttpConfig ourInstance = new HttpConfig();

    private OkHttpClient okHttpClient;

    public static HttpConfig getInstance() {
        return ourInstance;
    }

    private HttpConfig() {

    }

    /**
     * 初始化Http
     * @param requestInterceptor 拦截请求
     */
    public void initHttp(Interceptor requestInterceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //网络log拦截打印
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, message);
                }
            }
        });
        //拦截级别
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //添加log拦截器
        builder.addInterceptor(loggingInterceptor);

        //设置超时
        builder.readTimeout(READ_TIME_OUT,  TimeUnit.SECONDS);
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        //连接失败重试
        builder.retryOnConnectionFailure(true);

//        builder.addInterceptor(new GetCacheInterceptors());//获取缓存拦截器

//        CryptoRequestInterceptors cryptoRequestInterceptors = new CryptoRequestInterceptors();

        //添加请求拦截器
        if (requestInterceptor != null){
            builder.addInterceptor(requestInterceptor);
        }

        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
}
