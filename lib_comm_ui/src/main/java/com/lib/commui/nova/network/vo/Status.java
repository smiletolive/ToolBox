package com.lib.commui.nova.network.vo;

public enum Status {
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 失败/错误
     */
    ERROR,
    /**
     * 加载中
     */
    LOADING,
    /**
     * 刷新成功
     */
    SUCCESS_REFRESH,
    /**
     * 刷新成功，但没数据
     */
    SUCCESS_REFRESH_NO_DATA,
    /**
     * 加载更多成功
     */
    SUCCESS_LOAD_MORE,
    /**
     * 没有更多
     */
    NO_LOAD_MORE
}
