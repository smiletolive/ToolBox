package com.lib.commui.nova.network.vo;

/**
 * 封装数据及其状态公开网络状态
 */
public class Resource<T> {
    private Status status;
    private T data;
    private String message;

    private Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public static<T> Resource<T> success(T data, String message) {
        return new Resource<T>(Status.SUCCESS, data, message);
    }

    public static<T> Resource<T> success(T data) {
        return new Resource<T>(Status.SUCCESS, data, null);
    }

    /**
     * 刷新列表成功
     */
    public static<T> Resource<T> successRefresh(T data) {
        return new Resource<T>(Status.SUCCESS_REFRESH, data, null);
    }

    /**
     * 刷新列表成功，但没数据
     */
    public static<T> Resource<T> successRefreshNoData(String msg) {
        return new Resource<T>(Status.SUCCESS_REFRESH_NO_DATA, null, msg);
    }

    /**
     * 列表加载更多成功
     */
    public static<T> Resource<T> successLoadMore(T data) {
        return new Resource<T>(Status.SUCCESS_LOAD_MORE, data, null);
    }

    /**
     * 没有更多
     */
    public static<T> Resource<T> noLoadMore() {
        return new Resource<T>(Status.NO_LOAD_MORE, null, null);
    }

    public static<T> Resource<T> error(String message, T data) {
        return new Resource<T>(Status.ERROR, data, message);
    }

    public static<T> Resource<T> error(String message) {
        return Resource.error(message, null);
    }


    public static<T> Resource<T> loading(T data) {
        return new Resource<T>(Status.LOADING, data, null);
    }

}
