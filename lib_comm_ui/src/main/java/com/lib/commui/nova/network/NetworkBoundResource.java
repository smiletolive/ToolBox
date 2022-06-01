package com.lib.commui.nova.network;

import android.util.Log;


import com.lib.commui.nova.AppExecutors;
import com.lib.commui.nova.network.po.IServerResponse;
import com.lib.commui.nova.network.vo.Resource;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

/**
 * 通用数据请求处理类
 * 一边从网络加载数据，一边显示这些数据在磁盘上的副本
 * https://developer.android.google.cn/jetpack/docs/guide#addendum
 * @param <ResultType> Type for the Resource data.即资源数据类型
 * @param <RequestType> Type for the API response.API响应数据类型
 */
public abstract class NetworkBoundResource<ResultType, RequestType extends IServerResponse> {

    private static final String TAG = NetworkBoundResource.class.getSimpleName();

    //合并多个数据源
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<Resource<ResultType>>();

    private AppExecutors appExecutors ;

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors){
        Log.d(TAG,"NetworkBoundResource ");

        this.appExecutors = appExecutors;

        result.setValue(Resource.<ResultType>loading(null));

        final LiveData<ResultType> dbSource = loadFromDB();

        //首先观察数据库
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType data) {
                Log.d(TAG,"NetworkBoundResource dbSource onChanged data = " + data);
                result.removeSource(dbSource);
                if (shouldFetch(data)){
                    //从网络上去数据
                    Log.d(TAG,"NetworkBoundResource dbSource onChanged shouldFetch fetchFromNetwork");
                    fetchFromNetwork(dbSource);
                }else {
                    Log.d(TAG,"NetworkBoundResource dbSource onChanged shouldFetch dbSource");
                    //只返回数据库数据
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            Log.d(TAG,"NetworkBoundResource 2 dbSource onChanged Resource.success newData = " + newData);
                            setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue){
        if (result.getValue() != newValue){
            result.setValue(newValue);
        }
    }

    /**
     * 从网络上取数据
     * @param dbSource
     */
    private void fetchFromNetwork(final LiveData<ResultType> dbSource){
        Log.d(TAG,"fetchFromNetwork dbSource " + dbSource);

        //从网络上取数据
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        //我们重新附加dbSource作为一个新的源，它将迅速地发送它的最新值
        Log.d(TAG,"fetchFromNetwork result.addSource dbSource " + dbSource);
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newData) {
                Log.d(TAG,"fetchFromNetwork dbSource onChanged Resource.loading newData " + newData );
                setValue(Resource.loading(newData));
            }
        });
        Log.d(TAG,"fetchFromNetwork result.addSource apiResponse " + apiResponse);
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(final ApiResponse<RequestType> response) {
                Log.d(TAG,"fetchFromNetwork apiResponseSource onChanged");
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                if (response instanceof ApiResponse.ApiSuccessResponse){
                    Log.d(TAG,"fetchFromNetwork response is ApiSuccessResponse ");
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            ApiResponse.ApiSuccessResponse<RequestType> successResponse = (ApiResponse.ApiSuccessResponse<RequestType>) response;
                            final RequestType data = successResponse.getBody();
                            if (data.isSuccess()){
                                if (data.getData() == null){
                                    //data 部分为null，直接返回成功(比如请求的数据 data部分就是为null)
                                    appExecutors.mainThread().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d(TAG,"fetchFromNetwork apiResponseSource  data == null result.addSource dbSource " + dbSource);

                                            result.addSource(dbSource, new Observer<ResultType>() {
                                                @Override
                                                public void onChanged(ResultType newData) {
                                                    Log.d(TAG,"fetchFromNetwork apiResponseSource  data == null ApiSuccessResponse dbSource onChanged  Resource.success newData " + newData);
                                                    setValue(Resource.success(newData, data.getMessage()));
                                                }
                                            });
                                        }
                                    });
                                }else {
                                    //成功响应到业务数据才进行保存
                                    saveCallResult(data);
                                    appExecutors.mainThread().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            //我们特别要求一个新的LiveData，否则我们将立即得到最后一个缓存值，可能无法使用从网络收到的最新结果进行更新。
                                            LiveData<ResultType> loadFromDBLiveData = loadFromDB();
                                            Log.d(TAG,"fetchFromNetwork apiResponseSource result.addSource loadFromDBLiveData " + loadFromDBLiveData);
                                            result.addSource(loadFromDBLiveData, new Observer<ResultType>() {
                                                @Override
                                                public void onChanged(ResultType newData) {
                                                    Log.d(TAG,"fetchFromNetwork apiResponseSource loadFromDBLiveData onChanged  Resource.success newData " );
                                                    setValue(Resource.success(newData, data.getMessage()));
                                                }
                                            });
                                        }
                                    });
                                }
                            }else {
                                //未响应到业务数据，通知请求失败

                                if (data.getData() != null){
                                    //当错误里面有data数据时，缓存起来，返回给上层使用
                                    saveCallResult(data);
                                }

                                appExecutors.mainThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d(TAG,"fetchFromNetwork apiResponseSource  result.addSource dbSource " + dbSource);

                                        result.addSource(dbSource, new Observer<ResultType>() {
                                            @Override
                                            public void onChanged(ResultType newData) {
                                                Log.d(TAG,"fetchFromNetwork apiResponseSource  ApiSuccessResponse dbSource onChanged  Resource.error newData " + newData);
                                                setValue(Resource.error(data.getMessage(), newData));
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }else if (response instanceof ApiResponse.ApiErrorResponse){
                    Log.d(TAG,"fetchFromNetwork response is ApiErrorResponse ");
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            Log.d(TAG,"fetchFromNetwork apiResponseSource  ApiErrorResponse dbSource onChanged  Resource.error newData " + newData);
                            setValue(Resource.error(((ApiResponse.ApiErrorResponse<RequestType>) response).getErrorMessage(), newData));
                        }
                    });
                }else if (response instanceof ApiResponse.ApiEmptyResponse){
                    Log.d(TAG,"fetchFromNetwork response is ApiEmptyResponse ");
                    appExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            //从磁盘上重新加载我们拥有的
                            result.addSource(loadFromDB(), new Observer<ResultType>() {
                                @Override
                                public void onChanged(ResultType newData) {
                                    Log.d(TAG,"fetchFromNetwork apiResponseSource  ApiEmptyResponse dbSource onChanged  Resource.success newData " + newData);
                                    setValue(Resource.success(newData));
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    /**
     * Called to save the result of the API response into the database
     * API响应数据保存到数据库中
     * @param item API响应数据
     */
    @WorkerThread
    protected abstract void saveCallResult(RequestType item);

    /**
     * Called with the data in the database to decide whether to fetch
     * potentially updated data from the network.
     * 数据库取到数据后，决定是否从网络中获取可能更新的数据
     * @param data 数据
     * @return true 从网络获取数据；false 不从网络获取数据
     */
    @MainThread
    protected abstract boolean shouldFetch(ResultType data);

    /**
     * Called to get the cached data from the database.
     * 从数据库获取缓存数据
     * @return 数据
     */
    @MainThread
    protected abstract LiveData<ResultType> loadFromDB();

    /**
     * Called to create the API call.
     * API调用，从网络获取数据
     * @return
     */
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    /**
     * Called when the fetch fails. The child class may want to reset components like rate limiter.
     * 当获取失败时调用，子类可能需要重置诸如速率限制器之类的组件。
     */
    protected void onFetchFailed(){

    }

    protected RequestType processResponse(ApiResponse.ApiSuccessResponse<RequestType> response){
        return response.getBody();
    }

    public LiveData<Resource<ResultType>> asLiveData(){
        return result;
    }

}
