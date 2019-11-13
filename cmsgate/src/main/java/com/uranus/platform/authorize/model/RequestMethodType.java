package com.uranus.platform.authorize.model;

import com.uranus.tools.beans.function.EnumConvertFunction;

/**
 * @author 李亚斌
 * @date 2019/01/28 13:56
 * @since v1.1
 */
public enum RequestMethodType implements EnumConvertFunction<RequestMethodType> {
    /**
     * 任意请求形式形式
     */
    ALL("0"),
    /**
     * POST请求形式，一般为新增
     */
    POST("1"),
    /**
     * GET请求形式，一般为查询
     */
    GET("2"),
    /**
     * PUT请求形式，一般为修改
     */
    PUT("3"),
    /**
     * DELETE请求形式，一般为删除
     */
    DELETE("4");

    private String requestMethodTypeValue;

    RequestMethodType(String requestMethodTypeValue) {
        this.requestMethodTypeValue = requestMethodTypeValue;
    }

    @Override
    public String getStringValue() {
        return this.requestMethodTypeValue;
    }
}
