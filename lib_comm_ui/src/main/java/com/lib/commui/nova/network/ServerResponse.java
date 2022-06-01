package com.lib.commui.nova.network;


import com.lib.commui.nova.network.po.IServerResponse;

/**
 * 项目服务器自定义的响应实体
 * @param <BO> 业务实体数据
 */
public class ServerResponse<BO> implements IServerResponse<BO> {
    /**
     * 接口响应状态
     */
    private int code;
    /**
     * 响应状态信息描述
     */
    private String msg;
    /**
     * 业务实体数据
     */
    private BO data;

    @Override
    public boolean isSuccess() {
        return code == 200;
    }

    @Override
    public boolean isSpecialCode() {
        return false;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public BO getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


}
