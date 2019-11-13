package com.uranus.platform.authorize.permission.rbac;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李亚斌
 * @date 2019/04/15 19:41
 * @since v1.1
 */
public interface RBACService {
    /**
     * 判断该request请求是否用有权限
     * @param request 请求
     * @return true为通过鉴权
     */
    boolean hasPermission(HttpServletRequest request);

    /**
     * 判断该请求是否拥有权限
     * @param accessToken token信息
     * @param requestUri 请求URI地址
     * @param requestMethodType 请求方式
     * @return 鉴权结果
     */
    boolean hasPermission(String accessToken, String requestUri, String requestMethodType);

}
