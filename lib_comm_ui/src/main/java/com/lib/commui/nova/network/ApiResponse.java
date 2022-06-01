package com.lib.commui.nova.network;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * API响应使用的公共类
 * 用于区分此次请求是成功、还是失败，偏向于底层
 * @param <T>响应对象的类型
 */
public class ApiResponse<T> {

    private static final String TAG = ApiResponse.class.getSimpleName();

    public static<T> ApiErrorResponse<T> create(Throwable error){
        Log.d(TAG,"create error " + error);
        String errorMsg = error.getMessage() == null ? "请求异常，请稍后再试" : error.getMessage();
        if(error instanceof UnknownHostException){
            errorMsg = "网络连接不可用，请检查网络连接";
        }
        return new ApiErrorResponse<>(errorMsg);
    }

    public static<T> ApiResponse<T> create(Response<T> response){
        Log.d(TAG,"create response " + response);
        if (response.isSuccessful()){
            //响应成功
            T body = response.body();
            if (body == null || response.code() == 204){
                //响应实体空
                return new ApiEmptyResponse<T>();
            }else {
                return new ApiSuccessResponse<T>(body);
            }
        }else {
            //响应失败
            ResponseBody body = response.errorBody();
            String errorMsg;
            if (body != null){
                try {
                    String msg = body.string();
                    errorMsg = msg;
                } catch (IOException e) {
                    e.printStackTrace();
                    errorMsg = e.getMessage();
                }
            }else {
                errorMsg = response.message();
            }
            return new ApiErrorResponse<>(TextUtils.isEmpty(errorMsg) ? "请求异常，请稍后再试" : errorMsg);
        }
    }

    public static class ApiSuccessResponse<T> extends ApiResponse<T> {
        private T body;
        public ApiSuccessResponse(T body){
//            Log.d(TAG,"ApiSuccessResponse body " + body);
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public static class ApiErrorResponse<T> extends ApiResponse<T> {
        private String errorMessage;
        public ApiErrorResponse(String errorMessage){
            Log.d(TAG,"ApiErrorResponse errorMessage " + errorMessage);
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    /**
     *为HTTP204响应单独的类，这样我们就可以使ApiSuccessResponse的主体非空。
     * @param <T>
     */
    public static class ApiEmptyResponse<T> extends ApiResponse<T> {
        public ApiEmptyResponse(){
            Log.d(TAG,"ApiEmptyResponse ");
        }
    }
}
