package com.lib.commui.nova.network;

import android.util.Log;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LiveData请求适配器
 * @param <R> 响应类型
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {

    private static final String TAG = LiveDataCallAdapter.class.getSimpleName();

    private Type responseType;

    public LiveDataCallAdapter(Type responseType){
        Log.d(TAG,"LiveDataCallAdapter " + responseType);
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        Log.d(TAG,"responseType "  + responseType);
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
        Log.d(TAG,"adapt "  + call);
        return new LiveData<ApiResponse<R>>() {
            //原子布尔
            private AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                Log.d(TAG,"adapt LiveData onActive ");
                if (started.compareAndSet(false, true)){
                    Log.d(TAG,"adapt LiveData onActive compareAndSet true");
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            Log.d(TAG,"adapt LiveData onActive call onResponse call = " + call + " response = " + response);
                            postValue(ApiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable throwable) {
                            Log.d(TAG,"adapt LiveData onActive call onFailure call = " + call + " throwable = " + throwable);
                            postValue((ApiResponse<R>) ApiResponse.create(throwable));
                        }
                    });
                }
            }
        };
    }
}
