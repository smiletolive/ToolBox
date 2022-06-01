package com.lib.commui.nova.network.po;

/**
 * 封装服务端响应数据
 * 项目响应实体需要实现此接口
 */
public interface IServerResponse<BO> {
    /**
     * 请求是否成功
     * @return true 成功；false 不成功，有错误
     */
    boolean isSuccess();

    /**
     * 是否为特殊Code
     * @return true 是；false 否
     */
    boolean isSpecialCode();

    /**
     * 获取响应code
     * @return 响应code
     */
    int getCode();

    /**
     * 获取信息
     * 使用场景：1）未响应到业务数据时，此接口获取失败信息
     * @return 信息
     */
    String getMessage();

    /**
     * 获取业务实体对象
     * @return 业务实体对象
     */
    BO getData();
}
