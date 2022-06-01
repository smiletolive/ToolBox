package com.lib.commui.nova.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 响应实体转换
 */

public class SuperGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final String TAG = SuperGsonConverterFactory.class.getSimpleName();

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    SuperGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
//        XLog.d(TAG,"convert response = " + response);
        //解决Retrofit中GSON解析同字段不同类型的json数据
        //当响应失败时，data字段是空数组会导致解析异常，通过如下方式可以解决
        ServerResponse<Void> serverResponse = gson.fromJson(response, ServerResponse.class);
        if (serverResponse.isSuccess()){
            //正确响应
            return adapter.fromJson(response);
        }else if (serverResponse.isSpecialCode()){
            return adapter.fromJson(response);
        }else {
            return (T) serverResponse;
        }
    }

}
