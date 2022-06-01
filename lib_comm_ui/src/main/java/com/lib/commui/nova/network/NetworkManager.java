package com.lib.commui.nova.network;


import android.util.Log;

import retrofit2.Retrofit;

public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();

    public static <T> T createService(String baseUrl, Class<T> service){
        Log.d(TAG,"createService baseUrl = " + baseUrl + " service " + service);
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpConfig.getInstance().getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SuperGsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        //创建服务
        return retrofit.create(service);
    }
}
